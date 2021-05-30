package com.kjc.workplus.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/workplus/notice")
@Controller
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Model model) {
		
		List<NoticeResponseDto> noticeDtoList = noticeService.findAll();
		
		model.addAttribute("noticeDtoList", noticeDtoList);
		
		return "main";
	}
	
	/**
	 * 게시글 - 목록 조회
	 */
	@GetMapping(value = "/list.do", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<NoticeResponseDto>> findAll() {
		
		List<NoticeResponseDto> noticeResponseDtoList = noticeService.findAll();
		
		return new ResponseEntity<List<NoticeResponseDto>>(noticeResponseDtoList, HttpStatus.OK);
	}
}
