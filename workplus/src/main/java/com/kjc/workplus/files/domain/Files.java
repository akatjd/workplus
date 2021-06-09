package com.kjc.workplus.files.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성해줌
@NoArgsConstructor // 파라미터가 없는 생성자를 생성함
@Getter
@Entity
@Table(name = "wp_files")
public class Files {
	
	@Id
	@Column(name = "files_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long filesSeq;
	
	@Column(name = "category", length = 10, nullable = true)
	private String category;
	
	@Column(name = "category_seq")
	private Long categorySeq;
	
	@Column(name = "file_number")
	private int fileNumber;
	
	@Column(name = "file_stre_cours")
	private String fileStreCours;
	
	@Column(name = "stre_file_name")
	private String streFileName;
	
	@Column(name = "origin_file_name")
	private String originFileName;
	
	@Column(name = "reg_date")
	private LocalDateTime regDate;
	
	@Column(name = "file_size")
	private int fileSize;
	
	@Column(name = "delete_yn")
	private String deleteYn;
	
	@Builder
	public Files(String category, Long categorySeq, int fileNumber, String fileStreCours,
			 String streFileName, String originFileName, LocalDateTime regDate, int fileSize, String deleteYn) {
		this.category = category;
		this.categorySeq = categorySeq;
		this.fileNumber = fileNumber;
		this.fileStreCours = fileStreCours;
		this.streFileName = streFileName;
		this.originFileName = originFileName;
		this.regDate = regDate;
		this.fileSize = fileSize;
		this.deleteYn = deleteYn;
	}
}
