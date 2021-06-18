package com.kjc.workplus.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kjc.workplus.login.domain.Member;
import com.kjc.workplus.login.repository.MemberRepository;

@Service
public class LoginService implements UserDetailsService {
	
	@Autowired
	MemberRepository memberRepository;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("== loadUserByUsername ==");
		
		Member member = memberRepository.findByMemberId(username);
		
		if(member == null) {
			
			log.debug("== 계정정보가 존재하지 않음 ==");
			throw new UsernameNotFoundException(username);
		}
		
		return member;
		
	}
}
