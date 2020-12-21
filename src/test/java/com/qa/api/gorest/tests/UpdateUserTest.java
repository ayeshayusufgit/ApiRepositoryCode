package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.gorest.pojo.User;
import com.qa.api.gorest.restclient.RestClient;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Epic("Epic-101:Go Rest Api feature implementation")
@Feature("US-104:Update User Api Feature")
public class UpdateUserTest {

	String baseURI = "https://gorest.co.in";
	String basePath = "/public-api/users";
	String accessToken = "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";

	// @Test
	public void updateUserAPIPOSTTest_withConstructor() {
		User userObj = new User("Ayesha", "ayesha.yusuf14@gmail.com", "Female", "Active");
		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("Authorization", "Bearer " + accessToken);

		Response response = RestClient.doPost("JSON", baseURI, basePath, accessTokenMap, null, true, userObj);
		String userId = RestClient.getJsonPath(response).getString("data.id");
		System.out.println(userId);

		System.out.println("====================================");
		userObj = new User("AyeshaTest", "ayesha.yusuf14@gmail.com", "Male", "Inactive");
		Response responseUpdate = RestClient.doPut("JSON", baseURI, basePath + "/" + userId, accessTokenMap, null, true,
				userObj);
		String updatedUserId = RestClient.getJsonPath(response).getString("data.id");
		Assert.assertEquals(updatedUserId, userId);
		// System.out.println(responseUpdate.getStatusCode());
		// System.out.println(responseUpdate.prettyPrint());

		System.out.println("====================================");
	}

	@Description("Test Update user api and Verify the user is updated")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void updateUserAPIPOSTTest_withSetter() {
		User userObj = new User();
		userObj.setName("AyeshaTest");
		userObj.setEmail("ayesha.yusuf17@gmail.com");
		userObj.setGender("Female");
		userObj.setStatus("Active");

		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("Authorization", "Bearer " + accessToken);

		Response response = RestClient.doPost("JSON", baseURI, basePath, accessTokenMap, null, true, userObj);
		String userId = RestClient.getJsonPath(response).getString("data.id");
		System.out.println(userId);

		System.out.println("====================================");
		userObj.setName("AyeshaUpdated");
		Response responseUpdate = RestClient.doPut("JSON", baseURI, basePath + "/" + userId, accessTokenMap, null, true,
				userObj);
		String updatedUserId = RestClient.getJsonPath(response).getString("data.id");
		Assert.assertEquals(updatedUserId, userId);
		// System.out.println(responseUpdate.getStatusCode());
		System.out.println(responseUpdate.prettyPrint());

		System.out.println("====================================");
	}
}
