package com.kh.spring.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class PageInfo {
	int listCount; //현재 일반게시판의 게시글 총개수 =>SELECT COUNT(*)
	int currentPage; // 현재 페이지 => 앞단에서 넘길것
	int pageLimit; // 한번에 몇번 페이지까지 보여줄꺼냐 -> 10개로 고정
	int boardLimit; // 한페이지에 게시글을 몇개까지 보여줄꺼냐 ->10개로 고정 
	
	int maxPage; // 가장마지막 페이지가 몇번 페이지인가 (총페이지의 갯수) 
	int startPage; //화면상 시작하는숫자
	int endPage; //화면상 끝나는숫자 
}
