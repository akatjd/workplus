package com.kjc.workplus.notice.dto;

import java.time.LocalDateTime;

import com.kjc.workplus.notice.domain.Notice;

import lombok.Getter;

@Getter
public class NoticeResponseDto {
	
	private Long seq;
	private String title;
	private String content;
	private Long viewCnt;
	private String writer;
	private LocalDateTime createdDate;
	private String deleteYn;
	private String updatedId;
	private LocalDateTime updatedDate;
	private String fileId;
	
	public NoticeResponseDto(Notice notice) {
		this.seq = notice.getSeq();
		this.title = notice.getTitle();
		this.content = notice.getContent();
		this.viewCnt = notice.getViewCnt();
		this.writer = notice.getWriter();
		this.createdDate = notice.getCreatedDate();
		this.deleteYn = notice.getDeleteYn();
		this.updatedId = notice.getUpdatedId();
		this.updatedDate = notice.getUpdatedDate();
		this.fileId = notice.getFileId();
	}
}
