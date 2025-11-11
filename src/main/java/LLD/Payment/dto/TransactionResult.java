package LLD.Payment.dto;

public class TransactionResult {
    private final String transactionId;
    private final TransactionStatus status;
    private final String message;

    public TransactionResult(String transactionId, TransactionStatus status, String message) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
    }

    public String getTransactionId() { return transactionId; }
    public TransactionStatus getStatus() { return status; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "TransactionResult{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
