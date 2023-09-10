package com.example.spring_shopping.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 데이터베이스에 객체를 저장하기 위해 사용
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /*
    값 타입은 변경 불가능하게 설계해야 한다.
     */
    protected Address(){}
}
