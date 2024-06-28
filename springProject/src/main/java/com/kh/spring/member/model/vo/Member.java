package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*롬복 사용 주의사항
 * 시작글자가 외자인 필드명은 쓰면안됨
 * pName 을 게터로 바꾸면 ==>getPName()
 * userName ==>getUserName
 * => EL표기법을 이용할때 내부적인 getter사용법
 * ${pName} ==.getpName()
 * 필드명 작성시 반드시 두글자이상으로 시작하게 할것 !!
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Member {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;

}
