package com.sparta.devcampauth.point.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private double points;

}
