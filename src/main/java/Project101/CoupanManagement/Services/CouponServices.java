package Project101.CoupanManagement.Services;

import Project101.CoupanManagement.Classes.Coupon;

public class CouponServices {
    void createCoupon(String code,double discount,int usageLimit,int expiryDate){
        Coupon coupons = new Coupon(code,discount,usageLimit,expiryDate);
    }
    Coupon getCoupon(String code){
        return null;
    }
    boolean validateCoupon(String code){
        return  false;
    }
}
