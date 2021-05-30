package com.kjc.workplus;


import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kjc.workplus.notice.domain.Notice;
import com.kjc.workplus.notice.repository.NoticeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoticeRepositoryTest {
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@AfterAll
	public void cleanup() {
		noticeRepository.deleteAll();
	}
	
	@Test
	public void noticeTest() {
		String title = "테스트 게시글";
		String content = "테스트 본문";
		
		noticeRepository.save(Notice.builder()
									.title(title)
									.content(content)
									.author("b088081@gmail.com")
									.build());
	}
	
	List<Notice> noticeList = noticeRepository.findAll();
	
	Notice notice = noticeList.get(0);

}
