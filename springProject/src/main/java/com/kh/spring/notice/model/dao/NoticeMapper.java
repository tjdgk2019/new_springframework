package com.kh.spring.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {
	
	List<Notice> findAll();
	
	Notice findById(int noticeNo);
	
	int save(Notice notice);
	
	int update(Notice notice);
	
	int delete(int noticeNo);
}
