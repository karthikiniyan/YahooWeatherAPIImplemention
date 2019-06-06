package com.atelier.yahoo.WeatherReport;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;
import java.util.Collections;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;

@Component
public class WeatherReport {
	
	
	@Autowired 
	private Weather wea;

	 public void getWeatherReport() throws Exception {

	        final String appId = "3Hhn7K4m";
	        final String consumerKey = "dj0yJmk9ZHpTTU1zTktBU2E0JmQ9WVdrOU0waG9iamRMTkcwbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWYx";
	        final String consumerSecret = "4f617cf050372f4b6312f434a46c110633e1b9ba";
	        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

	        long timestamp = new Date().getTime() / 1000;
	        byte[] nonce = new byte[32];
	        Random rand = new Random();
	        rand.nextBytes(nonce);
	        String oauthNonce = new String(nonce).replaceAll("\\W", "");

	        List<String> parameters = new ArrayList<>();
	        parameters.add("oauth_consumer_key=" + consumerKey);
	        parameters.add("oauth_nonce=" + oauthNonce);
	        parameters.add("oauth_signature_method=HMAC-SHA1");
	        parameters.add("oauth_timestamp=" + timestamp);
	        parameters.add("oauth_version=1.0");
	        // Make sure value is encoded
	        parameters.add("location=" + URLEncoder.encode("chennai", "UTF-8"));
	        parameters.add("format=json");
	        Collections.sort(parameters);

	        StringBuffer parametersList = new StringBuffer();
	        for (int i = 0; i < parameters.size(); i++) {
	            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
	        }

	        String signatureString = "GET&" +
	            URLEncoder.encode(url, "UTF-8") + "&" +
	            URLEncoder.encode(parametersList.toString(), "UTF-8");

	        String signature = null;
	        try {
	            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
	            Mac mac = Mac.getInstance("HmacSHA1");
	            mac.init(signingKey);
	            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
	            Encoder encoder = Base64.getEncoder();
	            signature = encoder.encodeToString(rawHMAC);
	        } catch (Exception e) {
	            System.err.println("Unable to append signature");
	            System.exit(0);
	        }

	        String authorizationLine = "OAuth " +
	            "oauth_consumer_key=\"" + consumerKey + "\", " +
	            "oauth_nonce=\"" + oauthNonce + "\", " +
	            "oauth_timestamp=\"" + timestamp + "\", " +
	            "oauth_signature_method=\"HMAC-SHA1\", " +
	            "oauth_signature=\"" + signature + "\", " +
	            "oauth_version=\"1.0\"";

	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url + "?location=chennai&format=json"))
	            .header("Authorization", authorizationLine)
	            .header("X-Yahoo-App-Id", appId)
	            .header("Content-Type", "application/json")
	            .build();

	        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	        System.out.println("Response  : " +response.body());
	        
	        JSONParser parser = new JSONParser();
	        
	        JSONObject jobj = (JSONObject)parser.parse(response.body().toString()); 
	        
	        Object obj= jobj.get("location");
	        JSONObject job = (JSONObject)parser.parse(obj.toString()); 
	        job.get("country");
	        
	        String city = (String) job.get("city");
	        String country = (String) job.get("country");
	        String region = (String) job.get("region");
	        
	        wea.setCity(city);
	        wea.setCountry(country);
	        wea.setState(region); 
	        
	        Object obj1= jobj.get("current_observation");
	        JSONObject job1 = (JSONObject)parser.parse(obj1.toString());
	        Object obj2= job1.get("condition");
	        JSONObject job2 = (JSONObject)parser.parse(obj2.toString());
	        Long temp = (Long)job2.get("temperature"); 
	        @SuppressWarnings("deprecation")
			Double temperature = new Double (temp);
	        wea.setTemp(temperature);
	        
	        System.out.println(wea.toString());
	        
	    }
	}
