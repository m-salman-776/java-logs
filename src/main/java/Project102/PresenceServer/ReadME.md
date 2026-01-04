# Requirements & Constraints

---

## Functional Requirements

### **1. Heartbeat**
- System must receive periodic _"I am alive"_ signals from clients.
- Used to determine whether the user is still online.

### **2. Status Storage**
Store:
- Current status: `Online`, `Away`, `Busy`, `Offline`
- Last active timestamp (e.g., last heartbeat time)

### **3. Fanout Notifications**
- When a user’s status changes, **notify all their friends immediately**.
- Real-time push updates (WebSockets, Push Notifications, SSE).

### **4. On-Demand Lookup**
- When a user opens the app, the system must fetch:
    - Current status of all contacts
    - Last Active timestamps

---

## Non-Functional Requirements

### **1. Latency**
- Must be extremely low.
- **Target: < 100ms** for status propagation.

### **2. Throughput**
- System must handle **massive write load**, since:
    - Every user sends heartbeats periodically.
    - Millions of users continuously update presence.

### **3. Consistency**
- **Eventual Consistency is OK**
    - Seeing someone online 1 second late is acceptable.
    - Strong consistency not required.

# Component Deep Dive

---

## A. Storage Layer: Redis Cluster 🧠

We **do not** use SQL here — disk I/O is too slow for real-time presence.  
We use **Redis (in-memory)** for ultra-low latency.

### **Partitioning Strategy**

We shard based on `User_ID`:

`hash(User_ID) % Number_Of_Shards`

### **Data Model (Key-Value)**

`Key: user:12345:presence
        Value: {
        status: "Online",
        last_active: 1700000000,
        server_id: "ws-gateway-01"
}`


### **TTL (Time To Live)**
`30 seconds`

If heartbeats stop → key expires → user becomes offline.

---

## B. Write Path: The Heartbeat Flow ❤️

### **Client**
- Sends a heartbeat every **5 seconds** via WebSocket.

### **WebSocket Server**
- (Optional) aggregates heartbeats
- Calls **Presence Service**

### **Presence Service**
Executes:
`SET user:12345:presence "Online" EX 30`


- `EX 30` resets TTL every time
- As long as heartbeats continue → user stays **Online**

---

## C. Offline Detection: The Expiration Flow 💀

This is the clever part — we **never write “offline.”**  
We let Redis TTL **expire** the key.

### Flow:

1. User disconnects → heartbeats stop
2. Redis TTL runs to **0**
3. Key `user:12345:presence` is **deleted**
4. Redis publishes **key expiration event** (via `notify-keyspace-events Ex`)
5. Presence Service listens and detects:

User 12345 is now OFFLINE


---

## D. Fanout Flow: Notifying Friends 📢

When a status changes (e.g., Online → Offline), friends must be notified.

### **Trigger**
Presence Service detects a state change.

### **Lookup**
Presence Service queries **Graph Service** (Cassandra / Graph DB):

GetFriends(A) → [B, C, D ...]

### **Publish**
For ordinary users (small friend list), we push updates.

Kafka Message:

`{
subject_user: A,
status: "Offline",
recipients: [B, C, D]
}`



### **Delivery**
WebSocket servers:

1. Consume message from Kafka (`presence-updates`)
2. Look up the active connections for B, C, D
3. Push real-time notification

---

# 4. Handling Scale & Bottlenecks

## Problem 1: The "Justin Bieber" Issue (Celebrity Fanout)

If a celebrity has **10 million followers**, pushing 10M notifications every time they come online is impossible.

### **Solution: Hybrid Model**

| User Type         | Strategy | Why |
|------------------|----------|-----|
| **Small users**   | Push     | Their friend list is small |
| **Celebrities**   | Pull     | Avoid massive fanout load |

### How Pull Works for Celebrities

We **do NOT** push “celebrity online” updates.

Instead:

- Followers only check the celebrity’s status **when opening the chat**
- Presence is fetched **on demand**

This prevents catastrophic fanout.

---

# Summary

- Redis stores presence with TTL = **30 seconds**
- Heartbeats refresh TTL → user stays online
- Expiration → offline detection
- Friends are notified through Kafka
- Small users get **push updates**
- Celebrities use **pull model** to avoid overload

---
![Architecture Diagram](Assets/presence.png)


# Final Polish of the Presence Flow

This is the **production-grade** presence detection + fanout pipeline.

---

## 🔄 Step-by-Step Flow

### **1. User A (connected through Server 01) loses connection**
- Heartbeats stop.

### **2. Redis Key Expires**
Key:

Presence:A


expires due to TTL hitting 0.

Redis automatically publishes a **key expiration event**.

---

## 📡 3. Fanout Worker Receives Expiration Event

The Presence Worker is subscribed to Redis Keyspace Notifications.

It receives:


---

## 👥 4. Worker Fetches A's Friends

From the Graph DB:

Friends(A) -> [B,C,D,E]

---

## 🔍 5. Worker Checks Connection Registry

The Connection Registry (Redis) stores active WebSocket sessions for each user.

Example result:

| User | Connection State |
|------|------------------|
| B    | Online (Server 02) |
| C    | Offline |
| D    | Offline |
| E    | Online (Server 99) |

---

## 🎯 6. Worker Publishes Only Necessary Messages

We **do not push** updates to offline users (C, D).  
We only target servers that have live connections.

### Kafka Messages Published:

`Topic: server-02`
Payload: "Tell User B that A is offline."

`Topic: server-99`
Payload: "Tell User E that A is offline."


Each WebSocket server has its own topic-partition to ensure locality.

---

## 📲 7. WebSocket Fanout

### Server 02:
- Consumes `server-02` Kafka topic
- Looks up User B’s live WebSocket connection
- Pushes:


### Server 99:
Same process for User E.

---

# ✅ Production Guarantees

- **Minimal fanout** → only 2 servers receive messages, not 4 users.
- **Idempotent & stateless** workers.
- **Horizontal scalability** → each WebSocket server consumes its own topic.
- **No wasted work** on offline users.

---

