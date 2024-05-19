package com.ssafy.hfrf.news.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
	
	String clientId = "5_RLEIdox5Hs_WzBONEi";	// Application Client ID 값
	String clientSecret = "FCsnUHPFDT";	// Application Client Secret 값

	@Override
	public String newsList() {
		try {
			String text = URLEncoder.encode("부동산", "UTF-8");	// 검색어
			String apiUrl = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=10&sort=sim";	// 뉴스의 json 결과
			
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-Naver-Client-Id", clientId);
			conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));				
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));				
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine + "\n");				
			}
			br.close();
			System.out.println(response.toString());
			return response.toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
