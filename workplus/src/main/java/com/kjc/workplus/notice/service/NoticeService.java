package com.kjc.workplus.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	/**
	 * 게시글 - 목록 조회
	 */
	@Transactional(readOnly = true)
	public List<NoticeResponseDto> findAll() {
		
		return noticeRepository.findAll()
								.stream()
								.map(NoticeResponseDto::new)
								.collect(Collectors.toList());
	}
}
