package com.kh.spring.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.notice.model.vo.Message;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;
	
	@GetMapping
	public ResponseEntity<Message> findAll() {
		List<Notice> noticeList = noticeService.findAll();
		
		if(noticeList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .body(Message.builder()
								 .message("조회결과가 존재하지 않아요")
								 .build());
		}
		
		Message responseMsg = Message.builder()
									 .data(noticeList)
									 .message("조회 요청 성공")
									 .build();
		
		
		//log.info("조회된 공지사항 목록 : {}", noticeList);
		return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
	}
	
}
