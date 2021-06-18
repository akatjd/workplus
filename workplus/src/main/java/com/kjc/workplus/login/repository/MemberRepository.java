package com.kjc.workplus.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kjc.workplus.login.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	@Query("SELECT member FROM Member member WHERE member.memberId = :memberId")
	Member findByMemberId(@Param("memberId") String memberId);
}
