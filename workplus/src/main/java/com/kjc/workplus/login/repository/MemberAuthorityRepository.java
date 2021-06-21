package com.kjc.workplus.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kjc.workplus.login.domain.MemberAuthority;

@Repository
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long>{
	
	MemberAuthority findByMemberId(String memberId);
	
	@Modifying
	@Query("UPDATE MemberAuthority ma SET ma.authorityName = :authorityName WHERE ma.memberId = :memberId")
	int updateAuthorityName(@Param("authorityName") String authorityName, @Param("memberId") String memberId);

}
