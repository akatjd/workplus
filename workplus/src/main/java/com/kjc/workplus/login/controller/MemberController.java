package com.kjc.workplus.login.controller;

import java.io.File;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String homeView(Principal principal) {
		
//		System.out.println("principal getName() : "+principal.getName());
		
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
			
			String[] splitFile = originFileName.split("\\.");
			
			if(originFileName != null) {
				String streFileName = new MD5Generator(originFileName).toString();
				streFileName = streFileName + "." + splitFile[splitFile.length-1];
    			/* ?????? ?????? */
    			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    			/* ???????????? ????????? 'files' ????????? ????????? ???????????????. */
    			String savePath = Paths.get("C:", "workplus", "files", today).toString();
    			/* ????????? ???????????? ????????? ????????? ????????? ???????????????. */
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
    
    /**
     * ???????????? ?????????
     */
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/member/info.do")
    public String userInfoView(Authentication authentication, Model model) {
    	
    	System.out.println("???????????? : " + authentication.getClass());
    	
    	// ?????? ?????? ?????? ??????
		WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
		System.out.println("??????ID : " + web.getSessionId());
		System.out.println("??????IP : " + web.getRemoteAddress());

		// UsernamePasswordAuthenticationToken??? ????????? UserDetails ?????? ??????
		UserDetails userVO = (UserDetails) authentication.getPrincipal();
		System.out.println("ID?????? : " + userVO.getUsername());
		
		// ????????? ?????? ?????? ???????????????
		String fileCours = memberService.getFileCours(userVO.getUsername());
		
		String[] splitFile = fileCours.split("\\\\");
		
		for(int i=0; i<splitFile.length; i++) {
			System.out.println(splitFile[i]);
		}
		
		fileCours = splitFile[splitFile.length-2] + "\\" + splitFile[splitFile.length-1]; 
		
		log.info(fileCours);
		
		model.addAttribute("fileCours", fileCours);
    	
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
    
    // ????????? ?????? ??????
    @ResponseBody
    @PostMapping("/idChk.do")
    public int idChk(String memberId) throws Exception {
    	
    	int result = memberService.idChk(memberId);
    	return result;
    	
    }
    
    // ????????? ?????? ??????
    @ResponseBody
    @PostMapping("/nickChk.do")
    public int nickChk(String nickname) throws Exception {
    	
    	System.out.println(nickname);
    	
    	int result = memberService.nickChk(nickname);
    	return result;
    	
    }

}
