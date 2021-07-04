package com.kjc.workplus.mypage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kjc.workplus.files.service.FilesService;
import com.kjc.workplus.login.service.MemberService;

@Controller
@RequestMapping("/workplus/mypage")
public class MypageController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FilesService filesService;
	
	@GetMapping("/manageProfile.do")
	public String updateProfile(Authentication authentication, Model model) {
		
		// UsernamePasswordAuthenticationToken에 넣었던 UserDetails 객체 반환
		UserDetails userVO = (UserDetails) authentication.getPrincipal();
		String memberId = userVO.getUsername();
		System.out.println("ID정보 : " + userVO.getUsername());

		// 프로필 사진 경로 가져와야함
		String fileCours = memberService.getFileCours(userVO.getUsername());
		
		String[] splitFile = fileCours.split("\\\\");
		
		fileCours = splitFile[splitFile.length-2] + "\\" + splitFile[splitFile.length-1];
		
		model.addAttribute("memberId", memberId);
		model.addAttribute("fileCours", fileCours);
		
		return "mypage/profile_manage";
	}
}
