package Project101.PaymentSystem.DTO;

public enum PaymentStatus {
    CREATED, // 1. User click on Pay
    PENDING, // 2. we sent the request to gateway & waiting
    AUTHORIZED, //3. validate by gateway and money in on hold
    CAPTURED, //4. money is moved to our back account 'success'
    FAILED, // Optional Declined by bank, timeout, or invalid details.
    UNAVAILABLE
}
