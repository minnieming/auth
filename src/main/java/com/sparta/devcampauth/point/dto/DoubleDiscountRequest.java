package com.sparta.devcampauth.point.dto;

import com.sparta.devcampauth.coupon.entity.CouponType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoubleDiscountRequest {

    private double price;

    private CouponType couponType;

    private double couponDiscount;

    private double points;

}
