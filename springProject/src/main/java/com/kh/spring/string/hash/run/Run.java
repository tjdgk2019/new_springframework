package com.kh.spring.string.hash.run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kh.spring.string.hash.model.vo.Student;

public class Run {
    public static void main(String[] args) {
        
        // HashSet
        // value값만 저장, index가 존재하지 않음
        // 순서 보장 X, 중복을 허용하지 않음 (Set의 특징, 해쉬셋의 특징이 아님)
        
        // 문자열만 담을 수 있는 HashSet
        HashSet<String> set = new HashSet<String>();
        //String class 는 equals 랑 hashCode()가 오버라이딩되있음 
        
        // 제너릭 개발하는 사람의 편의를 위해 사용 
        // 1. 실수 방지: 혹여 자의도하지 않은 타입의 값이 저장소에 들어가지 않도록
        // 2. 개발의 편의성: 강제 형변환을 안해도 됨
        List<String> list = new ArrayList();
        
        // Student
        Set<Student> students = new HashSet();
        students.add(new Student("이승철", 10, 50));
        students.add(new Student("홍길동", 15, 100));
        students.add(new Student("제임스고슬링", 60, 80));
        students.add(new Student("홍길동", 15,100));
        
        //동일 객체로 판단하지 않기때문
        //HashSet : 요소가 새롭게 추가될때마다 equals와 hashCode 로 비교후에 둘다 결고가 true인경우만 동일하다고 여김 
        //equals(): 현재 객체의 주소값을 비교해서 한번 반환: boolean
        //hashCode(): 현재 객체의 주소값을 핵심 알고리즘을돌려서 10진수로 반환 :int 
     
        
        
        
    }
}
