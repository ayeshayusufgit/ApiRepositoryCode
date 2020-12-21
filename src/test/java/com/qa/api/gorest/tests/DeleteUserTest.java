package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.gorest.pojo.User;
import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Epic("Epic-101:Go Rest Api feature implementation")
@Feature("US-102:Delete User Api Feature")
public class DeleteUserTest {
	String baseURI = "https://gorest.co.in";
	String basePath = "/public-api/users";
	String accessToken = "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";

	@Description("Test delete user api and Verify the user is deleted....")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void deleteUserTest() {

		User userObj = new User();
		userObj.setName("AyeshaTest");
		userObj.setEmail("ayesha.yusuf22@gmail.com");
		userObj.setGender("Female");
		userObj.setStatus("Active");

		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("Authorization", "Bearer " + accessToken);

		Response response = RestClient.doPost("JSON", baseURI, basePath, accessTokenMap, null, true, userObj);
		String userId = RestClient.getJsonPath(response).getString("data.id");
		System.out.println(userId);
		System.out.println("====================================");
		Response deleteResponse = RestClient.doDelete("JSON", baseURI, basePath + "/" + userId, accessTokenMap, null,
				true);
		RestClient.getPrettyPinkResponse(deleteResponse);
		//Assert.assertNull(RestClient.getJsonPath(deleteResponse).getString("data"));
		Assert.assertNull(RestClient.getJsonPath(deleteResponse).getString("meta"));
		Assert.assertEquals(RestClient.getJsonPath(deleteResponse).getInt("code"), Constants.HTTP_STATUS_CODE_204);
	}

}
