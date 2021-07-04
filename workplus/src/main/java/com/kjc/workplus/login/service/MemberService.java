package com.kjc.workplus.login.service;

import java.util.ArrayList;
import java.util.List;

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

import com.kjc.workplus.files.repository.FilesRepository;
import com.kjc.workplus.login.domain.Member;
import com.kjc.workplus.login.domain.MemberAuthority;
import com.kjc.workplus.login.dto.MemberDto;
import com.kjc.workplus.login.repository.MemberAuthorityRepository;
import com.kjc.workplus.login.repository.MemberRepository;

@Service
public class MemberService implements UserDetailsService {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberAuthorityRepository memberAuthorityRepository;
	
	@Autowired
	FilesRepository filesRepository;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("== loadUserByUsername ==");
		
		Member member = memberRepository.findMemberByMemberId(username);
		
		MemberAuthority memberAuthority = memberAuthorityRepository.findByMemberId(username);
		
		log.info("authority.getAuthorityName() : " + memberAuthority.getAuthorityName());
		
		if(member == null) {
			
			log.debug("== 계정정보가 존재하지 않음 ==");
			throw new UsernameNotFoundException(username);
			
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority sg = new SimpleGrantedAuthority(memberAuthority.getAuthorityName());
		authorities.add(sg);
		
		User user = new User(member.getMemberId(), member.getPassword(), authorities);
		
		System.out.println(user);

		return user;
		
	}
	
	@Transactional
	public void save(MemberDto memberDto) {
		
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
		memberDto.setDeleteYn("N");
		memberDto.setPhoto("test");
		memberDto.setUpdatedId("admin");
		
		memberAuthorityRepository.save(MemberAuthority.builder()
								 .memberId(memberDto.getMemberId())
								 .authorityName("ROLE_MEMBER")
								 .build());												
		
		Member member = memberDto.toEntity();

		memberRepository.save(member).getMemberSeq();
	}
	
	@Transactional
    public Long findSeqIncrement() {
		
    	return (memberRepository.findSeqIncrement()+1);
    	
    }
	
	@Transactional
	public int idChk(String memberId) {
		
		System.out.println("memberId service : " + memberId);
		
		return memberRepository.idChk(memberId);
		
	}
	
	@Transactional
	public int nickChk(String nickname) {
		
		return memberRepository.nickChk(nickname);
		
	}
	
	public String getFileCours(String memberId) {
		
		// memberId로 memberSeq 값 가져오기
		int memberSeq = memberRepository.getMemberSeq(memberId);
		
		// 프로필사진 경로 가져오기
		String fileCours = filesRepository.getFileCours(Long.valueOf(memberSeq), "MEMBER");
		
		return fileCours;
	}
}
