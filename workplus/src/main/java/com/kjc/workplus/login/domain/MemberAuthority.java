package com.kjc.workplus.login.domain;

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
@Table(name = "wp_authority")
@Builder
public class MemberAuthority {
	
	@Id
	@Column(name = "authority_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authoritySeq;
	
	@Column(name = "member_id", length = 200, nullable = true)
	private String memberId;
	
	@Column(name = "authority_name", length = 20, nullable = true)
	private String authorityName;
	
	@Builder
	public MemberAuthority(String memberId, String authorityName) {
		
		this.memberId = memberId;
		this.authorityName = authorityName;
		
	}
	
}
