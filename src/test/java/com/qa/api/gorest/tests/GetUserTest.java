package com.qa.api.gorest.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Epic("Epic-101:Go Rest Api feature implementation")
@Feature("US-103:Get User Api Feature")
public class GetUserTest {

	String baseURI = "https://gorest.co.in";
	String basePath = "/public-api/users";
	String accessToken = "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";

	@Description("Test get all users api test and Verify the users....")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1)
	public void getUserListApiTest() {
		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("Authorization", "Bearer " + accessToken);
		Response response = RestClient.doGet("JSON", baseURI, basePath, accessTokenMap, null, true);
		//Assert.assertEquals(RestClient.getStatusCode(response), Constants.HTTP_STATUS_CODE_200);
		
		Assert.assertEquals(RestClient.getStatusCode(response), Constants.HTTP_STATUS_CODE_201);
		RestClient.getPrettyPinkResponse(response);
		ArrayList results = RestClient.getJsonPath(response).get("data");
		System.out.println(results.size());
		System.out.println(results.get(0));

		Map<String, Object> firstUserData = (Map<String, Object>) results.get(0);

		for (Map.Entry<String, Object> entry : firstUserData.entrySet()) {
			System.out.println("Key="+entry.getKey()+","+"Value="+entry.getValue());
		}
		

	}
	
	@Description("Test get all users api with Query Param and Verify the users....")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 2)
	public void getUserWithQueryParamsApiTest() {

		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("Authorization", "Bearer " + accessToken);

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("name", "Shanti Dhawan");
		paramsMap.put("gender", "Female");

		Response response = RestClient.doGet("JSON", baseURI, basePath, accessTokenMap, paramsMap, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());

	}

}
