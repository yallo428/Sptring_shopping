package com.example.spring_shopping.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;


    @ManyToOne // 다대일 관계 선언
    @JoinColumn(name = "member_id") //fk키 이름 설정
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status; // 주문 상태

}
