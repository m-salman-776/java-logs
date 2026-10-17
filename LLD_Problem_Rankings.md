# LLD Problem Rankings (Comprehensive & Final)

This document provides a comprehensive analysis and ranking of all 40 Low-Level Design (LLD) problems in the `/src/main/java/Project101` directory. The problems are ranked to help you focus your preparation on the most challenging and valuable examples.

---

## Tier 1: Hard (Distributed Systems & Advanced Concurrency)

These problems model complex, real-world systems, often involving multiple services, advanced concurrency patterns, and high-performance requirements. Mastering these indicates a senior-level understanding of LLD.

1.  **Rate Limiter**: A pure, high-performance systems design problem using a configuration-driven rule engine and the Strategy pattern. Excellent for discussing low-latency, extensible services.
2.  **Order Management System (OMS)**: A full-fledged distributed systems problem using the Saga pattern, message queues, and compensating transactions to manage an e-commerce order lifecycle.
3.  **Stock Brokerage**: A high-complexity system whose core is a low-latency order matching engine. The design realistically includes portfolio management with lot tracking for capital gains.
4.  **Auction System**: A real-time bidding system using WebSockets, optimistic locking for concurrency, and a hybrid SQL/Redis persistence model. The design includes advanced features like proxy bidding and anti-sniping.
5.  **Code Editor / Execution Platform**: Models a complex backend like LeetCode, featuring an asynchronous worker queue architecture and the Strategy pattern for handling different programming languages.
6.  **Ride-Sharing App**: A challenging problem involving real-time geospatial matching, state machines for the ride lifecycle, and real-time notifications.
7.  **Incident Alerting System (PagerDuty Clone)**: A complex system for managing on-call rotations and incident response, focusing on escalation policies and multi-channel notifications.
8.  **In-Memory Database (MySql / InMemoryDB)**: A highly ambitious problem that involves designing the core components of a database, including tables, schemas, indexing, and transactions.

---

## Tier 2: Medium-Hard (Advanced OOP & Concurrent Design)

These problems focus on a single, stateful service and require a strong command of OOP, design patterns, and concurrency control.

9.  **Hotel Reservation System**: A classic concurrency problem focused on managing inventory across date ranges. The solution correctly uses `synchronized` blocks and a `ScheduledThreadPoolExecutor` for auto-cancellation.
10. **Movie Booking System (V2)**: A robust booking system that correctly uses **Optimistic Locking** (`version` column) to handle concurrent seat selection—a key differentiator from more basic booking systems.
11. **Expense Sharing App (Splitwise Clone)**: A great mix of OOP (Strategy pattern for splits) and a non-trivial algorithmic challenge (debt simplification).
12. **Resource Pool**: A pure concurrency problem. The implementation correctly uses `Semaphore` and `BlockingQueue` to build a generic, thread-safe object pool.
13. **Meeting Scheduler (Google Calendar Clone)**: Manages state for multiple interacting entities (users, rooms) and handles complex availability logic, including support for recurring meetings.
14. **File System**: A clean, robust implementation of an in-memory file system. Excellent use of the Composite pattern and a good example of handling complex, recursive logic like path resolution.
15. **Currency Converter**: A great example of the Strategy pattern. The `GraphExchangeRateProvider` elevates the problem by introducing a graph traversal algorithm to find multi-step conversion paths.
16. **Consistent Hashing**: A focused implementation of a fundamental distributed systems algorithm. The code correctly uses a `TreeMap` and virtual nodes to ensure uniform data distribution.
17. **Project Assignment System**: A resource allocation problem that correctly uses atomic operations (`compareAndSetBusy`) to manage concurrency, demonstrating a sophisticated approach to thread safety.
18. **Elevator System (V2)**: A very detailed and granular OOP model that uses the Strategy pattern for its core dispatching logic, making it highly extensible.
19. **Order Inventory**: A focused concurrency problem that correctly models inventory reservation and solves the race condition between order completion and reservation timeout using atomic map operations.
20. **Payment System**: A fantastic example of using multiple design patterns (Gateway, Factory, Strategy) together to build a flexible system that integrates with external payment providers.
21. **LLM Router V2**: A sophisticated implementation of the Circuit Breaker pattern. It uses a state machine and concurrent data structures to dynamically route traffic based on provider health.

---

## Tier 3: Medium (Standard LLD Problems)

These are common LLD problems that test core OOP principles and data structures.

22. **Auth (RBAC + ABAC)**: A good blend of data modeling and policy engine design. Focuses on creating a flexible and extensible data schema for authorization.
23. **Review & Rating System**: A solid, thread-safe service using concurrent data structures. Focuses on efficient data aggregation (calculating average ratings).
24. **Car Rental System**: A standard business application LLD. Good for demonstrating data modeling skills for a complete system, including entities for reservations, billing, and logging.
25. **Chat System**: A classic example of the Observer design pattern. Good for demonstrating an understanding of basic behavioral patterns for real-time updates.
26. **Notification System**: A good example of a standard backend service that uses an asynchronous queue-based architecture (Producer-Consumer) and common creational patterns (Factory, Builder).
27. **Loyalty Program**: A good example of a rule-based system. The current implementation has bugs, which makes it a useful case study for code review and debugging.
28. **LFU Cache**: A good data structures and algorithms problem that is more complex than the basic LRU cache.
29. **Chess**: A classic OOP modeling problem. Requires good class design (inheritance for pieces) and algorithmic logic for move validation.
30. **Online Shopping System**: A more basic, high-level version of an e-commerce system. It's a good starting point but is superseded by the more detailed `OMS` design.
31. **Movie Ticket (V1)**: A more basic version of a movie booking system. It lacks the explicit concurrency control found in the V2 `BookingSystem`.
32. **Elevator System (V1)**: A more basic version of the elevator system. It lacks the detailed OOP model and the Strategy pattern found in V2.

---

## Tier 4: Easy (Entry-Level & Incomplete)

These are good for warm-ups or demonstrating a basic grasp of OOP, but they lack significant complexity.

33. **Snake and Ladder**: A simple board game LLD. Tests basic OOP and state management.
34. **Tic-Tac-Toe**: The simplest form of a game LLD problem. Good for a warm-up.
35. **Coupon Management**: A very basic CRUD service. The implementation is too skeletal to be of much value as a design problem.

---

## Not Ranked (Conceptual or Empty)

36. **SolidPrinciples**: Conceptual examples of SOLID principles, not an LLD problem.
37. **VendingMachine**: Directory is empty.
38. **LibraryManagement**: Directory only contains data models with no service logic.
39. **Cache**: The `LRU` implementation is a skeleton.
40. **Playlist**: The `README` is minimal, and while the structure implies patterns, the implementation is not detailed enough to rank higher.

---

## Recommended Study Path

1.  **Solidify Fundamentals (Tier 3 & 4):** Make sure you can comfortably design problems like `Chess`, `CarRental`, and `ChatSystem`.
2.  **Master Core Patterns & Concurrency (Tier 2):** Dive deep into `ExpenseShare` (Strategy), `ResourcePool` (Concurrency), `FileSystem` (Composite), and `HotelReservation` (Concurrency). These are the most common and high-value patterns.
3.  **Tackle Distributed Systems (Tier 1):** Once you are confident with single-system design, move to the Tier 1 problems. Start with `RateLimiter` or `CodeEditor` as they are slightly more self-contained, then move to `OMS` and `AuctionSystem`.

Good luck with your preparation!