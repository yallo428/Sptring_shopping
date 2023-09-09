package com.example.spring_shopping;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // 테스트 코드에 있으면, 작동이 끝나고 롤백시킨다
    @Rollback(false) // 롤백 방지
    public void testMember(){
        Member member = new Member();
        member.setUsername("memberA");

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(findMember).isEqualTo(member);
        /*
            id값이 같으면 같은 엔티티(객체)로 인식한다
         */
    }
}