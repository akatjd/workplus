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
	
	@Query("SELECT files FROM Files as files WHERE files.category = 'NOTICE' and files.categorySeq = :categorySeq ")
	List<Files> findFilesByNoticeSeq(@Param("categorySeq") Long categorySeq);
}
