package com.kjc.workplus.notice.repository;

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
	@Query("UPDATE Notice notice SET notice.deleteYn = :deleteYn WHERE notice.seq = :seq")
	int updateDeleteYn(@Param("seq") Long seq, @Param("deleteYn") String deleteYn);
}
