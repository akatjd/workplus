package com.kjc.workplus.notice.controller;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kjc.workplus.notice.domain.MemberVO;
import com.kjc.workplus.notice.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("memberTest")
public class TestJpaRestController {
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MemberService memberService; 
	
	// 모든 회원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MemberVO>> getAllmembers() {
		List<MemberVO> member = memberService.findAll(); 
		
		return new ResponseEntity<List<MemberVO>>(member, HttpStatus.OK);
	} 
	
	// 회원번호로 한명의 회원 조회
	@GetMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<MemberVO> getMember(@PathVariable("mbrNo") Long mbrNo) {
		Optional<MemberVO> member = memberService.findById(mbrNo); 
		
		return new ResponseEntity<MemberVO>(member.get(), HttpStatus.OK); 
	}
	
	// 회원번호로 회원 삭제
	@DeleteMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) { 
		memberService.deleteById(mbrNo);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
	}
	
	// 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함) 
	@PutMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<MemberVO> updateMember(@PathVariable("mbrNo") Long mbrNo, MemberVO member) {
		memberService.updateById(mbrNo, member); 
		
		return new ResponseEntity<MemberVO>(member, HttpStatus.OK); 
	}
	
	// 회원 입력
	@PostMapping public ResponseEntity<MemberVO> save(MemberVO member) {
		return new ResponseEntity<MemberVO>(memberService.save(member), HttpStatus.OK); 
	} 
	
	// 회원 입력
	@RequestMapping(value="/saveMember", method = RequestMethod.GET) 
	public ResponseEntity<MemberVO> save(HttpServletRequest req, MemberVO member){
		return new ResponseEntity<MemberVO>(memberService.save(member), HttpStatus.OK);
	}
}
