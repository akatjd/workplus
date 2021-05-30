package com.kjc.workplus.notice.dto;

import java.time.LocalDateTime;

import com.kjc.workplus.notice.domain.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeSaveRequestDto {
	
	private String title;
	private String content;
	private Long viewCnt;
	private String writer;
	private LocalDateTime createdDate;
	private String deleteYn;
	private String updatedId;
	private LocalDateTime updatedDate;
	private String fileId;
	
	@Builder
	public NoticeSaveRequestDto(String title, String content, Long viewCnt, String writer, 
			LocalDateTime createdDate, String deleteYn, String updatedId, LocalDateTime updatedDate, String fileId) {
	  this.title = title;
	  this.content = content;
	  this.viewCnt = viewCnt;
	  this.writer = writer;
	  this.createdDate = createdDate;
	  this.deleteYn = deleteYn;
	  this.updatedId = updatedId;
	  this.updatedDate = updatedDate;
	  this.fileId = fileId;
	}

	public Notice toEntity() {
		return Notice.builder()
				.title(title)
				.content(content)
				.viewCnt(viewCnt)
				.createdDate(createdDate)
				.deleteYn(deleteYn)
				.updatedId(updatedId)
				.updatedDate(updatedDate)
				.fileId(fileId)
				.build();
	}
}
