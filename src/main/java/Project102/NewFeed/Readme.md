# How It Works in Production (Instagram / Facebook Model)

---

## 1. Background Worker: The "Identifier"

A scheduled analytics job determines which users are **VIPs / Celebrities**.

### **Batch Job (Spark / MapReduce)**

Runs:
- Nightly
- Or hourly (depending on scale)

### **What it does:**
- Analyzes:
    - Follower counts
    - Engagement metrics
- Applies rule:

``IF followers > 500,000 THEN is_vip = TRUE``


### **Updates:**
- User Cache (Redis)
- User Database (persistent store)

---

## 2. Runtime Check

Whenever a user posts, the Feed Service performs:

``Users.get(id).is_vip``


This lookup happens in:
- **In-memory cache**
- OR local Redis cache

➡️ **Completed in microseconds**  
➡️ **No extra network calls needed**

### Important Note:
You **do not** need a "VIP Detection Service" in the runtime architecture.  
But you **do need** an **Analytics Worker** that sets the VIP flag in the background.

---

# 2. High-Level Architecture (Hybrid Feed System)

This system supports:

- **Regular Users** → Push Model
- **Celebrities** → Pull Model

Both combined form a production-grade hybrid feed system.

---

## Components

### **1. Feed Service**
- The brain
- Handles:
    - `POST /tweet`
    - `GET /feed`
- Determines whether user is VIP or regular by checking the cache

---

### **2. Fanout Service (The Worker)**
- Performs the heavy lifting:
    - Pushes updates to followers for **non-VIP users**
- Reads follower graph
- Writes timeline entries into Redis Feed Cache

---

### **3. Redis (Feed Cache)**
- Stores **pre-computed timelines** for every active user
- Example value: ``feed:user123 = [post791, post790, post788, ...]``


Fast read → low latency feed.

---

### **4. Global Celebrity Cache**
- A **separate Redis cluster**
- Stores recent post IDs of **VIP users only**
- Used in **pull model**:
    - Followers fetch celebrity posts on demand

---

### **5. Cassandra / HBase**
Stores:
- Raw post content
- The follower graph structure

High write throughput  
High storage capacity  
Optimized for feed workloads

---

# 🔚 End of Production Explanation

Every line preserved. Nothing missed.  
Let me know if you want a diagram or full README-ready architecture drawing.


![Architecture Diagram](Assets/newfeed.png)

