package com.kjc.workplus.login.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "wp_member")
@Builder
public class Member {
	
	@Id
	@Column(name = "member_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberSeq;
	
	@Column(name = "member_id", length = 200, nullable = true)
	private String memberId;
	
	@Column(name = "nickname", length = 20, nullable = true)
	private String nickname;
	
	@Column(name = "password", length = 45, nullable = true)
	private String password;
	
	@Column(name = "photo", length = 200, nullable = true)
	private String photo;
	
	@Column(name = "hphone", length = 20, nullable = true)
	private String hphone;
	
	@Column(name = "delete_yn", length = 1, nullable = true)
	private String deleteYn;
	
	@Column(name = "reg_date")
	private LocalDateTime regDate;
	
	@Column(name = "updated_id", length = 200)
	private String updatedId;
	
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	
	@Builder
	public Member(Long memberSeq, String memberId, String memberType, String nickname, String password,
			String photo, String hphone, String deleteYn, LocalDateTime regDate, String updatedId, LocalDateTime updatedDate) {
		
		this.memberSeq = memberSeq;
		this.memberId = memberId;
		this.nickname = nickname;
		this.password = password;
		this.photo = photo;
		this.hphone = hphone;
		this.deleteYn = deleteYn;
		this.regDate = regDate;
		this.updatedId = updatedId;
		this.updatedDate = updatedDate;
		
	}
}
