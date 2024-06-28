package com.kh.spring.member.model.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

//Repository : 저장소 
//영속성 작업 : DB CRUD 작업
//sql문 실행하고 결과받아오는것외에 다른것이 존재하면 안됨



@Repository
public class MemberRepository {
	
	public Member login(SqlSessionTemplate sqlSession, Member member) {
		System.out.println("로그인실행");
		return sqlSession.selectOne("memberMapper.login", member);
	}
	
	
	 public int insert(SqlSessionTemplate sqlSession, Member member) {
	        System.out.println("회원 삽입 실행");
	        return sqlSession.insert("memberMapper.insert", member);
	    } 
	 
	 public int update(SqlSessionTemplate sqlSession, Member member) {
	        System.out.println("회원 정보수정 실행");
	        return sqlSession.update("memberMapper.update", member);
	    } 
	 public int delete(SqlSessionTemplate sqlSession, String userId) {
		 return sqlSession.update("memberMapper.delete",userId);
	 }


	public int idCheck(SqlSessionTemplate sqlSession, String checkId) {
		return sqlSession.selectOne("memberMapper.idCheck",checkId);
	}
	 
	 
}