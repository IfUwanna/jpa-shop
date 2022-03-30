package com.jpabook.jpashop.repository.query;

import com.jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.jpabook.jpashop.repository.query
 * fileName       : MemberRepository
 * author         : Jihun Park
 * date           : 2022/03/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/30        Jihun Park       최초 생성
 */
public interface MemberRepository extends JpaRepository<Member,Long> { // Type, PKType
    // select m form Member m where m.name = ? 변환해줌!
    List<Member> findByName (String name);
}
