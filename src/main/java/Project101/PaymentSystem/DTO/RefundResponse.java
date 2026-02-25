package Project101.PaymentSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefundResponse {
    int paymentId;
    PaymentStatus status;
}
