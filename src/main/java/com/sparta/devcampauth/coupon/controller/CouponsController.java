package com.sparta.devcampauth.coupon.controller;

import com.sparta.devcampauth.coupon.dto.CouponRequest;
import com.sparta.devcampauth.coupon.dto.PaymentResponse;
import com.sparta.devcampauth.coupon.entity.Coupon;
import com.sparta.devcampauth.coupon.service.CouponService;
import com.sparta.devcampauth.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping (value = "/payment")
public class CouponsController {

    private final CouponService couponService;

    @PostMapping ("/coupon")
    public ResponseEntity <PaymentResponse> discountCoupon (
                                                         @RequestBody CouponRequest couponRequest) {
        return couponService.discountCoupon (couponRequest);
    }
}
