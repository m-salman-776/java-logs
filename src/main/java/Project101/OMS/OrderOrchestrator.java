//@Component
//public class OrderSagaOrchestrator {
//
//    private final OrderRepository orderRepo;
//    private final InventoryClient inventoryClient;
//    private final PaymentClient paymentClient;
//    private final KafkaTemplate<String, Object> kafkaProducer;
//
//    // 1. Trigger: Order Created
////    @KafkaListener(topics = "order-events", groupId = "saga-group")
//    public void handleOrderEvents(OrderEvent event) {
//        switch (event.getType()) {
//            case ORDER_CREATED:
//                // Step 1: Trigger Inventory Reservation
//                commandReserveInventory(event.getOrderId());
//                break;
//
//            case ORDER_CANCELLED:
//                // Rollback flow
//                commandReleaseInventory(event.getOrderId());
//                break;
//        }
//    }
//
//    // 2. Response: Inventory Reserved (Success or Fail)
//    @KafkaListener(topics = "inventory-events", groupId = "saga-group")
//    public void handleInventoryEvents(InventoryEvent event) {
//        Order order = orderRepo.findById(event.getOrderId());
//
//        if (event.getStatus() == InventoryStatus.RESERVED) {
//            // Success! Move to Next Step: Payment
//            // Update Local State Machine (Optimistic Lock happens here)
//            orderService.updateState(order.getId(), Action.PAY);
//
//            // Command Payment
//            commandProcessPayment(order);
//        } else {
//            // Failure! (Out of Stock) -> Abort Order
//            orderService.updateState(order.getId(), Action.CANCEL);
//            // No rollback needed for inventory since it failed, but notify user
//        }
//    }
//
//    // 3. Response: Payment Processed (Success or Fail)
//    @KafkaListener(topics = "payment-events", groupId = "saga-group")
//    public void handlePaymentEvents(PaymentEvent event) {
//        Order order = orderRepo.findById(event.getOrderId());
//
//        if (event.getStatus() == PaymentStatus.SUCCESS) {
//            // Success! Move to Shipping
//            orderService.updateState(order.getId(), Action.SHIP);
//            commandShipOrder(order);
//        } else {
//            // FAILURE! (Insufficient Funds) -> START COMPENSATION
//            // 1. Cancel Order Locally
//            orderService.updateState(order.getId(), Action.CANCEL);
//
//            // 2. Trigger Compensation (Undo Inventory)
//            // We must explicitly tell Inventory Service to unlock the items
//            inventoryClient.releaseStock(order.getItems());
//        }
//    }
//}


