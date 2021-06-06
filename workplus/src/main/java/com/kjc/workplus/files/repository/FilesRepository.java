package com.kjc.workplus.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjc.workplus.files.domain.Files;

public interface FilesRepository extends JpaRepository<Files, Long> {
	
}
