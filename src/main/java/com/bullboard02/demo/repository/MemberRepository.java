package com.bullboard02.demo.repository;

import com.bullboard02.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    //JPA를 사용하는 이유 = repository를 사용하기 위해서
    //JpaRepository를 extends한 것만으로 데이터를 조작(https://blog.naver.com/rorean/221588251804)
    //repository에서 데이터를 다양하게 조회해보기

    boolean existsByUsername(String username); //이렇게 좆대로 해도 돼?

    Member findByUsername(String username);

    Member getOne(Long id);
    //->jpa 커스텀 메소드 작성할 수 있음.
    //    Member findByUserId(String username);
//    List<Member> findByUserIdAndPassword(String username, String password);
//    List<Member> findBySeqBetween(Long startId, Long endId);
//    List<Member> findByUserIdNotIn(String username);
//    List<Member> findByUserIdContaining(String username);
//    List<Member> findByNameIsNotNull();
//    List<Member> findBySeqAfter(Long id);

}
