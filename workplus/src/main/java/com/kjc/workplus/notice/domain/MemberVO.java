package com.kjc.workplus.notice.domain;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="member")
public class MemberVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mbrNo; 
	
	private String id; 
	
	private String name;
	
	@Builder
	public MemberVO(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
