# Real-time Order Processing System:

## Scenario:
An e-commerce platform publishes all placed orders to a Kafka topic. 
These orders contain information like 
    1.orderId, 
    2.userId, 
    3.productId 
    4.amount. 
Your task is to build a stream processing application that:
Counts the number of orders placed by each userId in real-time.
Calculates the total amount spent by each userId in real-time.
Implementation Steps:

Read from the Kafka topic that contains the order data.
Create a key-value pair with userId as the key and the order amount as the value.
Use the groupByKey method to group the orders by userId.
Use the count method to count the number of orders for each userId.
Use the reduce method to calculate the total amount spent by each userId.
Write the output back to a new Kafka topic.