package com.kh.api.pollution.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("pollution")
public class AirController {
    public static final String SERVICE_KEY = "6V3qwvh7jszcNr7Ftl2jdqRbJ7sl15UmkIhh%2Bd23AUvSvQ3QXwQ4wN9TLC3jVC2Y1EB9%2BZ4yxPa4Z%2Bczl6fEng%3D%3D";

    @GetMapping(value="/xml", produces="text/hrml; charset=UTF-8")
    public String xmlPollution(@RequestParam String sidoName) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
        sb.append("?serviceKey=");
        sb.append(SERVICE_KEY);
        sb.append("&sidoName=");
        sb.append(URLEncoder.encode(sidoName, "UTF-8"));
        sb.append("&returnType=xml");

        String url = sb.toString();
        URL requestUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();
        urlConnection.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        
        String responseData= "";
        String line;
        
        while ((line = br.readLine()) != null) {
            responseData += line;
        }

        br.close();
        urlConnection.disconnect();

        // Parse the response JSON
        JsonObject jsonObj = JsonParser.parseString(responseData.toString()).getAsJsonObject();
        JsonObject responseObj = jsonObj.getAsJsonObject("response");
        JsonObject bodyObj = responseObj.getAsJsonObject("body");
        JsonArray items = bodyObj.getAsJsonArray("items");

        // Return the JSON response as a string
        return items.toString();
    }
}