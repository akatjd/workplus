package com.kjc.workplus.login.controller;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kjc.workplus.files.dto.FilesDto;
import com.kjc.workplus.files.service.FilesService;
import com.kjc.workplus.files.utils.MD5Generator;
import com.kjc.workplus.login.dto.MemberDto;
import com.kjc.workplus.login.service.MemberService;

@Controller
@RequestMapping("/workplus")
public class MemberController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FilesService filesService;
	
	@GetMapping("/main.do")
    public String homeView() {
		
        return "member/home";
        
    }

    @GetMapping("/login.do")
    public String loginView() {
    	
        return "member/login";
        
    }
    
    @PostMapping("/login.do")
    public String loginView(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model) {

        return "member/login";
        
    }

    @GetMapping("/signup.do")
    public String signupView() {
    	
        return "member/signup";
        
    }

    @PostMapping("/signup.do")
    public String signup(@RequestParam("file") MultipartFile[] file, MemberDto memberDto) {
    	
    	try {
			FilesDto filesDto = new FilesDto();
			
			String originFileName = file[0].getOriginalFilename();
			
			if(originFileName != null) {
				String streFileName = new MD5Generator(originFileName).toString();
    			/* 오늘 날짜 */
    			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    			/* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
    			String savePath = Paths.get("C:", "workplus", "files", today).toString();
    			/* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
    			if(!new File(savePath).exists()) {
    				try {
    					new File(savePath).mkdir();
    				}catch(Exception e) {
    					e.getStackTrace();
    				}
    			}
    			
    			String fileStreCours = savePath + "\\" + streFileName;
    			file[0].transferTo(new File(fileStreCours));
    			
    			filesDto.setCategorySeq(memberService.findSeqIncrement());
    			filesDto.setCategory("MEMBER");
    			filesDto.setFileNumber(1);
    			filesDto.setOriginFileName(originFileName);
    			filesDto.setStreFileName(streFileName);
    			filesDto.setFileStreCours(fileStreCours);
    			filesDto.setFileSize(Long.valueOf(file[0].getSize()).intValue());
    			
        		filesService.saveFiles(filesDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
    	
        memberService.save(memberDto);
        return "redirect:/workplus/login.do";
        
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/member/info.do")
    public String userInfoView() {
    	
        return "member/user_info";
        
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin.do")
    public String adminView() {
    	
        return "member/admin";
        
    }

    @GetMapping("/denied.do")
    public String deniedView() {
    	
        return "member/denied";
        
    }
    
    // 아이디 중복 체크
    @ResponseBody
    @RequestMapping(value="/idChk.do", method = RequestMethod.POST)
    public int idChk(MemberDto memberDto) throws Exception {
    	int result = memberService.idChk(memberDto.getMemberId());
    	System.out.println(result);
    	return result;
    }

}
