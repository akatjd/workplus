package com.kjc.workplus.notice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.dto.NoticeSaveRequestDto;
import com.kjc.workplus.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/workplus/notice")
@Controller
public class NoticeController {
	
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
	
	/**
	 * 공지사항 글 등록
	 */
    @PostMapping(value = "/register.do")
    public String openNoticeRegister(NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
    	
    	if(noticeSaveRequestDto.getSeq() == null) {
    		
    		noticeService.save(noticeSaveRequestDto);
    		
    		List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
    		
    		model.addAttribute("noticeDtoList", noticeDtoList);
    		
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
