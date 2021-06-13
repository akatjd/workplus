package com.kjc.workplus.files.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	/**
	 * 첨부파일 하나의 정보만 가져옴
	 */
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
	
	/**
	 * 여러 첨부파일 정보를 가져옴
	 */
	@Transactional
	public List<FilesDto> getFiles(Long categorySeq) {
		
		List<FilesDto> filesList = new ArrayList<FilesDto>();
		
		filesList = filesRepository.findFilesByNoticeSeq(categorySeq)
				.stream()
				.map(FilesDto::new)
				.collect(Collectors.toList());
		
		return filesList;
	}
	
}
