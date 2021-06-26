package com.kjc.workplus.notice.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kjc.workplus.files.dto.FilesDto;
import com.kjc.workplus.files.service.FilesService;
import com.kjc.workplus.files.utils.MD5Generator;
import com.kjc.workplus.notice.domain.Notice;
import com.kjc.workplus.notice.dto.NoticeResponseDto;
import com.kjc.workplus.notice.dto.NoticeSaveRequestDto;
import com.kjc.workplus.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/workplus/notice")
@Controller
public class NoticeController {
	
	private final FilesService filesService;
	private final NoticeService noticeService;
	
//	/**
//	 * 공지사항 리스트 확인
//	 */
//	@GetMapping(value = "/list.do")
//	public String openNoticeList(Model model) {
//		
//		List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
//		
//		model.addAttribute("noticeDtoList", noticeDtoList);
//		
//		return "noticeList";
//	}
	
	/**
	 * 공지사항 리스트 확인
	 */
	@GetMapping(value = "/list.do")
	public String paging(@PageableDefault Pageable pageRequest, Model model) {
		
		Page<Notice> noticeDtoList = noticeService.getNoticeList(pageRequest);
		model.addAttribute("noticeDtoList", noticeDtoList);
		
		log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
				noticeDtoList.getTotalElements(), noticeDtoList.getTotalPages(), noticeDtoList.getSize(),
				noticeDtoList.getNumber(), noticeDtoList.getNumberOfElements());
		
		return "noticeList";
	}
	
	/**
	 * 공지사항 상세내용 확인
	 */
	@GetMapping(value = "/view.do")
	public String openNoticeDetail(@RequestParam(value = "noticeSeq", required = false) Long noticeSeq, Principal principal, Model model) {
		
		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);
		
		if(principal != null) {
			String writer = principal.getName();
			
			System.out.println(writer);
			
			noticeService.updateViewCnt(noticeSeq);
			
			model.addAttribute("writer", writer);
			model.addAttribute("noticeResponseDto", noticeResponseDto);
			
			List<FilesDto> fileList = filesService.getFiles(noticeSeq); // 추가된 로직
			model.addAttribute("fileList", fileList); // 추가된 로직

			return "noticeView";
		}else {
			
			noticeService.updateViewCnt(noticeSeq);

			model.addAttribute("noticeResponseDto", noticeResponseDto);
			
			List<FilesDto> fileList = filesService.getFiles(noticeSeq); // 추가된 로직
			model.addAttribute("fileList", fileList); // 추가된 로직

			return "noticeView";
		}
	}
	
	/**
	 * 공지사항 글쓰기 화면 출력
	 */
	@GetMapping(value = "/write.do")
	public String openNoticeWrite(@RequestParam(value = "noticeSeq", required = false) Long noticeSeq, NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
		
		if(noticeSeq == null) {
			return "noticeWrite";
		}else {
			NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);
			List<FilesDto> fileList = filesService.getFiles(noticeSeq); // 추가된 로직
			
			model.addAttribute("fileList", fileList); // 추가된 로직
			model.addAttribute("noticeResponseDto", noticeResponseDto);
			
			return "noticeWrite";
		}
	}
	
