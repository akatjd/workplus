package com.kjc.workplus.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjc.workplus.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
