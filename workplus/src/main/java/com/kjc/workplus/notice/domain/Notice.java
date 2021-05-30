package com.kjc.workplus.notice.domain;

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

@Getter
@AllArgsConstructor // 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성해줌
@NoArgsConstructor // 파라미터가 없는 생성자를 생성함
@Entity
@Table(name = "wp_notice")
@Builder
public class Notice {
	
	@Id
	@Column(name = "notice_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long seq;

	@Column(name = "title", length = 200, nullable = false)
	private String title;

	@Column(name = "content", columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(name = "view_cnt")
	private Long viewCnt;
	
	@Column(name = "writer", length = 100)
	private String writer;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "delete_yn", length = 2)
	private String deleteYn;
	
	@Column(name = "updated_id", length = 100)
	private String updatedId;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	
	@Column(name = "file_id", length = 200)
	private String fileId;
	
	@Builder
	public Notice(String title, String content, Long viewCnt, String writer, 
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

	
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 500, nullable = false)
//    private String title;
//
//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String content;
//
//    private String author;

//    @Builder
//    public Notice(String title, String content, String author) {
//        this.title = title;
//        this.content = content;
//        this.author = author;
//    }
}
