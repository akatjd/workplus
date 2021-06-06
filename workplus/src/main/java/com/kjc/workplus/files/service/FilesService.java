package com.kjc.workplus.files.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjc.workplus.files.domain.Files;
import com.kjc.workplus.files.dto.FilesDto;
import com.kjc.workplus.files.repository.FilesRepository;

@Service
public class FilesService {
	
	private FilesRepository filesRepository;
	
	public FilesService(FilesRepository filesRepository) {
		this.filesRepository = filesRepository;
	}
	
	@Transactional
	public Long saveFiles(FilesDto filesDto) {
		return filesRepository.save(filesDto.toEntity()).getFilesSeq();
	}
	
	@Transactional
	public FilesDto getFile(Long filesSeq) {
		Files files = filesRepository.findById(filesSeq).get();
		
		FilesDto filesDto = FilesDto.builder()
									.filesSeq(filesSeq)
									.originFileName(files.getOriginFileName())
									.streFileName(files.getStreFileName())
									.fileStreCours(files.getFileStreCours())
									.build();
		return filesDto;
	}
}
