package com.kjc.workplus;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
	
	/**
	 * 공지사항 wp_notice 테스트 데이터 삽입 (50개)
	 */
	@Test
	public void noticeTest() {
		String title = "테스트 게시글";
		String content = "테스트 본문";
		
		for(int i=0; i<50; i++) {
			noticeRepository.save(Notice.builder()
					.title(title + Integer.toString(i))
					.content(content + Integer.toString(i))
					.viewCnt(10L)
					.writer("김민성" + Integer.toString(i))
					.createdDate(LocalDateTime.now())
					.deleteYn("N")
					.updatedId("김민성" + Integer.toString(i))
					.updatedDate(LocalDateTime.now())
					.fileId(null)
					.build());
		}
		
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
	
	@Test
	public void findById() {
		
		System.out.println("테스트함");
		
		Long noticeSeq = 1L;
		
		Notice notice = noticeRepository.findById(noticeSeq)
										.orElseThrow(() -> new IllegalAccessError(" 해당 게시글이 존재하지 않습니다."));
		
		System.out.println(notice);
	}
	
	@Test
	public void findSeqIncrement() {
		System.out.println(noticeRepository.findSeqIncrement()+1);
	}
	
	@Transactional
	@Test
	@Rollback(false)
	public void viewCntUpdate() {
		
		System.out.println(noticeRepository.updateViewCnt(1L));
	}
	
	@Transactional
	@Test
	public void update() {
		System.out.println(noticeRepository.update(1L, "업데이트 테스트", "업데이트 테스트"));
	}
	
	@Test
    public void testSeqPagingSort() {
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "seq");

        Page<Notice> result = noticeRepository.findBySeqGreaterThan(0L, paging);
        System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL PAGES : " + result.getTotalPages());
        System.out.println("TOTAL COUNT : " + result.getTotalElements());
        System.out.println("NEXT : " + result.nextPageable());

        List<Notice> list = result.getContent();
        list.forEach(System.out::println);
    }

}
