package Project101.Payment.Services;

import Project101.Payment.Repository.DataRepository;


public class LedgerService {
    // This class would be responsible for recording all transactions in a ledger.
    // It could interact with a DataRepository to persist transaction details.
    //account , Debit , Credit
    //Buyer   , $1    , ______
    //Seller  , ______, $1
    DataRepository repository;
    public LedgerService(DataRepository repository){
        this.repository = repository;
    }
    public void updatePayment(){

    }
}
