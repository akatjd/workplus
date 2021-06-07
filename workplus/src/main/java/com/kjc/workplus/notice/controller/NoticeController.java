package com.kjc.workplus.notice.controller;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kjc.workplus.files.dto.FilesDto;
import com.kjc.workplus.files.service.FilesService;
import com.kjc.workplus.files.utils.MD5Generator;
import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.dto.NoticeSaveRequestDto;
import com.kjc.workplus.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/workplus/notice")
@Controller
public class NoticeController {
	
	private final FilesService filesService;
	private final NoticeService noticeService;
	
	/**
	 * 공지사항 리스트 확인
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String openNoticeList(Model model) {
		
		List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
		
		model.addAttribute("noticeDtoList", noticeDtoList);
		
		return "noticeList";
	}
	
	/**
	 * 공지사항 상세내용 확인
	 */
	@GetMapping(value = "/view.do")
	public String openNoticeDetail(@RequestParam(value = "noticeSeq", required = false) Long noticeSeq, Model model) {
		
		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);

		model.addAttribute("noticeResponseDto", noticeResponseDto);
		
//		List<AttachDTO> fileList = boardService.getAttachFileList(seq); // 추가된 로직
//		model.addAttribute("fileList", fileList); // 추가된 로직

		return "noticeView";
	}
	
	/**
	 * 공지사항 글쓰기 화면 출력
	 */
	@GetMapping(value = "/write.do")
	public String openNoticeWrite(@RequestParam(value = "noticeSeq", required = false) Long noticeSeq, NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
		
		if(noticeSeq == null) {
			
			return "noticeWrite";
		}else {
			NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);

			model.addAttribute("noticeResponseDto", noticeResponseDto);
			
			return "noticeWrite";
		}
	}
	
//	/**
//	 * 공지사항 글 등록
//	 */
//    @PostMapping(value = "/register.do")
//    public String openNoticeRegister(NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
//    	
//    	if(noticeSaveRequestDto.getSeq() == null) {
//    		
//    		noticeService.save(noticeSaveRequestDto);
//    		
//    		List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
//    		
//    		model.addAttribute("noticeDtoList", noticeDtoList);
//    		
//    		return "noticeList";
//    	}else {
//    		
//    		noticeService.update(noticeSaveRequestDto);
//    		
//    		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSaveRequestDto.getSeq());
//
//    		model.addAttribute("noticeResponseDto", noticeResponseDto);
//    		
//    		return "noticeView";
//    	}
//    }
    
    /**
	 * 공지사항 글 등록
	 */
    @PostMapping(value = "/register.do")
    public String openNoticeRegister(@RequestParam("file") MultipartFile[] files, NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
    	
    	if(noticeSaveRequestDto.getSeq() == null) {
    		try {
    			FilesDto filesDto = new FilesDto();
    			
    			System.out.println("files.length : " + files.length);
    			
    			String originFileName = files[0].getOriginalFilename();
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
    			files[0].transferTo(new File(fileStreCours));

    			filesDto.setOriginFileName(originFileName);
    			filesDto.setStreFileName(streFileName);
    			filesDto.setFileStreCours(fileStreCours);
    			filesDto.setFileSize(Long.valueOf(files[0].getSize()).intValue());
    			
        		filesService.saveFiles(filesDto);
        		
        		noticeService.save(noticeSaveRequestDto);
        		
        		List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
        		
        		model.addAttribute("noticeDtoList", noticeDtoList);
    			
    		}catch(Exception e) {
    			
    			e.printStackTrace();
    			
    		}
    		
    		return "noticeList";
    	}else {
    		
    		noticeService.update(noticeSaveRequestDto);
    		
    		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSaveRequestDto.getSeq());

    		model.addAttribute("noticeResponseDto", noticeResponseDto);
    		
    		return "noticeView";
    	}
    }
    
    /**
     * 공지사항 글 삭제하기
     */
    @GetMapping(value = "/delete.do")
    public String delete(@RequestParam("noticeSeq") Long noticeSeq, Model model) {
 
        noticeService.updateDeleteYn(noticeSeq);
        
//      	List<NoticeResponseDto> noticeDtoList = noticeService.findAll();
        List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
		
		model.addAttribute("noticeDtoList", noticeDtoList);
 
        return "noticeList";
    }
}
