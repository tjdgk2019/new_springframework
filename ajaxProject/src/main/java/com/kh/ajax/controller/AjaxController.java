package com.kh.ajax.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Menu;

@Controller
public class AjaxController {
	
	/*1. httpservletReonse객체로 응답데이터를 읃답하기(stream을 이용한 방식)
	 * 
	 * 2.응답할 데이터를 문자열로 반환
	 * -> HttpServletResponse를 사용하지 않는 방법
	 * -> String 반환하면 포워딩-> 응답뷰의 경로로 인식을 해서 뷰 리졸버로 전달
	 * 따라서 스프링을 사용해서 응답데이터를 반환 할때는
	 * -->MessageContverter로 이동하게끔 해줘야 한다. @ResponseBody
	 * 
	
	@GetMapping("ajax1.do")
	public void calSum(String menu,
						int amount, HttpServletResponse response) throws IOException {
		//System.out.println("사용자가 입력한 메뉴 : "+ menu);
		//System.out.println("사용자가 입력한 수량 : "+ amount);
		
		
		//여기서부터는 db에서 일어나서 조회된 일이라고 가정
		int price=0;
		switch(menu) {
		case "알밥" : price =10000; break;
		case "김치찜" : price =12000; break;
		case "돈까스" : price =15000; break;
		case "막국수" : price =9000; break;
		case "샌드위치" : price =8000; break;
		}
		price *= amount;
		System.out.println(price);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(price);
		
		
	}
	*/
	@ResponseBody
	@GetMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String calSum(String menu, int amount){
		
		int price = 0;
		switch(menu) {
		case "알밥" : price = 10000; break;
		case "김치찜" : price = 12000; break;
		case "돈까스" : price = 15000; break;
		case "막국수" : price = 9000; break;
		case "샌드위치" : price = 8000; break;
		}
		price *= amount;
		return String.valueOf(price);
	}
	
	@GetMapping("responseData")
	public void test(HttpServletResponse response) throws IOException {
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		writer.print("<h1>1234</h1>");
	}
	
	@ResponseBody
	@GetMapping("ajax2.do, produces=application/json; charset=UTF-8;")
	public String selectMenu(int menuNumber) {
		//요청처리를 잘했다는 가정하에 데이터 응답
		
		/* DB에 존재한는 메뉴 테이블
		 * ------------------------------
		 * 메뉴 번호 | 메뉴 이름 | 가격 | 재료
		 * ------------------------------
		 * |   1   |  순두부  | 9500| 순두부
		 * ------------------------------
		 * 
		 * 
		 * 
		 */
		Menu menu = new Menu(1, "순두부", 9500,"순두부");
		/*
		StringBuilder sb= new StringBuilder();
		
		sb.append("{");
		sb.append("menuNumber:"+"'"+menu.getMenuNumber()+"',");
		sb.append("menuName:"+"'"+menu.getMenuName()+"',");
		sb.append("price :"+"'"+menu.getPrice()+"',");
		sb.append("material:"+"'"+menu.getMaterial()+"'");
		sb.append("}");
		
		
		return sb.toString();
		*/
		
		JSONObject jObj = new JSONObject();
		jObj.put("menuNumber", menu.getMenuNumber());
		jObj.put("menuName", menu.getMenuName());
		jObj.put("price", menu.getPrice());
		jObj.put("material", menu.getMaterial());
		
		return jObj.toJSONString();
		
	}
	
	
	@ResponseBody
	@GetMapping(value="ajax3.do, produces=application/json; charset=UTF-8;")
	public String ajax3Method(int menuNumber) {
	
		Menu menu = new Menu(1, "순두부", 9500, "순두부");
		
		return new Gson().toJson(menu);
	}
	
	
	@ResponseBody
	@GetMapping(value="find.do, produces=application/json; charset=UTF-8;")
	public String findAll() {
		
		//JSONArray jArr = new JOSNArray();
		
		
		return new Gson().toJson("");
			
	/*
	 * {
	 * menuNumber : 1,
	 * menuName : "순두부찌개"
	 * price : 9500,
	 * material : "순두부찌개"
	 * }
	 *  {
	 * menuNumber : 2,
	 * menuName : "김치부찌개"
	 * price : 9500,
	 * material : "김치부찌개"
	 * }
	 *  {
	 * menuNumber : 3,
	 * menuName : "된장부찌개"
	 * price : 9500,
	 * material : "된장부찌개"
	 * }
	 */	
	}
	
	
}