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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Notice {
	
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public Long idx;
//
//	@Column
//	private String title;
//
//	@Column
//	private String subTitle;
//
//	@Column
//	private String content;
//
//	@Column
//	private LocalDateTime createdDate;
//
//	@Column
//	private LocalDateTime updatedDate;

//	@OneToOne(fetch = FetchType.LAZY)
//	private User user;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Notice(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }


}
