package com.kjc.workplus.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kjc.workplus.login.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
//	@Query("SELECT member FROM Member as member WHERE member.memberId = : memberId")
//	Member findByMemberId(@Param("memberId") String memberId);
	
	Member findMemberByMemberId(String memberId);
	
	@Modifying
	@Query("UPDATE Member members SET members.password = :password WHERE members.memberId = :memberId")
	int updatePassword(@Param("password") String password, @Param("memberId") String memberId);
	
}
