package com.sparta.devcampauth.point.service;

import com.sparta.devcampauth.coupon.dto.CouponRequest;
import com.sparta.devcampauth.coupon.dto.PaymentResponse;
import com.sparta.devcampauth.coupon.service.CouponService;
import com.sparta.devcampauth.point.dto.DoubleDiscountRequest;
import com.sparta.devcampauth.point.dto.PointRequest;
import com.sparta.devcampauth.point.repository.PointRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class PointService {

    private CouponService couponService;
    private PointRepository pointRepository;

    @Autowired
    public PointService(CouponService couponService) {
        this.couponService = couponService;
    }

    public PaymentResponse discountPoint(PointRequest pointRequest) {

        double price = pointRequest.getPrice();
        double points = pointRequest.getPoints();
        double finalPrice = 0;

        if (points > 0) {
            finalPrice = price - points;
        }

        return new PaymentResponse(finalPrice);
    }

    public PaymentResponse doubleDiscount(DoubleDiscountRequest doubleDiscountRequest) {

        CouponRequest couponRequest = new CouponRequest(
                doubleDiscountRequest.getPrice(),
                doubleDiscountRequest.getCouponType(),
                doubleDiscountRequest.getCouponDiscount());
        double priceAfterCoupon = 0;

        // 1. 쿠폰 적용
        if (doubleDiscountRequest.getCouponType() != null) {
            priceAfterCoupon = couponService.discountCoupon(couponRequest).getFinalPrice();
        }

        double finalPrice = 0;
        // 2. 포인트 적용
        if (doubleDiscountRequest.getPoints() > 0) {
            finalPrice = priceAfterCoupon - doubleDiscountRequest.getPoints();
        }

        return new PaymentResponse(finalPrice);

    }
}
