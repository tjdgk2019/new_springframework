package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

//여기에 메서드를 선언해놔야함 
public interface MemberService {
    Member login(Member member);

	int insert(Member member);
	
	int update(Member member);
    //회원탈퇴 (Delete ,Update)
	int delete(String userId);

	int idCheck(String checkId);

	

	
	//로그인 select
	//회원가입 insert
	//회원정보수정 update
	//회원탈퇴 delete update
	//아이디 중복체크 select
	// 메일인증 
	//내가 수행해야하는 sql문을 호출 
}