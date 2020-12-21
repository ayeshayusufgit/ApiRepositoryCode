package com.qa.api.gorest.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class Token {
	public static Map<Object,Object> appTokenMap;
	public static Map<String,String> tokenMap=new HashMap<String, String>();
	public static String clientId="787df17940b0441";
	
	@Test
	//The return type is Map<Object,Object> coz the Map key value can be of any type of datatype
	public static Map<Object,Object> getAllTokens() {
		Map<String,String> formParams=new HashedMap<String, String>();
		formParams.put("refresh_token","67238e014bdc6e58976f0ea4b13309247fb61731");
		formParams.put("client_id",clientId);
		formParams.put("client_secret","c35374ddf42579f476f18e1863577e7156e69440");
		formParams.put("grant_type","refresh_token");
		
		JsonPath tokenJson=
		
		given()
			.formParams(formParams)
		.when()
			.post("https://api.imgur.com/oauth2/token")
		.then()
			.extract()
				.jsonPath();
		
		appTokenMap=tokenJson.getMap("");
		return appTokenMap;
	}
	
	public static Map<String,String> getAccessToken(){
		String authToken=appTokenMap.get("access_token").toString();
		tokenMap.put("Authorization", "Bearer "+authToken);
		return tokenMap;
	}
	
	public static Map<String,String> getClientId(){
		tokenMap.put("Authorization", "Client-ID "+clientId);
		return tokenMap;
	}
}
