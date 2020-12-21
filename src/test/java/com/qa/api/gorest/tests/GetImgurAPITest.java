package com.qa.api.gorest.tests;

import java.util.HashMap;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.Token;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Epic("Epic-102:Imgur Api feature implementation")
@Feature("US-101:Get Imgur Image Api Feature")
public class GetImgurAPITest {
	Map<Object, Object> tokenMap;
	String accessToken;
	String accountUsername;
	String refreshToken;

	@BeforeMethod
	public void setUp() {
		tokenMap = Token.getAllTokens();
		accessToken = tokenMap.get("access_token").toString();
		accountUsername = tokenMap.get("account_username").toString();
		refreshToken = tokenMap.get("refresh_token").toString();
	}

	@Description("Test get account block status Api implementation")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void getAccountBlockStatusTest() {
		Map<String, String> accessTokenMap = Token.getAccessToken();
		Response response = RestClient.doGet(null, "https://api.imgur.com", "/account/v1/" + accountUsername + "/block",
				accessTokenMap, null, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}

	@Description("Test get account image status api test implementation")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void getAccountImageTest() {

		Map<String, String> accessTokenMap = Token.getAccessToken();
		Response response = RestClient.doGet("JSON", "https://api.imgur.com", "/3/account/me/images", accessTokenMap,
				null, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}

	@Description("Test upload image api implementation")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void uploadImagePostAPITest() {
		Map<String, String> accessTokenMap = Token.getAccessToken();
		Map<String, String> formMap = new HashMap<String, String>();
		// formMap.put("image", "C:\\Users\\450520\\Documents\\cc.png");
		formMap.put("name", "pixel.gif");
		formMap.put("title", "1x1 Pixel");
		formMap.put("description", "This is an 1x1 pixel image.");

		Response response = RestClient.doPost("multipart", "https://api.imgur.com", "/3/upload", accessTokenMap, null,
				true, formMap);
		System.out.println(response.statusCode());

		System.out.println(response.prettyPrint());

	}

}
