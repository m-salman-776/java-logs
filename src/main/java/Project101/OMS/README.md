# Architecture: The Orchestrator Saga 🎻

We do **not** want services calling each other randomly (spaghetti).  
We use the **Orchestrator Pattern**, where the **Order Service** is the central command center.

---

## 1. Core Microservices

### **Order Service (The Boss)**

- Manages lifecycle:  
- Maintains the **State Machine**
- Ensures **Idempotency**

---

### **Inventory Service (The Vault)**

Tracks SKU counts.

**Capabilities:**

- `reserveStock()`
- `releaseStock()`
- `commitStock()`

---

### **Payment Service (The Cashier)**

Talks to payment gateways (Stripe/PayPal).

**Capabilities:**

- `authorize()`
- `capture()`
- `refund()`

---

### **Fulfillment Service (The Worker)**

Talks to warehouse/shipping providers.

**Capabilities:**

- `createShipment()`
- `generateLabel()`

---

## 2. Workflow: "Place Order" (Happy Path) 🛒

**Scenario:** User clicks **Buy Now** for an iPhone 15.

**Request:**


---

### Idempotency Check

- Order Service checks **Redis** for `request_id`
- If exists → return previous result

---

### Step 1: Create Order (Pending)

Order Service creates DB record:


---

### Step 2: Reserve Inventory (Locking)

Order Service 👉 Inventory Service:


Inventory Service:

- Decrements **Available Stock**
- Increments **Reserved Stock**

**Result:** Success

---

### Step 3: Process Payment

Order Service 👉 Payment Service:


Payment talks to gateway.

**Result:** Success

---

### Step 4: Confirm Order

Order Service:

- Updates status: `CONFIRMED`
- Emits event: `ORDER_CONFIRMED`

---

### Step 5: Async Fulfillment

Fulfillment Service listens to event:

- Starts packing process

---

## 3. Failure Path: The Compensation Strategy ⚠️

**Scenario:** Inventory reserved, but payment fails.

---

### Step 1
Order Created → `PENDING`

### Step 2
Inventory Reserved → `Success`

### Step 3
Payment Requested → **FAILED**

---

### Step 4: Rollback (Compensation)

Order Service:

👉 `releaseStock()` at Inventory Service

Inventory:

- Moves item from **Reserved → Available**

---

### Step 5: Update Status

Order Service: updates DB: Status = CANCELLED

User notified:"Payment Failed".


---

## 4. Idempotency (Double-Click Protection) 🛡️

### The Problem

User has slow internet → clicks **Pay** twice.

**Risks:**

- 2 orders created
- User charged twice
- 2 shipments

---

### The Solution: Idempotency Key

Frontend includes:


### Flow

1. Order Service checks Redis
2. If Key Exists:
    - Return stored response
    - No logic executed
3. If Key is New:
    - Save key: `PROCESSING`
    - Execute logic
    - Save result

---

## Summary (For Interviews)

✔️ **Design Pattern:** Orchestration-based Saga  
✔️ **Inventory Strategy:**  
(Prevents overselling)

✔️ **Consistency:**
- **Strong consistency** for inventory (avoid negative stock)
- **Eventual consistency** for fulfillment/shipping

---

# Distributed Orchestration in a Saga Workflow

In a distributed system, we **cannot** do:

inventoryService.reserve()
paymentService.charge()


inside a single synchronous block.

If the server crashes mid-operation, we **lose state**.

---

## 🎯 Goal: The Async Worker

We need a **Saga Orchestrator** that:

- Listens for domain events
- Drives the state machine forward
- Handles recovery + rollback

This is our **OrderOrchestrator**.

---

## Orchestrator Workflow

1. Listen for `ORDER_CREATED`
2. Command **Inventory Service** to reserve stock
3. Wait for `INVENTORY_RESERVED`
4. Command **Payment Service** to charge money
5. Handle failures via **compensating transactions**

---

## 1. Architecture: Message Queues 📨

We use a **Message Broker** (Kafka / RabbitMQ) to decouple the workflow.

### Topics (Event Streams)

| Topic Name         | Produced By             | Purpose                          |
|-------------------|-------------------------|----------------------------------|
| `order-events`     | Order Service           | Order lifecycle changes          |
| `inventory-events` | Inventory Service       | Stock reservation status         |
| `payment-events`   | Payment Service         | Payment authorization outcomes   |

---

The Orchestrator **subscribes** to these topics and makes decisions:

- If inventory is reserved → proceed to payment
- If payment fails → release inventory
- If payment succeeds → confirm order
- Everything is **asynchronous** and **recoverable**

---

# 1. The Happy Path (Success Flow) 🟢

This is the **successful end-to-end state transition** for an order.

## State Transition Table

| From State       | Event / Trigger        | To State         | Action / Side Effect                                      |
|------------------|------------------------|------------------|-----------------------------------------------------------|
| `CREATED`        | Checkout Initiated     | `RESERVED`       | Call **Inventory Service** to lock items.                 |
| `RESERVED`       | User Clicks Pay        | `PAYMENT_PENDING`| Redirect user to Payment Gateway / send auth request.     |
| `PAYMENT_PENDING`| Webhook: Success       | `PAID`           | Record **transaction ID**.                                |
| `PAID`           | Fraud Check Passed     | `CONFIRMED`      | Generate **invoice**; notify **Warehouse (WMS)**.         |
| `CONFIRMED`      | Warehouse Packed       | `SHIPPED`        | Generate **shipping label**; assign **tracking number**.  |
| `SHIPPED`        | Carrier Scan           | `IN_TRANSIT`     | Notify user: _"Your order is on the way."_                |

---
## 2. The Failure & Exit Paths 🔴

These are the **non-happy paths** where the order fails or is cancelled, and we apply **compensation logic** (rollback).

### State Transition & Compensation Table

| From State        | Event / Trigger          | To State    | Compensation Logic (Rollback)                      |
|-------------------|--------------------------|------------|---------------------------------------------------|
| `CREATED`         | Inventory Check Fail     | `FAILED`   | Notify user: _"Out of Stock"_.                    |
| `RESERVED`        | Payment Declined         | `FAILED`   | **Release inventory lock** immediately.           |
| `PAYMENT_PENDING` | Timeout / Payment Fail   | `FAILED`   | **Release inventory lock**.                       |
| Any Pre-Ship      | User Clicks Cancel       | `CANCELLED`| If **Paid**: trigger **refund**. Always: release stock. |

---
## Key Design Decisions (For Interview)

---

### RESERVED vs PAYMENT_PENDING

We separate these states because a user might reserve stock and then **never proceed to payment** (e.g., closes browser).

#### Rule:
`RESERVED` should have a **TTL (Time To Live)**, e.g.:


If no payment happens:

- A **background job** moves it to `CANCELLED`
- Stock is **released for others**

---

### PAID vs CONFIRMED

- `PAID` means **we have the money**
- `CONFIRMED` means the **order is valid**:
    - Fraud check passed
    - Shipping address valid

#### Why separate?

If a user **pays** but **fails fraud check**, we move to:


Instead of sending a thief an iPhone.

---

### FAILED vs CANCELLED

| State     | Reason                                              |
|----------|-----------------------------------------------------|
| `FAILED` | System error or rejection (Card Decline, OOS)       |
| `CANCELLED` | User intent (clicked cancel) or Timeout          |

Summary:

- `FAILED` → User **wanted to buy** but **couldn’t**
- `CANCELLED` → User **or time** decided to stop

---
![Architecture Diagram](Assets/osm_summary.png)
![Architecture Diagram](Assets/osm.png)