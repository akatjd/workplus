package com.kjc.workplus.notice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kjc.workplus.notice.domain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
	
	@Modifying
	@Query("UPDATE Notice notice SET notice.title = :title, notice.content = :content WHERE notice.seq = :seq")
	int update(@Param("seq") Long seq, @Param("title") String title,@Param("content") String content);
	
	@Modifying
	@Query("UPDATE Notice notice SET notice.viewCnt = notice.viewCnt + 1 WHERE notice.seq = :seq")
	int updateViewCnt(@Param("seq") Long seq);
	
	@Modifying
	@Query("UPDATE Notice notice SET notice.deleteYn = :deleteYn WHERE notice.seq = :seq")
	int updateDeleteYn(@Param("seq") Long seq, @Param("deleteYn") String deleteYn);
	
	@Query(value = "SELECT * FROM wp_notice WHERE delete_yn = 'N'", nativeQuery = true)
	List<Notice> findAllNature();
	
	@Query(value = "SELECT COUNT(*) FROM wp_notice;", nativeQuery = true)
	Long findSeqIncrement();
	
	Page<Notice> findAll(Pageable pageable);
	
	Page<Notice> findBySeqGreaterThan(Long seq, Pageable paging);
}
