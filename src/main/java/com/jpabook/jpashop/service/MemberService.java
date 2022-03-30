package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepositoryOld;
import com.jpabook.jpashop.repository.query.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.jpabook.jpashop.service
 * fileName       : MemberService
 * author         : Jihun Park
 * date           : 2022/03/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/17        Jihun Park       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member the member
     * @return the long
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증!
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {  //TODO 카운트 코드로
        List<Member> findMembers= memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        //return memberRepository.findOne(memberId);
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        //DB에서 꺼내서 1차캐시올리고 영속화해주고 @Transactional scope가 종료되면  관련 AOP가 끝나면서 트랜잭션 커밋이 되는 시점에 영속성 컨텍스트 flush()하면서 변경감지를 통해  db에 업데이트 쿼리가 나감
        //Member member = memberRepository.findOne(id);
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
