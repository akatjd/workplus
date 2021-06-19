package com.kjc.workplus.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kjc.workplus.login.domain.Member;
import com.kjc.workplus.login.dto.MemberDto;
import com.kjc.workplus.login.repository.MemberRepository;

@Service
public class MemberService implements UserDetailsService {
	
	@Autowired
	MemberRepository memberRepository;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("== loadUserByUsername ==");
		
		Member member = memberRepository.findMemberByMemberId(username);
		
		if(member == null) {
			
			log.debug("== 계정정보가 존재하지 않음 ==");
			throw new UsernameNotFoundException(username);
			
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority sg = new SimpleGrantedAuthority("ROLE_MEMBER");
		authorities.add(sg);
		
		System.out.println(member.getMemberId());
		System.out.println(member.getPassword());
		System.out.println(authorities);
		
		return new User(member.getMemberId(), member.getPassword(), authorities);
		
	}
	
	@Transactional
	public Long save(MemberDto memberDto) {
		
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
		memberDto.setDeleteYn("N");
		memberDto.setPhoto("test");
		memberDto.setUpdatedId("admin");
		
		Member member = memberDto.toEntity();

//		memberRepository.updatePassword(passwordEncoded, member.getMemberId()); // 암호화된 비밀번호 업데이트
		
		return memberRepository.save(member).getMemberSeq();
	}
}