//	/**
//	 * 공지사항 글 등록
//	 */
//    @PostMapping(value = "/register.do")
//    public String openNoticeRegister(NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
//    	
//    	if(noticeSaveRequestDto.getSeq() == null) {
//    		
//    		noticeService.save(noticeSaveRequestDto);
//    		
//    		List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
//    		
//    		model.addAttribute("noticeDtoList", noticeDtoList);
//    		
//    		return "noticeList";
//    	}else {
//    		
//    		noticeService.update(noticeSaveRequestDto);
//    		
//    		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSaveRequestDto.getSeq());
//
//    		model.addAttribute("noticeResponseDto", noticeResponseDto);
//    		
//    		return "noticeView";
//    	}
//    }
    
    /**
	 * 공지사항 글 등록
	 */
    @PostMapping(value = "/register.do")
    public String openNoticeRegister(@RequestParam("file") MultipartFile[] files, @PageableDefault Pageable pageRequest, NoticeSaveRequestDto noticeSaveRequestDto, Model model) {
    	
    	if(noticeSaveRequestDto.getSeq() == null) {
    		try {
    			FilesDto filesDto = new FilesDto();
    			
    			for(int i=0; i<files.length; i++) {
    				String originFileName = files[i].getOriginalFilename();
    				
    				if(originFileName != null) {
    					String streFileName = new MD5Generator(originFileName).toString();
            			/* 오늘 날짜 */
            			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            			/* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            			String savePath = Paths.get("C:", "workplus", "files", today).toString();
            			/* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            			if(!new File(savePath).exists()) {
            				try {
            					new File(savePath).mkdir();
            				}catch(Exception e) {
            					e.getStackTrace();
            				}
            			}
            			
            			String fileStreCours = savePath + "\\" + streFileName;
            			files[i].transferTo(new File(fileStreCours));
            			
            			filesDto.setCategorySeq(noticeService.findSeqIncrement());
            			filesDto.setCategory("NOTICE");
            			filesDto.setFileNumber(i+1);
            			filesDto.setOriginFileName(originFileName);
            			filesDto.setStreFileName(streFileName);
            			filesDto.setFileStreCours(fileStreCours);
            			filesDto.setFileSize(Long.valueOf(files[i].getSize()).intValue());
            			
                		filesService.saveFiles(filesDto);
    				}
    			}
        		
        		noticeService.save(noticeSaveRequestDto);

        		Page<Notice> noticeDtoList = noticeService.getNoticeList(pageRequest);
        		model.addAttribute("noticeDtoList", noticeDtoList);
        		
        		log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
        				noticeDtoList.getTotalElements(), noticeDtoList.getTotalPages(), noticeDtoList.getSize(),
        				noticeDtoList.getNumber(), noticeDtoList.getNumberOfElements());
    			
    		}catch(Exception e) {
    			e.printStackTrace();
    		}  	
    		
    		return "noticeList";
    		
    	}else {
    		try {
    			Long noticeSeq = noticeSaveRequestDto.getSeq();
    			
    			int totalFileNum = filesService.findFilesCnt("NOTICE", noticeSeq);
    			
				FilesDto filesDto = new FilesDto();
			
    			for(int i=0; i<files.length; i++) {
    				String originFileName = files[i].getOriginalFilename();
    				
    				if(originFileName != null) {
    					String streFileName = new MD5Generator(originFileName).toString();
            			/* 오늘 날짜 */
            			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            			/* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            			String savePath = Paths.get("C:", "workplus", "files", today).toString();
            			/* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            			if(!new File(savePath).exists()) {
            				try {
            					new File(savePath).mkdir();
            				}catch(Exception e) {
            					e.getStackTrace();
            				}
            			}
            			
            			String fileStreCours = savePath + "\\" + streFileName;
            			files[i].transferTo(new File(fileStreCours));
            			
            			filesDto.setCategorySeq(noticeSeq);
            			filesDto.setCategory("NOTICE");
            			filesDto.setFileNumber(totalFileNum+i+1);
            			filesDto.setOriginFileName(originFileName);
            			filesDto.setStreFileName(streFileName);
            			filesDto.setFileStreCours(fileStreCours);
            			filesDto.setFileSize(Long.valueOf(files[i].getSize()).intValue());
            			
                		filesService.saveFiles(filesDto);
                		
                		noticeService.update(noticeSaveRequestDto);
                		
                		NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSaveRequestDto.getSeq());
                		model.addAttribute("noticeResponseDto", noticeResponseDto);
                		
                		List<FilesDto> fileList = filesService.getFiles(noticeSeq);
                		model.addAttribute("fileList", fileList);	
                		
    				}
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    		
    		return "noticeView";
    	}
    }
    
    /**
     * 공지사항 글 삭제하기
     */
    @GetMapping(value = "/delete.do")
    public String delete(@RequestParam("noticeSeq") Long noticeSeq, Model model) {
 
        noticeService.updateDeleteYn(noticeSeq);
       
        List<NoticeResponseDto> noticeDtoList = noticeService.findAllNature();
		
		model.addAttribute("noticeDtoList", noticeDtoList);
 
        return "noticeList";
    }
    
    /**
     * 공지사항 첨부파일 다운로드
     */
    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
    	
        FilesDto filesDto = filesService.getFile(fileId);
        Path path = Paths.get(filesDto.getFileStreCours());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        
        String encodedFilename = null;
        String originFileName = filesDto.getOriginFileName();
	
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < originFileName.length(); i++) {
               char c = originFileName.charAt(i);
               if (c > '~') {
                     sb.append(URLEncoder.encode("" + c, "UTF-8"));
               } else {
                     sb.append(c);
               }
        }

        encodedFilename = sb.toString();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .body(resource);
    }
    
    @GetMapping(value = "/deleteFile.do")
    public String deleteFile(@RequestParam("filesSeq") Long filesSeq, @RequestParam("noticeSeq") Long noticeSeq, Model model) {
    	
    	filesService.deleteFile(filesSeq);
    	
    	NoticeResponseDto noticeResponseDto = noticeService.findById(noticeSeq);
		model.addAttribute("noticeResponseDto", noticeResponseDto);
		
		List<FilesDto> fileList = filesService.getFiles(noticeSeq);
		model.addAttribute("fileList", fileList);
    	
    	return "noticeWrite";
    }
}
