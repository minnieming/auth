package com.sparta.devcampauth.coupon.service;

import com.sparta.devcampauth.coupon.dto.CouponRequest;
import com.sparta.devcampauth.coupon.dto.PaymentResponse;
import com.sparta.devcampauth.coupon.entity.CouponType;
import com.sparta.devcampauth.coupon.repository.CouponRepository;
import com.sparta.devcampauth.signup.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class CouponService {

    private CouponRepository couponRepository;
    private UserRepository userRepository;


    public PaymentResponse discountCoupon(CouponRequest couponRequest) {

        String coupon = String.valueOf(couponRequest.getCouponType());
        double price = couponRequest.getPrice();
        double finalPrice = 0;

        if (coupon != null) {
            if (coupon.equals(CouponType.PERCENTAGE.toString())) {
                finalPrice = price - (price * (couponRequest.getCouponDiscount() / 100));
            } else if (coupon.equals(CouponType.FIXED.toString())) {
                finalPrice = price - couponRequest.getCouponDiscount();
            }
        }

      return new PaymentResponse(finalPrice);
    }
}
