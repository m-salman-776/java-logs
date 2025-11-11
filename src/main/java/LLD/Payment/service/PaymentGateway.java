package LLD.Payment.service;

import LLD.Payment.dto.CreditCard;
import LLD.Payment.dto.TransactionResult;

public interface PaymentGateway {
    TransactionResult charge(double amount, CreditCard creditCard);
    void refund(String transactionId, double amount);
}
