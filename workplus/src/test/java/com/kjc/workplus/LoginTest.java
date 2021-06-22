package com.kjc.workplus;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.kjc.workplus.login.domain.Member;
import com.kjc.workplus.login.domain.MemberAuthority;
import com.kjc.workplus.login.repository.MemberAuthorityRepository;
import com.kjc.workplus.login.repository.MemberRepository;

@SpringBootTest
public class LoginTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberAuthorityRepository memberAuthorityRepository;
	
	@Test
	public void memberInsertTest() {
		
		// 어드민 계정 생성
		memberRepository.save(Member.builder()
				.memberId("admin")
				.nickname("맘성")
				.password("dustkafh61!")
				.photo(null)
				.hphone("01012341234")
				.deleteYn("N")
				.regDate(LocalDateTime.now())
				.updatedId("test")
				.updatedDate(LocalDateTime.now())
				.build());
		
		// 일반 유저 생성
		memberRepository.save(Member.builder()
				.memberId("testUser01")
				.nickname("테스트유저01")
				.password("dustkafh61!")
				.photo(null)
				.hphone("01055671123")
				.deleteYn("N")
				.regDate(LocalDateTime.now())
				.updatedId("test")
				.updatedDate(LocalDateTime.now())
				.build());
		
	}
	
	@Test
	public void memberAuthorityInsertTest() {
		
		// admin 계정 ADMIN 권한 삽입
		memberAuthorityRepository.save(MemberAuthority.builder()
				.memberId("admin")
				.authorityName("ROLE_ADMIN")
				.build());
		
		// testUser01 MEMBER 권한 삽입
		memberAuthorityRepository.save(MemberAuthority.builder()
				.memberId("testUser01")
				.authorityName("ROLE_MEMBER")
				.build());
		
	}
	
	@Test
	public void adminAuthorityInsertTest() {

		memberAuthorityRepository.save(MemberAuthority.builder()
				 .memberId("admin01")
				 .authorityName("ROLE_ADMIN")
				 .build());			
		
	}
	
	@Transactional
	@Test
	@Rollback(false)
	public void updateAuthorityName() {
		
		memberAuthorityRepository.updateAuthorityName("ROLE_ADMIN", "admin01@gmail.com");
		
	}
	
	@Test
	public void idCheck() {
		System.out.println(memberRepository.idChk("admin01@gmail.com"));
	}
}
