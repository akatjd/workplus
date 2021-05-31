package com.kjc.workplus.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjc.workplus.notice.domain.Notice;
import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.dto.NoticeSaveRequestDto;
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
	
	/**
	 * 게시글 - 상세 조회
	 */
	@Transactional(readOnly = true)
	public NoticeResponseDto findById(Long noticeSeq) {
		
		Notice notice = noticeRepository.findById(noticeSeq)
										.orElseThrow(() -> new IllegalAccessError("[noticeSeq=" + noticeSeq + "] 해당 게시글이 존재하지 않습니다."));
		
		return new NoticeResponseDto(notice);
	}
	
	/** 게시글 - 등록 */
    @Transactional
    public Long save(NoticeSaveRequestDto noticeSaveRequestDto) {
 
        return noticeRepository.save(noticeSaveRequestDto.toEntity())
                              .getSeq();
    }
}
