package com.example.spring_shopping.Service;

import com.example.spring_shopping.domain.Member;
import com.example.spring_shopping.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/*
    데이터 변경같은 것은 트랙잭션 안에서 일어나야한다
    readOnly = 조회할 때 true로 읽기만 하기에 성능이 오른다
    join은 따로 먹힌다
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
        생성자 인젝션을 써주는게 좋다
     */

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    /*
        a라는 사람이 동시에 가입할 경우 오류가 날 수도 있기에,
        만약을 대비해 name(회원가입할 때 id)같은 것은 유니크로 설정하는 것이 좋다
     */
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
