package Project101.Payment.dto;

import Common.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class PaymentEvent {
    Account sourceAccount;
    Account destinationAccount;
    String amount;
    PaymentType paymentType;
    Map<String,String> paymentDetails;
}
