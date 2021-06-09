package com.kjc.workplus.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjc.workplus.notice.domain.Notice;
import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.dto.NoticeSaveRequestDto;
import com.kjc.workplus.notice.repository.NoticeRepository;

//@RequiredArgsConstructor
@Service
public class NoticeService {
	
	private NoticeRepository noticeRepository;
	
	public NoticeService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	
	/**
	 * 공지사항 목록 조회
	 */
	@Transactional(readOnly = true)
	public List<NoticeResponseDto> findAllNature() {
		
		return noticeRepository.findAllNature()
								.stream()
								.map(NoticeResponseDto::new)
								.collect(Collectors.toList());
	}
	
	/**
	 * 공지사항 상세 조회
	 */
	@Transactional(readOnly = true)
	public NoticeResponseDto findById(Long noticeSeq) {
		
		Notice notice = noticeRepository.findById(noticeSeq)
										.orElseThrow(() -> new IllegalAccessError("[noticeSeq=" + noticeSeq + "] 해당 게시글이 존재하지 않습니다."));
		
		return new NoticeResponseDto(notice);
	}
	
	/**
	 * 공지사항 등록
	 */
    @Transactional
    public Long save(NoticeSaveRequestDto noticeSaveRequestDto) {
 
        return noticeRepository.save(noticeSaveRequestDto.toEntity())
                              .getSeq();
    }
    
    /**
     * 공지사항 삭제
     */
    @Transactional
    public void delete(Long noticeSeq) {
 
        Notice notice = noticeRepository.findById(noticeSeq)
                                     .orElseThrow(() -> new IllegalAccessError("[noticeSeq=" + noticeSeq + "] 해당 게시글이 존재하지 않습니다."));
 
        noticeRepository.delete(notice);
    }
    
    @Transactional
    public void update(NoticeSaveRequestDto noticeSaveRequestDto) {
    	
    	noticeRepository.update(noticeSaveRequestDto.getSeq(), noticeSaveRequestDto.getTitle(), noticeSaveRequestDto.getContent());
    }
    
    @Transactional
    public void updateViewCnt(Long noticeSeq) {
    	
    	noticeRepository.updateViewCnt(noticeSeq);
    }
    
    @Transactional
    public void updateDeleteYn(Long noticeSeq) {
    
    	noticeRepository.updateDeleteYn(noticeSeq, "Y");
    }
    
    @Transactional
    public Long findSeqIncrement() {
    	return (noticeRepository.findSeqIncrement()+1);
    }
}
