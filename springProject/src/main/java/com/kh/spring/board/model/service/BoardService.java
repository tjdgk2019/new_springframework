package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;

public interface BoardService {
	
	// 게시글 전제 조회 + 페이징처리
	// 현재 Board테이블의 총 행의 개수
	int boardCount();
	
	// 오라클 그룹함수 5총사
	// 
	// SUM()
	// AVG()
	// MIN()
	// MAX()
	// COUNT()
	
	// 게시글 목록 조회
	List<Board> findAll(Map<String, Integer> map);
	
	// 게시글 검색기능
	int searchCount(Map<String,String> map);
		
	//검색 목록 조회
	List<Board> findByConditionAndKeyword(Map<String, String> map, RowBounds rowBounds);
	
	// 게시글 작성
	int insert(Board board);
	
	// 게시글 상세보기 : 성공할수도 실패할수 도있다.
	int increaseCount(int boardNo);
	
	// 상세조회
	Board findById(int boardNo);
	
	// 게시글 삭제하기
	int delete(int boardNo);
	
	// 게시글 수정하기
	int update(Board board);

	List<Board> selectImages();

	List<Reply> selectReply(int boardNo);

	int insertReply(Reply reply);

	Board boardAndReply(int boardNo);
	
	List<Board> findTopBoard();
	
	//------------------------------------------------------ 댓글 관련 (AJAX)
	
	//1. AJAX를 활용한 댓글 목록조회
	
	//2. MyBatis 기술을 이요한 댓글 조회
	
	//댓글 작성하기
	
	// --------------------------------------------------------
	
	
	// --------------------------------------------------------TOP-N 분석
	
}
