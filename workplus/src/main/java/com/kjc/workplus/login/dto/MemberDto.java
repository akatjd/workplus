package com.kjc.workplus.login.dto;

import java.time.LocalDateTime;

import com.kjc.workplus.login.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
	
	private Long memberSeq;
	private String memberId;
	private String nickname;
	private String password;
	private String photo;
	private String hphone;
	private String deleteYn;
	private LocalDateTime regDate;
	private String updatedId;
	private String updatedDate;
	
	public Member toEntity() {
		
		return Member.builder()
					 .memberId(memberId)
					 .nickname(nickname)
					 .password(password)
					 .photo(photo)
					 .hphone(hphone)
					 .deleteYn(deleteYn)
					 .regDate(LocalDateTime.now())
					 .updatedId(updatedId)
					 .updatedDate(LocalDateTime.now())
					 .build();
		
	}

}
