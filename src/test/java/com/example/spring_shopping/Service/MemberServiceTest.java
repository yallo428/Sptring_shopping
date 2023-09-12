package com.example.spring_shopping.Service;

import com.example.spring_shopping.domain.Member;
import com.example.spring_shopping.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 테스트에서는 롤백을 시킨다
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);


        Assertions.assertEquals(member, memberRepository.findOne(saveId));

    }
    @Test() //junit4에서 지원하는 기능으로 이 오류가 뜰 경우 성공표시
    public void 중복_회원_예약(){
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        memberService.join(member2);


        fail("예외 발생");
    }
}