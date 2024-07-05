package com.kh.api.kakao.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.api.kakao.model.SocialMember;
import com.kh.api.kakao.model.service.KakaoService;

@Controller
public class LoginController {
	
	@Autowired
	private KakaoService kakaoService;
	
	@GetMapping("oauth")
	public String socialLogin(String code, HttpSession session) throws IOException, ParseException {
		
		String accessToken = kakaoService.getToken(code);
		session.setAttribute("accessToken", accessToken);
		
		SocialMember sm = kakaoService.getUserInfo(accessToken);
		session.setAttribute("loginUser", sm);
		
		return "redirect:login";
		
		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		String accessToken = (String)session.getAttribute("accessToken");
		
		kakaoService.logout(accessToken);
		session.removeAttribute("loginUser");
		
		return "redirect:login";
		
		
	}
}
