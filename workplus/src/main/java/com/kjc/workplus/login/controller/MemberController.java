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

import com.kjc.workplus.constant.Method;
import com.kjc.workplus.files.dto.FilesDto;
import com.kjc.workplus.files.service.FilesService;
import com.kjc.workplus.files.utils.MD5Generator;
import com.kjc.workplus.login.domain.Member;
import com.kjc.workplus.login.dto.MemberDto;
import com.kjc.workplus.login.service.MemberService;
import com.kjc.workplus.util.UiUtils;

@Controller
@RequestMapping("/workplus")
public class MemberController extends UiUtils {
	
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
    
    /**
     * 회원정보 페이지
     */
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/member/info.do")
    public String userInfoView(Authentication authentication, Model model) {
    	
    	System.out.println("타입정보 : " + authentication.getClass());
    	
    	// 세션 정보 객체 반환
		WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
		System.out.println("세션ID : " + web.getSessionId());
		System.out.println("접속IP : " + web.getRemoteAddress());

		// UsernamePasswordAuthenticationToken에 넣었던 UserDetails 객체 반환
		UserDetails userVO = (UserDetails) authentication.getPrincipal();
		System.out.println("ID정보 : " + userVO.getUsername());
		
		// 프로필 사진 경로 가져와야함
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
    
    // 아이디 중복 체크
    @ResponseBody
    @PostMapping("/idChk.do")
    public int idChk(String memberId) throws Exception {
    	
    	int result = memberService.idChk(memberId);
    	return result;
    	
    }
    
    // 닉네임 중복 체크
    @ResponseBody
    @PostMapping("/nickChk.do")
    public int nickChk(String nickname) throws Exception {
    	
    	System.out.println(nickname);
    	
    	int result = memberService.nickChk(nickname);
    	return result;
    	
    }
    
    // 마이페이지 프로필 관리창 열기
    @GetMapping("/member/manageProfile.do")
	public String manageProfile(Authentication authentication, Model model) {
		
		// UsernamePasswordAuthenticationToken에 넣었던 UserDetails 객체 반환
		UserDetails userVO = (UserDetails) authentication.getPrincipal();
		String memberId = userVO.getUsername();
		System.out.println("ID정보 : " + userVO.getUsername());
		
		// 회원정보 가져오기
		Member member = memberService.getMembData(memberId);

		// 프로필 사진 경로 가져오기
		String fileCours = memberService.getFileCours(userVO.getUsername());
		
		String[] splitFile = fileCours.split("\\\\");
		
		fileCours = splitFile[splitFile.length-2] + "\\" + splitFile[splitFile.length-1];
		
		model.addAttribute("member", member);
		model.addAttribute("fileCours", fileCours);
		
		return "mypage/profile_manage";
	}
    
    // 마이페이지 프로필 업데이트 하기
    @PostMapping("/member/updateProfile.do")
	public String updateProfile(@RequestParam("file") MultipartFile[] file, MemberDto memberDto, Model model) {
    	
    	boolean pwChk = memberService.pwChk(memberDto.getMemberId(), memberDto.getPassword());
    	int nickChk = memberService.nickChk(memberDto.getNickname());
    	
    	if(pwChk == false) {
    		
    		return showMessageWithRedirect("비밀번호가 틀렸습니다.", "/workplus/member/manageProfile.do", Method.GET, null, model);
    		
    	}else if(nickChk == 1) {
    		return showMessageWithRedirect("중복 닉네임입니다.", "/workplus/member/manageProfile.do", Method.GET, null, model);
    	}
    	
    	int resultUpdate = memberService.updateProfile(memberDto.getMemberId(), memberDto.getNickname(), memberDto.getHphone()); 
    	
    	if(resultUpdate == 1) {
    		
    		return showMessageWithRedirect("수정 되었습니다.", "/workplus/member/manageProfile.do", Method.GET, null, model);
    		
    	}else {
    		
    		return showMessageWithRedirect("프로필 수정에 실패했습니다.", "/workplus/member/manageProfile.do", Method.GET, null, model);
    		
    	}
    	
	}
    
    // 비밀번호 변경 팝업 띄우기
    @GetMapping("/member/update_pw_pop_up.do")
    public String updatePwPopUp() {
    	
    	return "mypage/update_pw_pop_up";
    	
    }

}
