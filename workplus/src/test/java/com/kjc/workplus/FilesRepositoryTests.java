package com.kjc.workplus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.kjc.workplus.files.dto.FilesDto;
import com.kjc.workplus.files.repository.FilesRepository;

@SpringBootTest
public class FilesRepositoryTests {
	
	@Autowired
	private FilesRepository filesRepository;
	
	@Test
	public void findByCategorySeq() {
		
		System.out.println(filesRepository.findFilesByNoticeSeq(11L).getClass());
		
		System.out.println(filesRepository.findFilesByNoticeSeq(11L)
				.stream()
				.map(FilesDto::new)
				.collect(Collectors.toList()));
		
		List<FilesDto> files = new ArrayList<FilesDto>();
		
		files = filesRepository.findFilesByNoticeSeq(11L)
								.stream()
								.map(FilesDto::new)
								.collect(Collectors.toList());
		
		System.out.println(files.get(0));
	}
	
	@Transactional
	@Test
	@Rollback(false)
	public void deleteFile() throws Exception{
		try {
			filesRepository.deleteFile(1L);
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	@Test
	public void findFileCnt() {
		System.out.println(filesRepository.findFilesCnt("NOTICE", 13L));
	}
	
	@Test
	public void getFileCours() {
		System.out.println(filesRepository.getFileCours(3L, "MEMBER"));
	}
	
}
