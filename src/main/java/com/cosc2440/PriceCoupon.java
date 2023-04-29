package com.cosc2440;

public class PriceCoupon extends Coupon{
    private double couponValue;

    public PriceCoupon (double couponValue){
        super();
        this.couponValue = couponValue;
    }

    @Override
    public String toString(){
        return String.format("Coupon ID: %s, Coupon Type: Price Coupon , Coupon Value: %.0f",this.getId(),this.getCouponValue());
    }

    //Getters and Setters

    public double getCouponValue() {
        return this.couponValue;
    }

    public void setCouponValue(double couponValue) {
        this.couponValue = couponValue;
    }

}
