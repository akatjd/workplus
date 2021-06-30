package com.kjc.workplus.files.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kjc.workplus.files.domain.Files;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {
	
	@Query("SELECT files FROM Files as files WHERE files.category = 'NOTICE' and files.categorySeq = :categorySeq and files.deleteYn = 'N' ")
	List<Files> findFilesByNoticeSeq(@Param("categorySeq") Long categorySeq);
	
	@Modifying
	@Query("UPDATE Files files SET files.deleteYn = 'Y' WHERE files.filesSeq = :filesSeq")
	int deleteFile(@Param("filesSeq") Long filesSeq);
	
	@Query("SELECT COUNT(fileNumber) FROM Files files WHERE files.category = :category and files.categorySeq = :categorySeq")
	int findFilesCnt(@Param("category") String category, @Param("categorySeq") Long categorySeq);
	
	@Query("SELECT f.fileStreCours FROM Files f WHERE f.categorySeq = :memberSeq and f.category = :category")
	String getFileCours(@Param("memberSeq") Long memberSeq, @Param("category") String category);
}
