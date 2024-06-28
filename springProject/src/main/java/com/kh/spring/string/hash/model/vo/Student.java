package com.kh.spring.string.hash.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
	private String name;
	private int age;
	private int score;
	
	@Override
	public int hashCode() {
		//new Student("홍길동",15,100)
		//홍길동15100
		//홍길동 1 5100
		//홍길동 151 00
		
		
		return(name +age +score).hashCode();
	}
	
	//equals()
	public boolean equals (Object obj) {
		
		//내가 가진 name필도와
		//매개변수로 전달받은 Sturdent객체의 name 필드값을 비교 
		return true;
	}
}
