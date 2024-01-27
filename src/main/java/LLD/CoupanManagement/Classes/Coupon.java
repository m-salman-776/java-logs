package LLD.CoupanManagement.Classes;

public class Coupon {
    int id;
    String code;
    double discount;
    int usesLimit ;
    int useCunt;
    long expiryDate;
    public Coupon(String code, double discount, int usesLimit, int expiryDate){
        this.code = code;
        this.discount = discount;
        this.usesLimit=usesLimit;
        this.useCunt = 0;
        this.expiryDate = expiryDate;
    }
}
