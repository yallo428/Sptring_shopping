package com.example.spring_shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue

    @Column(name ="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //일대다 관계 선언 //관계 주인은 member가 아니다.
    private List<Order> orders = new ArrayList<Order>();



}
