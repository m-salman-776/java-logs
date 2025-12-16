![Architecture Diagram](Assets/chat.png)
# 1. Core Design Decision: Storage Models

Before designing the architecture, we must choose **how to store and deliver messages**.  
There are two primary models:

---

## **Approach A: The "Inbox" Model (WhatsApp / WeChat)**

### **Concept**
Like email. When User A sends a message to a Group:

- The server **copies** the message into the “inbox” of every member.

### **Pros**
- Great for mobile
- Offline users just sync their inbox later

### **Cons**
- VERY expensive for large groups
- 1 message × 1000 users = 1000 DB writes


### **Use Case**
- **Small groups (< 500 members)**

---

## **Approach B: The "Timeline" Model (Slack / Discord)**

### **Concept**
Messages are stored **once** in a central channel timeline.  
Users read from this shared history.

### **Pros**
- Efficient storage
- 1 message = 1 DB write
- Scales to **100k+ member groups**

### **Cons**
- Slower to sync
- Client must poll each channel for unread status

### **Use Case**
- Large communities / servers

---

## ✅ **Recommendation: Hybrid Model**

Most modern chat systems use:

- **Storage:** 1 copy in DB
- **Delivery:** Fan-out **notifications** to each member

This is exactly how **WhatsApp, Messenger, Telegram** do it.

---

# 2. High-Level Architecture

This describes what happens when **User A sends a message** to **Family Group (ID: 101)**.

---

## **1. Send Path**

### **User Action**
 ``POST /message { group_id: 101, text: "Hello" }``

### **Chat Service Steps**

#### **Persist**
Store the message in Cassandra/HBase:

- Table: `Group_Messages`
- Partition: `group_id = 101`

#### **Lookup**
Chat Service queries **Group Service** (Redis cache):

``Members = [User B, User C, User D]``


#### **Fan-out**
Publish **3 events** to Kafka:

``Topic: User_B_Queue → "New msg in Group 101"
Topic: User_C_Queue → "New msg in Group 101"
Topic: User_D_Queue → "New msg in Group 101"``


---

## **2. Delivery Path**

### WebSocket Servers consume Kafka events:

#### If User B is Online:
- Push message instantly via WebSocket

#### If User C is Offline:
- Trigger push notification (FCM/APNS)

---

# 3. Data Model (Cassandra / ScyllaDB)

We need a database with **massive write throughput**.

---

## **Table 1: Message Store (Chat History)**
Partition by `group_id` → all group messages co-located.

```sql
CREATE TABLE Group_Messages (
    group_id bigint,
    message_id timeuuid, -- Auto-sorts by time
    user_id bigint,      -- Who sent it
    content text,
    PRIMARY KEY ((group_id), message_id)
) WITH CLUSTERING ORDER BY (message_id DESC);


CREATE TABLE Group_Members (
    group_id bigint,
    user_id bigint,
    joined_at timestamp,
    role text, -- Admin/Member
    PRIMARY KEY ((group_id), user_id)
);
```
# System Design: Read Receipts & Group Scalability

This document outlines the architectural solutions for handling high-volume "Read Receipt" updates in group chats and optimizing message delivery for "Mega Groups" (e.g., 20,000+ users).

---

## 1. The "Read Receipt" Problem (The Hidden Bottleneck)

### The Bottleneck
In a **1:1 chat**, handling read receipts is straightforward: User B reads it $\rightarrow$ update status.

However, in a **group of 100 people**, if everyone reads the message simultaneously, that results in **100 write operations** hitting the database per second, per message. This high write throughput can crush a standard database.

### The Solution: "Last Read" Pointer
Instead of tracking the granular state of "User B read Message X," we track a cursor: **"User B has read Group 101 up to Time T / Message ID Y."**

#### Implementation Flow

**1. The Event**
User B opens the group chat and scrolls to the bottom.

**2. The API Call**
The client sends a lightweight update to the server:
```http
PUT /group/101/read 
{ 
  "last_message_id": 999 
}
```


### 3. The Storage (Redis)
We use an in-memory store like Redis for high-speed updates.

* **Key:** `read_marker:group:101:user:B`
* **Value:** `999`

### 4. The Calculation (Who read my message?)
When User A asks, *"Who read my message (ID 500)?"*, the server queries Redis rather than a persistent DB table:

1.  **Check User B:** Is User B's marker (`999`) > Message ID (`500`)?
    * $\rightarrow$ **Yes.** (Show Blue Ticks)
2.  **Check User C:** Is User C's marker (`450`) > Message ID (`500`)?
    * $\rightarrow$ **No.** (Show Grey Ticks)

> **Impact:** This approach reduces thousands of database writes into simple, fast Redis key-value updates.

---

## 2. Handling "Mega Groups" (e.g., 20,000 Users)

### The Problem: Fan-out Latency
If a group is too large, the **Fan-out loop** (looping through 20,000 users to identify active connections and push messages) becomes a significant latency bottleneck.



### Optimizations
* **Disable Read Receipts:** For groups surpassing a certain threshold (e.g., 1,000 users), "Blue Ticks" are disabled to save computing resources and storage I/O.
* **Turn off Push Notifications:** Users are defaulted to **"Muted"** upon joining mega groups. This prevents the system from triggering 20,000 external push notification requests (FCM/APNS) for every single message.
* **Pull-Only Mode:** If the group is extremely active, the system switches from Push to Pull for specific clients.
    * **Mechanism:** The client does not wait for WebSocket pushes. Instead, when the app is open, it polls the server every few seconds.
    * **Query:** `"Give me the latest 50 messages."`

