package Project101.Payment.dto;

import lombok.Getter;

@Getter
public class TransactionResult {
    private final String transactionId;
    private final TransactionStatus status;
    private final String message;

    public TransactionResult(String transactionId, TransactionStatus status, String message) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "TransactionResult{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
