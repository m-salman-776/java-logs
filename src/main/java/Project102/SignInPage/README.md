# Sign In Page HLD
![Architecture Diagram](Assets/login.png)

# Login System HLD --- Complete Design & Scaling Guide

------------------------------------------------------------------------

# Table of Contents

1.  Correct Design Order
2.  Define Auth Flow
3.  Decide JWT vs Session
4.  Define Session Storage
5.  Define Consistency Requirements
6.  Add Infrastructure Layers
7.  Redesign for 100k QPS
8.  Token Revoke at Scale
9.  JWT vs Session Comparison
10. Numerical Sizing
11. Redis vs Read Replicas
12. Final Production Architecture

------------------------------------------------------------------------

# 1. Correct Design Order

Never start from DB or replicas.

Correct order:

1.  Define authentication flow
2.  Decide JWT vs session
3.  Define revoke strategy
4.  Define consistency requirements
5.  Add minimal infrastructure
6.  Scale numerically
7.  Add HA
8.  Add security and rate limiting

------------------------------------------------------------------------

# 2. Define Auth Flow

## Login Flow

1.  User submits email/password
2.  Auth service validates credentials
3.  Generate token (JWT or session_id)
4.  Return token to client
5.  Client sends token in future requests

## Request Validation Flow

1.  Client sends request with token
2.  Service validates token
3.  If valid → process request
4.  If invalid → reject

------------------------------------------------------------------------

# 3. Decide JWT vs Session

## Session-Based Authentication

Login: - Generate session_id - Store session in Redis/DB - Send cookie
to client

Request: - Lookup session in Redis - Validate

Pros: - Easy revoke - Strong control - Immediate logout

Cons: - Redis lookup per request - Stateful - Must scale session store

------------------------------------------------------------------------

## JWT (Stateless)

Login: - Generate signed JWT - Return to client

Request: - Verify signature - Check expiry - No DB/Redis call required

Pros: - Stateless - Highly scalable - No DB hit per request

Cons: - Harder revoke - Valid until expiry - Requires security
management

JWT is preferred for high-QPS distributed systems.

------------------------------------------------------------------------

# 4. Define Session Storage Strategy

Even with JWT, storage is required for:

-   Refresh tokens
-   Revoked tokens
-   Password hashes
-   User metadata

Typical storage:

Primary DB: - Users table - Refresh tokens

Redis: - Token blacklist - Rate limiting - OTP cache

------------------------------------------------------------------------

# 5. Define Consistency Requirements

Ask:

-   Does logout require immediate revoke?
-   Does password change invalidate tokens instantly?
-   Multi-device logout?

Strong consistency required for:

-   Credential validation
-   Account lock
-   Password change

Not required for:

-   Every token validation (if short-lived JWT)

------------------------------------------------------------------------

# 6. Add Infrastructure Layers

Minimal architecture:

Clients\
→ Global Load Balancer\
→ API Gateway\
→ Auth Service (Pods / ASG)\
→ Primary DB (Multi-AZ)\
→ Redis (optional)

Read replicas are usually unnecessary for login systems.

------------------------------------------------------------------------

# 7. Redesign Login for 100k QPS

Assume:

100k QPS total\
95% token validation\
5% login

Traffic:

95k token validation\
5k login

------------------------------------------------------------------------

## With JWT

Token validation: - Pure CPU - No DB call - No Redis lookup

Scaling dimension: CPU-bound

------------------------------------------------------------------------

## With Session DB

Each request: - Redis lookup

Redis must handle 100k ops/sec.

JWT avoids this bottleneck.

------------------------------------------------------------------------

## Recommended 100k QPS Architecture

Clients\
→ Global LB : DNS Multi-Region\
→ API Gateway : [Rate Limit]\
→ Auth Service (10--20 pods)\
→ Primary DB (Multi-AZ)\
→ Redis (refresh tokens, revoke list)

------------------------------------------------------------------------

# 8. Token Revoke at Scale

## 1. Short-Lived Access Tokens (Recommended)

Access token: 5--15 minutes\
Refresh token: stored in DB

Logout: - Delete refresh token - Access token expires naturally

------------------------------------------------------------------------

## 2. Blacklist Store

Logout: - Add token ID to Redis blacklist

Each request: - Check blacklist

Drawback: - Redis lookup per request

------------------------------------------------------------------------

## 3. Token Versioning

DB stores: - user.token_version

JWT includes: - token_version

Logout: - Increment version

Validation: - Compare versions

------------------------------------------------------------------------

# 9. JWT vs Session DB Comparison

Feature                  JWT         Session DB
  ------------------------ ----------- ------------------------
Stateless :             Yes |        No\
Revoke easy :           Hard  |      Easy\
DB hit per request :     No     |     Yes\
Scales to 100k QPS :     Very well |   Requires Redis scaling\
Good for microservices:  Yes        | Less ideal\
Operational complexity:  Moderate   | Lower 

------------------------------------------------------------------------

# 10. Numerical Sizing (100k QPS)

## Auth Service CPU

Assume JWT validation = 0.2 ms CPU

100k × 0.0002 sec = 20 CPU seconds per second

Minimum cores required = 20

Add 30% headroom: \~30 cores

If each instance = 4 vCPU:

30 / 4 ≈ 8 instances

Provision 10 instances.

------------------------------------------------------------------------

## DB Sizing

Login QPS = 5k

Each login: 
- 1 user lookup
- 1 password hash

5k QPS handled by 1 RDS instance (8--16 vCPU)

Multi-AZ for HA.

------------------------------------------------------------------------

## Redis (If Used)

5k writes + occasional reads

Small Redis cluster supports \>100k ops/sec.

------------------------------------------------------------------------

# 11. Redis vs Read Replicas

Redis is more useful than DB read replicas because it handles:

-   Session storage
-   Token blacklist
-   Rate limiting
-   OTP caching

Read replicas rarely needed for login.

------------------------------------------------------------------------

## Schema

#### 1.User
- id
- name
- email
- password_hash
- user_salt
- version : for check global logout from all device
- account_status

#### Token Schema
- token_id
- token_hash
- device_id
- ip : check if there is drastic change in ip to trigger re-logic or capcha validation
- expired_at
- is_revoked

## Rolling Session / Token Refresh
- Client send login Request with expired JWT
- System Reject the call
- Client automatically hit `/refresh` end point 
- System generate a new refresh token and remove the old one thereby increasing the expiration duration by 7days

# 12. Final Production Architecture

Clients\
→ Global LB\
→ API Gateway\
→ Auth Service (10 pods)\
→ Primary DB (Multi-AZ)\
→ Redis Cluster

Key properties:

-   Stateless token validation
-   Strong consistency for login
-   High availability
-   Scales to 100k QPS
-   Minimal DB pressure

------------------------------------------------------------------------

# Final Mental Model

Login systems are:

CPU-bound for validation\
DB-bound for login attempts\
Redis-bound for revoke & rate limiting

Not read-replica-bound.



