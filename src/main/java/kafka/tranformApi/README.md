# Kafka Streams Transformer Practice Scenarios

This repository contains practice exercises to help understand and master the `Transformer` interface in Kafka Streams.

## Scenarios

### 1. Enrichment with a State Store
- **Objective**: Enrich a stream of transactions with user details using a state store.
- **Details**: You're receiving a stream of transactions with a user ID. Use a static dataset (loaded into a state store) of user details to enrich the transaction stream.

### 2. Moving Average
- **Objective**: Calculate the moving average of a stream of numbers.
- **Details**: Implement a transformer that keeps the last 'n' numbers in a state store and calculates the moving average for each incoming number.

### 3. Delayed Duplicate Filter
- **Objective**: Filter out duplicates from a stream of records based on a time window.
- **Details**: Only consider a record as a duplicate if it appears again within a specified time window.

### 4. Event Flattening
- **Objective**: Flatten a stream of events containing multiple sub-events.
- **Details**: Implement a transformer that produces multiple output records for each input record containing multiple sub-events.

### 5. Stateful Anomaly Detection
- **Objective**: Detect and flag sudden spikes in a stream of numbers.
- **Details**: If a number is 2x bigger than the average of the previous 'n' numbers, flag it as an anomaly.

### 6. Sessionizing User Activity
- **Objective**: Detect user sessions and calculate their duration.
- **Details**: Based on a stream of user activities with timestamps, determine user sessions and their lengths.

### 7. Record Type Conversion
- **Objective**: Convert a stream of XML records to JSON.
- **Details**: Implement a transformer to convert records in XML format to JSON.

### 8. Priority Flagging
- **Objective**: Add a priority flag to tasks based on certain criteria.
- **Details**: Given a stream of tasks, if a task is due soon and marked as "important", give it a "high" priority flag.

### 9. Data Masking
- **Objective**: Mask sensitive information in a stream of user data.
- **Details**: For a stream of user data, mask information like credit card numbers and SSNs.

### 10. Event Aggregation
- **Objective**: Aggregate events by type and emit a summary.
- **Details**: Implement a transformer that aggregates events by type, emitting a summary record after every 'n' events or 't' time.

## Getting Started
*Instructions on setting up and running the exercises can be added here.*

## Contributors
- [Your Name](Your GitHub Profile Link)
