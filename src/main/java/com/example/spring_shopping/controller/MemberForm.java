package com.example.spring_shopping.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.") //필수값 지정
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
