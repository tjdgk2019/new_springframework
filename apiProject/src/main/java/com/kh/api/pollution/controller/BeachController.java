package com.kh.api.pollution.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("beach")
public class BeachController {
	@GetMapping(produces="application/json; charset=UTF-8")
	   public String info() throws IOException{
	      StringBuilder sb = new StringBuilder();
	      
	      sb.append("http://apis.data.go.kr/6260000/BusanBeachInfoService/getBeachInfo");
	      sb.append("?serviceKey=");
	      sb.append(AirController.SERVICE_KEY);
	      sb.append("&pageNo=1");
	      sb.append("&numOfRows=10");
	      sb.append("&resultType=json");
	      
	      
	      String url = sb.toString();
	      
	      URL requestUrl = new URL(url);
	      HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();
	      
	      urlConnection.setRequestMethod("GET");
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	      
	      String responseData = br.readLine();
	      
	      br.close();
	      urlConnection.disconnect();
	      
	      return responseData;
	   }
	
	
}
