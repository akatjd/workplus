package com.kjc.workplus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kjc.workplus.login.domain.Member;
import com.kjc.workplus.login.repository.MemberRepository;

@SpringBootTest
class MypageTests {
	
	@Autowired
	private MemberRepository memberRepository;

	@Test
	void test() {
		Member member = new Member();
		
		member = memberRepository.findMembData("member02@gmail.com");
		
		System.out.println(member.getDeleteYn());
		System.out.println(member.getHphone());
		System.out.println(member.getMemberId());
		System.out.println(member.getNickname());
		System.out.println(member.getPassword());
		System.out.println(member.getUpdatedId());
		System.out.println(member.getMemberSeq());
		System.out.println(member.getPhoto());
	}

}
