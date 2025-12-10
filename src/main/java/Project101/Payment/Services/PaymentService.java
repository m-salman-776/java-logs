package Project101.Payment.Services;

import Project101.Payment.Repository.DataRepository;
import Project101.Payment.dto.PaymentEvent;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.dto.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    // This class would orchestrate the payment process using different strategies
    LedgerService ledgerService;
    DataRepository repository;
    PaymentExecutorService paymentExecutorService;
    public PaymentService(LedgerService ledgerService, DataRepository repository){
        this.ledgerService = ledgerService;
        this.repository = repository;
    }
    public List<TransactionResult> processPayment(List<PaymentEvent> paymentEventList){
        // do risk analysis & validation;
        if (!doRiskCheck(paymentEventList)){
            return new ArrayList<>(List.of(new TransactionResult(null, TransactionStatus.FAILURE, "Risk check failed")));
        }
        List<TransactionResult> results = new ArrayList<>();
        for (PaymentEvent paymentEvent : paymentEventList) {
            // update ledger & other necessary db
            results.add(paymentExecutorService.executePayment(paymentEvent));
        }
        return results;
    }
    private boolean doRiskCheck(List<PaymentEvent> paymentEventList){
        return true;
    }

}
