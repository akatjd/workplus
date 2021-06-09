package com.kjc.workplus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.kjc.workplus.notice.repository.NoticeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class NoticeUpdateTests {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Test
	public void update() {
		System.out.println("테스트");
		noticeRepository.update(1L, "업데이트테스트제목", "업데이트테스트내용");
	}
	
	@Test
	public void viewCntUpdate() {
		System.out.println(noticeRepository.updateViewCnt(1L));
	}
}
