package com.kjc.workplus.notice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/workplus/notice")
@Controller
public class NoticeController {
	
	private Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private final NoticeService noticeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String openNoticeList(Model model) {
		
		List<NoticeResponseDto> noticeDtoList = noticeService.findAll();
		
		model.addAttribute("noticeDtoList", noticeDtoList);
		
		return "noticeList";
	}
	
	@GetMapping(value = "/view.do")
	public String openNoticeDetail(@RequestParam(value = "noticeSeq", required = false) Long noticeSeq, Model model) {
		
		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);

		model.addAttribute("noticeResponseDto", noticeResponseDto);
		
//		List<AttachDTO> fileList = boardService.getAttachFileList(seq); // 추가된 로직
//		model.addAttribute("fileList", fileList); // 추가된 로직

		return "noticeView";
	}
	
	@GetMapping(value = "/write.do")
	public String openNoticeWrite(Model model) {
		return "noticeWrite";
	}
	

//	/**
//	 * 게시글 - 목록 조회
//	 */
//	@GetMapping(value = "/list.do", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<List<NoticeResponseDto>> findAll() {
//		
//		List<NoticeResponseDto> noticeResponseDtoList = noticeService.findAll();
//		
//		return new ResponseEntity<List<NoticeResponseDto>>(noticeResponseDtoList, HttpStatus.OK);
//	}
	
//	@GetMapping(value = "/view/{noticeSeq}", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<NoticeResponseDto> findById(@PathVariable("noticeSeq") Long noticeSeq){
//		
//		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);
//		
//		return new ResponseEntity<NoticeResponseDto>(noticeResponseDto, HttpStatus.OK);
//	}
}
