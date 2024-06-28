package com.kh.spring.string.controller;

public class StringController {
    // String 클래스 => 불변(변하지 않음을 의미)
    int num = 1;
    boolean flag = true;

    {
        /*
        "1";
        "11";
        "111";

        int[] numArr = {1, 2, 3, 4, 5};
        String str;
        int num1 = numArr[0];
        int num2 = numArr[1];
        int num3 = numArr[2];
        */
        // charArray
    }
/*
    // 생성자를 호출해서 String을 객체로 만들어주는 방법
    public void constructorString() {
        String str1 = new String("Hello");
        String str2 = new String("Hello");

        System.out.println(str1.toString());
        System.out.println(str2.toString());

        System.out.println(str1.equals(str2));
        // 주소값 비교가 아닌 실제 문자열 리터럴 값을 비교하도록 오버라이딩

        System.out.println(str1.hashCode());
        // 해쉬코드도 오버라이딩 되어 있음

        System.out.println(System.identityHashCode(str1));
        System.out.println(System.identityHashCode(str2));
        // str1과 str2는 다르다
        // 진짜 진짜 비교할 수 있는 값
    }

    // 리터럴 대입 방식
    public void assignToString() {
        String str1 = "Hello";
        String str2 = "Hello";

        // toString()
        System.out.println(str1);
        System.out.println(str2);

        // equals()
        System.out.println(str1.equals(str2));

        // hashCode()
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());

        // identityHashCode()
        System.out.println(System.identityHashCode(str1));
        System.out.println(System.identityHashCode(str2));
    }
 */
    //스트링풀
	public void stringPool() {
		String str1 = "Hello";

		System.out.println(System.identityHashCode(str1));
		// 대입연산자를 이용해서 문자열 리터럴값을 대입시
		// StringPool 영역에 올라감
		// StringPool 특징: 동일한 내용의 문자열이 존재하수 없다
		str1 = "bye";

		System.out.println(System.identityHashCode(str1));
		//연결이 끊긴 문자열은 가비지 콜렉터가 정리해줌
		//객체는 불변
		//참조변수는 새로운 주소값을 참조
		
	//quiz
		String a ="a";
		String b ="a";
		System.out.println("결과:" +(a==b));
	}
 
    
    
    public static void main(String[] args) {
        StringController controller = new StringController();
      //  controller.constructorString();
      //cv0  controller.assignToString();
        controller.stringPool();
    }
}
