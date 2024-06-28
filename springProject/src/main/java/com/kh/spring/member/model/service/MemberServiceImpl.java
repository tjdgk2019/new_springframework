package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.repository.MemberRepository;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

//비즈니스 로직을 작성하는곳 => 의사결정코드를 작성하는 곳 
//스프링이 관리할수 있도록 빈등록을 하였음 
//SqlSessionFatory가 여깄어야함 
//Service : 비즈니스(도메인) 로직 작성
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
   
   private final SqlSessionTemplate sqlSession;
   private final MemberRepository memberRepository;

   @Override
   public Member login(Member member) {
      //내가 수행해야 하는 sql문을 호출
     
      return  memberRepository.login(sqlSession, member);
   }
   
   @Override
   public int insert(Member member) {
       // 1. DAO 호출
       // 2. 컨트롤러로 결과 반환
       return memberRepository.insert(sqlSession, member);
   }
   @Override
   public int update(Member member) {
	   return memberRepository.update(sqlSession, member);
   }
   @Override
   public int delete(String userId) {
	   return memberRepository.delete(sqlSession, userId);
   }
	
	@Override
	public int idCheck(String checkId) {
		return memberRepository.idCheck(sqlSession, checkId);
	}
   
  /* @Override
   public int insert(Member member) {
       // 1. DAO호출
       // 2. 컨트롤러로 결과 반환
       return memberRepository.insert(sqlSession, member);
   }
   */
  /* 
   @Override
   public int insert(Member member) {
      return 0;
   }
   
   @Override
   public int update(Member member) {
      return 0;
   }
   
   @Override
   public int delete(String userId) {
      return 0;
   }
   */
}