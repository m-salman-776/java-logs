# Auction Pricing in Used and Vintage Markets

In markets like **Used Cars** (Cars24, Spinny) or **Vintage Fashion** (The RealReal, Depop), pricing is the hardest problem.

---

## 1. Why Bidding? (The Business Case)

A fixed price usually fails in these markets due to three reasons:

### ### 1.1 Price Discovery (The "True Value" Problem)

- **New iPhone** → Price is known ($999). It’s a commodity.
- **Used 2015 Honda City** → Highly variable:
    - One may have scratches
    - One may have a new engine

A fixed price of **$5,000** can be:
- Too low → seller loses money
- Too high → car never sells

**Bidding lets the market decide the price.**  
If 50 people want it → price rises to the true value.

---

### 1.2 Liquidity (Speed of Sale)

Auctions have a **deadline** (e.g., *“Ends in 24 hours”*).

This creates **urgency and FOMO**:
- Buyers act fast
- Auctions clear in **days**, while fixed listings may sit for **months**

---

### 1.3 Gamification (Engagement)

Watching the **bid counter go up** is addictive.

- Users stay longer
- The product becomes “sticky”
- More chance they will bid on other items

---

## 2. The User Journey: *“The Battle for the Red Honda”* 🚗💥

Let’s trace the full auction path.

### Actors

| Role | Description |
|---|---|
| **Alice (Seller)** | Lists her Red Honda City |
| **Bob (Early Bird)** | Likes the car, sets a budget |
| **Charlie (Sniper)** | Jumps in at the last second |

---

## Phase 1: Creation (The "Slow" Path)

Alice lists the car.

**Input:**
- Car: `"Red Honda City"`
- Start Price: `$10,000`
- Reserve Price: `$12,000` (hidden minimum)
- End Time: **5:00 PM**

**System Actions:**
- `AuctionService` (SQL DB) creates record with status: `CREATED`
- `Scheduler` sets timer to move status → `ACTIVE` at **9:00 AM**

---

## Phase 2: The Bidding War (The "Fast" Path)

- Current Time: **4:50 PM**
- High Bid: **$11,000**

### Bob places a **Proxy Bid** (Auto-Bid): `$15,000`

> “I will pay up to $15k, but only if necessary.”

**Bidding Engine (Redis):**

- Stores Bob’s max: `$15,000`
- Calculates new minimum:

- Updates:
- Current Price: **$11,100**
- Leader: **Bob**

**Live Updates (WebSockets):**


---

## Phase 3: Sniping & Defense (The "Edge Case")

- Time: **4:59:55 PM**
- Charlie bids: **$11,500**

### Engine Logic:

- Checks Redis → valid
- Sees Bob’s max = `$15,000`

**Auto counter-bid from Bob:**
- New price: **$11,600**
- Charlie is instantly outbid

---

### Panic Move:

- Charlie bids **$16,000** at **4:59:59 PM**
- Charlie is now winning

### Anti-Sniping Logic:

Since a bid arrived in the **last 1 minute**:
- Extend auction by **+2 minutes**

**New End Time:** `5:02 PM`

**Live Broadcast:**


---

## Phase 4: Settlement (The "Reliability" Path)

- Time: **5:02 PM**
- No more bids

### Scheduler:
- Triggers `End Auction` job

### Validation:

Is final bid > reserve?

Auction is **successful**.

---

## Wallet Service Actions

- Locks **$16,000** in **Charlie’s wallet**
- Releases deposit for **Bob**

---

## Notifications

- **Alice:**  


- **Charlie:**  

---

![Architecture Diagram](Assets/auction.png)

# Schema Relationships (ER Diagram Logic) 🔗

These three tables form a hierarchy.

---

## 1. Table Hierarchy

### **Auctions (Parent Table)**
This is the **heart**. It represents the **item for sale**.  
Changes frequently (high bid, status).

### **Auction_Settings (1:1 Extension)**
This is the **brain**. It holds the **rules** (reserve price, increments).  
Rarely changes after creation.

### **Bids (1:N Child)**
This is the **ledger**. It records **every attempt to buy**.  
Never changes once inserted (immutable log).

---

## 1.1 Relationships

- **One Auction HAS ONE Auction_Settings**  
  `auction_settings.auction_id → auctions.id`

- **One Auction HAS MANY Bids**  
  `bids.auction_id → auctions.id`

---

## 2. Detailed Schema Definitions 🗄️

---

### A. **Auctions Table (The Dynamic State)**

Holds the **current state** of the auction.

| Column       | Type       | Purpose                           | Change Frequency |
|--------------|------------|-----------------------------------|------------------|
| `id`         | UUID (PK)  | Unique ID for the auction         | Never            |
| `seller_id`  | UUID (FK)  | Who is selling?                   | Never            |
| `current_price` | DECIMAL | Updated on every valid bid        | High             |
| `status`     | ENUM       | `CREATED → ACTIVE → ENDED`        | Low              |
| `end_time`   | TIMESTAMP  | When does it stop? (Sniping updates) | Medium       |
| `version`    | INT        | Optimistic lock for concurrency   | High             |

---

### B. **Auction_Settings Table (The Rules Engine)**

Holds the configuration for an auction.

| Column            | Type       | Purpose                                         | Change Frequency |
|-------------------|------------|-------------------------------------------------|------------------|
| `auction_id`      | UUID (PK, FK) | Links back to the auction                      | Never            |
| `reserve_price`   | DECIMAL    | Hidden minimum price                            | Rare             |
| `min_increment`   | DECIMAL    | Minimum bid increment                           | Rare             |
| `sniping_duration`| INT        | Extend by N minutes if bid is within last M mins| Rare             |

---

### C. **Bids Table (The Immutable Ledger)**

Write-only history of all bids.

| Column       | Type       | Purpose                              | Change Frequency |
|--------------|------------|--------------------------------------|------------------|
| `id`         | UUID (PK)  | Unique Bid ID                        | Never            |
| `auction_id` | UUID (FK)  | Which auction?                       | Never            |
| `user_id`    | UUID (FK)  | Who bid?                             | Never            |
| `amount`     | DECIMAL    | How much?                            | Never            |
| `created_at` | TIMESTAMP  | Tie-breaker for identical bids       | Never            |
| `status`     | ENUM       | ACCEPTED, REJECTED, WINNING          | Rare             |

---

## 3. Lifecycle of Data 🔄

Follow the data changes through the auction phases.

---

### Phase 1: Listing the Item (Creation)

**Action:** User creates an auction for a “Red Honda”.

#### Transaction Start

```sql
INSERT INTO Auctions:
  id = 'auc_101',
  current_price = 10000,
  status = 'CREATED',
  version = 1;

INSERT INTO Auction_Settings:
  auction_id = 'auc_101',
  reserve_price = 12000,
  min_increment = 100;


