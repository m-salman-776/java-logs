# Expense Sharing & Settlement System

## 📌 Problem Statement

Design and implement a program that facilitates expense sharing among a group of people, accurately calculates balances, and facilitates settlements efficiently.

---

## 🎯 Requirements

### 1. Add Expenses

Users should be able to:
- Add an expense
- Specify:
    - Total amount
    - Paid by whom
    - Participants involved
    - Split strategy

---

### 2. Split Strategies

The system should support the following split strategies:

#### ✅ Equal Split

The total amount is divided equally among all participants.

**Example:**

- Participants: 3
- Amount: INR 300
- Each owes: INR 100  
- 300 / 3 = 100 each
---

#### ✅ Exact Amount Split

Each participant’s owed amount is explicitly specified.

**Example:**

- Participants: 3
- Amount: INR 300

| User   | Owes (INR) |
|--------|------------|
| User1  | 50         |
| User2  | 100        |
| User3  | 150        |

---

#### ✅ Percentage Split

Each participant’s share is specified in percentage.

**Example:**

- Participants: 3
- Amount: INR 300

| User   | Percentage | Owes (INR) |
|--------|------------|------------|
| User1  | 20%        | 60         |
| User2  | 30%        | 90         |
| User3  | 50%        | 150        |
- User1: 300 × 20% = 60 
- User2: 300 × 30% = 90
- User3: 300 × 50% = 150


---

### 3. Check Balances

Users should be able to:

- View how much they owe others
- View how much others owe them
- See pairwise balances

**Example Output:**

- User1 owes User2: INR 200
- User3 owes User1: INR 150


---

### 4. Check Net Balance

Users should be able to see their net balance:

- Positive Balance → Others owe them money
- Negative Balance → They owe money to others

**Example:**

- User1: +150
- User2: -100
- User3: -50


---

### 5. Settle Debt Optimally

The system should:

- Suggest the minimum number of transactions
- Minimize total transactions required
- Provide clear settlement instructions

**Example:**

- User2 → User1 : 100
- User3 → User1 : 50


---

## 🧠 Expected Capabilities

- Maintain accurate ledger of transactions
- Validate split amounts and percentages
- Prevent inconsistent states
- Provide optimized settlement suggestions

---

## 🚀 Summary

This system enables:

- Flexible expense splitting
- Transparent balance tracking
- Accurate net calculations
- Optimized debt settlement  
