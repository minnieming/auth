package com.sparta.devcampauth.coupon.entity;

import com.sparta.devcampauth.signup.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (EnumType.STRING)
    private CouponType type;

    private double couponDiscount; // 할인 금액, 할인 비율

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

}
