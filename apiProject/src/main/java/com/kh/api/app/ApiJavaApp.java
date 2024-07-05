package com.kh.api.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.api.model.vo.AirVO;

public class ApiJavaApp {

	public static final String SERVICE_KEY ="6V3qwvh7jszcNr7Ftl2jdqRbJ7sl15UmkIhh%2Bd23AUvSvQ3QXwQ4wN9TLC3jVC2Y1EB9%2BZ4yxPa4Z%2Bczl6fEng%3D%3D";
	public static void main(String[] args) throws IOException {

      StringBuilder sb = new StringBuilder();
      
      sb.append("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
      sb.append("?serviceKey=");
      sb.append(SERVICE_KEY);
      sb.append("&returnType=json");
      sb.append("&sidoName=");
      sb.append(URLEncoder.encode("서울", "UTF-8"));

		
      String url = sb.toString();
      
//    System.out.println(url);
    
    //Java 코드를 통해 URL 요청
    //HttlUrlConnection 객체를 활용하여 API 서버로 요청
    //1. java.net.URL로 객체 생성 => 요청 보낼 url을 인자값으로 전달
    URL requestUrl = new URL(url);
    //2. 생성한 URL 객체를 통해 HttpURLConnection 객체 생성 (HttpUrlConnection은 URLConnection을 상속받으므로 형변환 가능)
    HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();
    //3. 요청에 필요한 설정
    urlConnection.setRequestMethod("GET");
    
    //stream : byte 단위이므로 한글을 가져올 수 없음 -> Reader 필요
    //buffered ~ : 보조스트림 -> 기본스트림 없이 단독으로 존재할 수 없음 -> 기반스트림도 동일한 단위의 스트림이어야 함
    //4. API 서버와 스트림 연결
    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    
    //xml의 경우 출력
    //readLine() 메서드는 결과가 없으면 null 반환
    /*1.
    while (true) {
       String value = br.readLine();
       if ( value != null) {
          System.out.println(value);
       } else {
          break;
       }
    }*/
    /* 2.
    String responseXml = "";
    
    while((responseXml = br.readLine()) != null) {
       System.out.println(responseXml);
    }*/
    
    String responseJson = br.readLine();
    //System.out.println(responseJson);
    
    // 라이브러리
    // JsonObject, JsonArray : JSON -> 자바데이터 (GSON 라이브러리) + JSONParser 이용
    // <-> JSON.... : 자바데이터 -> JSON (JSON 라이브러리)
    
    JsonObject jsonObj = JsonParser.parseString(responseJson).getAsJsonObject(); //>>JsonObject 타입의 객체 반환 가능
    //System.out.println(jsonObj);
    
    JsonObject responseObj = jsonObj.getAsJsonObject("response");
    //System.out.println(responseObj);
    
    JsonObject bodyObj = responseObj.getAsJsonObject("body");
    //System.out.println(bodyObj);
    
    int totalCount = bodyObj.get("totalCount").getAsInt();
    //System.out.println(totalCount);
    
    JsonArray items = bodyObj.getAsJsonArray("items");
    //System.out.println(items);
    
    
    //System.out.println("===================================");
    
    JsonObject firstItem = items.get(0).getAsJsonObject();  //get(index) => JsonArray는 ArrayList를 사용했음을 알 수 있음 (크기 지정하지 않고 사용 가능)
    //System.out.println(firstItem);
    
    List<AirVO> list = new ArrayList();
    
    for (int i=0; i < items.size(); i ++) {
       
       JsonObject item = items.get(i).getAsJsonObject();
       
       AirVO air = new AirVO();
       
       air.setPm10Value(item.get("pm10Value").getAsString());
       air.setStationName(item.get("stationName").getAsString());
       air.setDataTime(item.get("dataTime").getAsString());
       air.setO3Value(item.get("o3Value").getAsString());
       air.setKhaiValue(item.get("khaiValue").getAsString());
       
       list.add(air);
       
    }
    
    for(AirVO air : list) {
       System.out.println(air);
    }
    
    
    br.close();
    urlConnection.disconnect();
    
    
 }
				

}
