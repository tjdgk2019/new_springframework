package com.kh.spring.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//이 컨트롤러는 보드s로 시작하는 요청이 들어오면 처리를 해줄 컨트롤러
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="boards", produces="application/json; charset=UTF-8")
public class BoardsController {
	
	/*Mapping값은 notice로 통일
	 * 
	 * c : insert -> @PostMapping
	 * r : select -> @GetMapping
	 * u : update -> @PutMapping
	 * d : delete -> @DeleteMapping
	 * 
	 */
	
	private final BoardService boardService;
	
	@GetMapping
	public List<Board> findTopFiveBoard() {
		return boardService.findTopBoard();
	}
	
	@GetMapping("/{boardNo}")
	public Board findByBoardNo(@PathVariable int boardNo) {
		//log.info("넘어온 PK : {}", boardNo);
		return boardService.findById(boardNo);
	}
	
}
