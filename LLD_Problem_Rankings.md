# LLD Problem Rankings

This document provides a ranked analysis of the Low-Level Design (LLD) problems found in the `/src/main/java/Project101` directory. The problems are ranked based on their complexity, the concepts they cover, and the quality of the solution, to help you focus your preparation on the more challenging and comprehensive examples.

---

## Tier 1: Hard (Complex Distributed Systems & Advanced Concepts)

These problems involve multiple services, asynchronous communication, and advanced design patterns. They are excellent for preparing for senior-level interviews.

### 1. Order Management System (OMS)
- **Directory:** `OMS`
- **Complexity:** High
- **Core Concepts:**
    - **Microservices Architecture:** Simulates a real-world e-commerce backend with multiple services (Order, Inventory, Payment).
    - **Saga Pattern (Orchestration):** The `OrderOrchestrator` is a central piece that manages a distributed transaction, which is a very relevant and advanced topic.
    - **State Machines:** The order lifecycle is managed via a well-defined state machine.
    - **Asynchronous Communication:** Uses message queues (like Kafka) to decouple services.
    - **Idempotency & Compensating Transactions:** Includes logic for handling duplicate requests and rolling back failed transactions.
- **Why it's a top problem:** This is a full-fledged distributed systems design problem in a nutshell. Understanding this solution deeply will prepare you for a wide range of complex LLD questions.

---

## Tier 2: Medium-Hard (Sophisticated Single-System Design)

These problems involve complex logic within a single service, often requiring a deep understanding of data modeling and advanced design patterns.

### 2. Auth (RBAC + ABAC System)
- **Directory:** `Auth`
- **Complexity:** Medium-High
- **Core Concepts:**
    - **Hybrid Authorization Model:** Combines Role-Based Access Control (RBAC) with Attribute-Based Access Control (ABAC), which is a very powerful and flexible approach.
    - **Advanced Data Modeling:** The schema is well-designed to handle roles, permissions, scopes, and inheritable permissions.
    - **Policy Engine:** The concept of attaching policy classes (`OwnershipPolicy`) to permissions is a key part of ABAC and demonstrates a sophisticated design.
- **Why it's a top problem:** It goes far beyond simple CRUD and tackles a complex, real-world problem. It's a great example of how to design a flexible and extensible authorization system.

---

## Tier 3: Medium (Core LLD & Data Structures)

These problems are classic LLD interview questions that test your understanding of core data structures and object-oriented design principles.

### 3. CricBuzz
- **Directory:** `cricBuzz`
- **Complexity:** Medium
- **Core Concepts:**
    - **Rich Domain Modeling:** Requires modeling a complex domain (a cricket match) with many interacting classes (`Match`, `Inning`, `Player`, `Ball`, etc.).
    - **Database Schema Design:** The `README` shows a well-thought-out schema, including the use of `JSONB` for flexibility.
    - **Object-Oriented Principles:** A good example of applying encapsulation, inheritance (for different match types), and composition.
- **Why it's a good problem:** It's a great test of your ability to translate a complex real-world scenario into a clean, object-oriented model.

### 4. Playlist Player
- **Directory:** `Playlist`
- **Complexity:** Medium
- **Core Concepts:**
    - **Behavioral Design Patterns:** The solution correctly uses the **State Pattern** to manage the player's state (`Playing`, `Paused`) and the **Strategy Pattern** for different playback modes (`Shuffle`, `Sequential`).
    - **Polymorphism:** Uses a `Playable` interface to handle different media types (songs, podcasts) uniformly.
- **Why it's a good problem:** It's a perfect, self-contained example of how to use common behavioral design patterns to create a flexible and maintainable system.

### 5. LFU Cache
- **Directory:** `Cache`
- **Complexity:** Medium
- **Core Concepts:**
    - **Advanced Data Structures:** Requires a combination of hash maps and a doubly linked list to achieve an efficient O(1) implementation.
    - **Algorithm Design:** The logic for tracking frequency and evicting the least frequently used item is non-trivial.
- **Why it's a good problem:** It's a classic LLD problem that directly tests your data structure and algorithm skills. The provided implementation has some bugs, making it a good exercise for code review and debugging.

---

## Summary for Preparation

- **Start with Tier 3:** Make sure you have a rock-solid understanding of the `cricBuzz`, `Playlist`, and `LFU Cache` problems. These cover the fundamental skills of OOP, data structures, and common design patterns.
- **Move to Tier 2:** The `Auth` system is a great step up. Focus on understanding the data model and how it enables the hybrid RBAC/ABAC system.
- **Master Tier 1:** The `OMS` is the most comprehensive problem. If you can understand and explain this design, you are likely well-prepared for even the most challenging LLD interviews.

Good luck with your preparation!