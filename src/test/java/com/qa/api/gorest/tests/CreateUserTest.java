package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.gorest.pojo.User;
import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Epic("Epic-101:Go Rest Api feature implementation")//Corresponding to Epics
@Feature("US-101:Create User Api Feature")//Coressponding to User Stories
public class CreateUserTest {
	
	String baseURI="https://gorest.co.in";
	String basePath="/public-api/users";
	String accessToken="5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";
	
	@DataProvider
	public Object[][] getUserData() {
		Object[][] userData=ExcelUtil.getTestData("userdata");
		return userData;
	}
	
	
	@Description("Test create user api test and verify the user is created....")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider="getUserData")
	public void createUserAPIPOSTTest(String name,String email,String gender,String status) {
		//User userObj=new User("Ayesha","ayesha.yusuf1@gmail.com","Female","Active");
		Map<String,String> accessTokenMap=new HashMap<String, String>();
		accessTokenMap.put("Authorization", "Bearer "+accessToken);
		
		User userObj=new User(name,email,gender,status);
		Response response=RestClient.doPost("JSON", baseURI, basePath, accessTokenMap, null, true, userObj);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());	
		System.out.println("====================================");
	}
}
