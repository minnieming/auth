package com.sparta.devcampauth.point.controller;

import com.sparta.devcampauth.coupon.dto.PaymentResponse;
import com.sparta.devcampauth.point.dto.DoubleDiscountRequest;
import com.sparta.devcampauth.point.dto.PointRequest;
import com.sparta.devcampauth.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/payment")
public class PointController {

    private final PointService pointService;

    @PostMapping ("/point")
    public ResponseEntity <PaymentResponse> discountPoint (@RequestBody PointRequest pointRequest) {
        PaymentResponse paymentResponse = pointService.discountPoint (pointRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping ("/doubleDiscount")
    public ResponseEntity <PaymentResponse> doubleDiscount (@RequestBody DoubleDiscountRequest doubleDiscountRequest) {
        PaymentResponse paymentResponse = pointService.doubleDiscount (doubleDiscountRequest);
        return ResponseEntity.ok(paymentResponse);
    }
}
