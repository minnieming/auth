package com.sparta.devcampauth.coupon.repository;

import com.sparta.devcampauth.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository <Coupon, Long> {
}
