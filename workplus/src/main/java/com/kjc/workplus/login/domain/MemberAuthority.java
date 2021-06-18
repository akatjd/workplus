package com.kjc.workplus.login.domain;

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
