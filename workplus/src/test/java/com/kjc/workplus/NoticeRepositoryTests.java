package com.kjc.workplus;


import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.kjc.workplus.notice.domain.Notice;
import com.kjc.workplus.notice.repository.NoticeRepository;

@SpringBootTest
public class NoticeRepositoryTests {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Test
	public void cleanup() {
		noticeRepository.deleteAll();
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void noticeTest() {
		String title = "테스트 게시글";
		String content = "테스트 본문";
		
		noticeRepository.save(Notice.builder()
									.title(title)
									.content(content)
									.viewCnt(10L)
									.writer("김민성")
									.createdDate(LocalDateTime.now())
									.deleteYn("N")
									.updatedId("김민성")
									.updatedDate(LocalDateTime.now())
									.fileId(null)
									.build());
		
//		Notice notice = new Notice();
//		notice.setTitle("제목 테스트");
//		notice.setContent("내용 테스트");
//		notice.setViewCnt(10L);
//		notice.setWriter("작성자 테스트");
//		notice.setCreatedDate(LocalDateTime.now());
//		notice.setDeleteYn("N");
//		notice.setUpdatedId("수정자 테스트");
//		notice.setUpdatedDate(LocalDateTime.now());
//		notice.setFileId("파일 아이디 테스트");
//		
//		noticeRepository.save(notice);
	}

}
