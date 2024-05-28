package com.sparta.devcampauth.coupon.dto;

import com.sparta.devcampauth.coupon.entity.CouponType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponRequest {

    private double price;

    private CouponType couponType;

    private double couponDiscount;

}
