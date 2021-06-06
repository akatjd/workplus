package com.kjc.workplus.files.dto;

import java.time.LocalDateTime;

import com.kjc.workplus.files.domain.Files;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FilesDto {
	private Long filesSeq;
	private String category;
	private Long categorySeq;
	private int fileNumber;
	private String fileStreCours;
	private String streFileName;
	private String originFileName;
	private LocalDateTime regDate;
	private int fileSize;
	private String deleteYn;
	
	@Builder
	public FilesDto(Long filesSeq, String category, Long categorySeq, int fileNumber, String fileStreCours,
			String streFileName, String originFileName, int fileSize, String deleteYn) {
		this.filesSeq = filesSeq;
		this.category = category;
		this.categorySeq = categorySeq;
		this.fileNumber = fileNumber;
		this.fileStreCours = fileStreCours;
		this.streFileName = streFileName;
		this.originFileName = originFileName;
		this.fileSize = fileSize;
		this.deleteYn = deleteYn;
	}
	
	public Files toEntity() {
		return Files.builder()
					.category(category)
					.categorySeq(categorySeq)
					.fileNumber(fileNumber)
					.fileStreCours(fileStreCours)
					.streFileName(streFileName)
					.originFileName(originFileName)
					.regDate(LocalDateTime.now())
					.fileSize(fileSize)
					.deleteYn("N")
					.build();
	}
}
