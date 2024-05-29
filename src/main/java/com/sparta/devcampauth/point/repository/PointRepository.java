package com.sparta.devcampauth.point.repository;

import com.sparta.devcampauth.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository <Point, Long> {
}
