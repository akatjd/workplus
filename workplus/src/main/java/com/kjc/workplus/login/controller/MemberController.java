package com.kjc.workplus.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kjc.workplus.login.dto.MemberDto;
import com.kjc.workplus.login.service.MemberService;

@Controller
@RequestMapping("/workplus")
public class MemberController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/")
    public String homeView() {
        return "member/home";
    }

    @GetMapping("/login")
    public String loginView() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signupView() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto) {
    	
    	log.info("signup post 들어옴");
    	
        memberService.save(memberDto);
        return "redirect:/workplus/login";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/member/info")
    public String userInfoView() {
        return "member/user_info";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminView() {
        return "member/admin";
    }

    @GetMapping("/denied")
    public String deniedView() {
        return "member/denied";
    }

}
