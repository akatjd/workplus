package com.kjc.workplus.notice.dto;

import java.time.LocalDateTime;

import com.kjc.workplus.notice.domain.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NoticeSaveRequestDto {
	
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
	

//	@Builder
//	public NoticeSaveRequestDto(String title, String content, Long viewCnt, String writer, 
//			LocalDateTime createdDate, String deleteYn, String updatedId, LocalDateTime updatedDate, String fileId) {
//	  this.title = title;
//	  this.content = content;
//	  this.viewCnt = viewCnt;
//	  this.writer = writer;
//	  this.createdDate = createdDate;
//	  this.deleteYn = deleteYn;
//	  this.updatedId = updatedId;
//	  this.updatedDate = updatedDate;
//	  this.fileId = fileId;
//	}
	
	/**
	 * 공지사항 글쓰기 생성자
	 */
	@Builder
	public NoticeSaveRequestDto(String title, String content) {
	  this.title = title;
	  this.content = content;
	}
	
	@Builder
	public NoticeSaveRequestDto(Long seq, String title, String content) {
	  this.seq = seq;
	  this.title = title;
	  this.content = content;
	}

	public Notice toEntity() {
		if(seq == null) {
			return Notice.builder()
					.title(title)
					.content(content)
					.viewCnt(0L)
					.writer("admin")
					.createdDate(LocalDateTime.now())
					.deleteYn("N")
					.updatedId("admin")
					.updatedDate(LocalDateTime.now())
					.fileId(null)
					.build();
		}else {
			return Notice.builder()
					.seq(seq)
					.title(title)
					.content(content)
					.viewCnt(0L)
					.writer("admin")
					.createdDate(LocalDateTime.now())
					.deleteYn("N")
					.updatedId("admin")
					.updatedDate(LocalDateTime.now())
					.fileId(null)
					.build();
		}
	}
}
