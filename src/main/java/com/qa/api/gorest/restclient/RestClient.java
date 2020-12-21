package com.qa.api.gorest.restclient;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.qa.api.gorest.util.TestUtil;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	enum HttpMethod {
		GET, POST, PUT, DELETE;
	}

	/**
	 * This method is used to make a GET Call
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param paramsMap
	 * @param log
	 * @param obj
	 * @return this method returns response from the GET Call
	 */
	@Step("Get Call with the {0},{1},{2},{3},{4}")
	public static Response doGet(String contentType, String baseURI, String basePath, Map<String, String> tokenMap,
			Map<String, String> paramsMap, boolean log) {
		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, tokenMap, paramsMap, log);
			return getResponse(HttpMethod.GET, request, basePath);
		}
		return null;
	}

	/**
	 * This method is used to make a POST Call
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param paramsMap
	 * @param log
	 * @param obj
	 * @return this method returns response from the POST Call
	 */
	@Step("Post Call with the {0},{1},{2},{3},{4},{5}")
	// The Object obj can hold any class reference
	public static Response doPost(String contentType, String baseURI, String basePath, Map<String, String> tokenMap,
			Map<String, String> paramsMap, boolean log, Object obj) {
		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, tokenMap, paramsMap, log);
			addRequestPayload(request, obj);
			return getResponse(HttpMethod.POST, request, basePath);
		}
		return null;
	}

	/**
	 * This method is used to make a PUT Call
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param paramsMap
	 * @param log
	 * @param obj
	 * @return this method returns response from the PUT Call
	 */
	@Step("Put Call with the {0},{1},{2},{3},{4},{5}")
	// The Object obj can hold any class reference
	public static Response doPut(String contentType, String baseURI, String basePath, Map<String, String> tokenMap,
			Map<String, String> paramsMap, boolean log, Object obj) {
		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, tokenMap, paramsMap, log);
			addRequestPayload(request, obj);
			return getResponse(HttpMethod.PUT, request, basePath);
		}
		return null;
	}

	/**
	 * This method is used to make a DELETE Call
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param paramsMap
	 * @param log
	 * @return this method returns response from the DELETE Call
	 */
	@Step("Delete Call with the {0},{1},{2},{3},{4}")
	public static Response doDelete(String contentType, String baseURI, String basePath, Map<String, String> tokenMap,
			Map<String, String> paramsMap, boolean log) {
		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, tokenMap, paramsMap, log);
			return getResponse(HttpMethod.DELETE, request, basePath);
		}
		return null;
	}

	private static void addRequestPayload(RequestSpecification request, Object obj) {
		if (obj instanceof Map) {
			request.formParams((Map<String, String>) obj);
		}

		String jsonPayload = TestUtil.getSerializedJSON(obj);
		request.body(jsonPayload);
	}

	private static boolean setBaseURI(String baseURI) {
		if (baseURI == null || baseURI.isEmpty()) {
			System.out.println("Please pass the correct the base URI either null or blank/empty");
			return false;
		}

		try {
			RestAssured.baseURI = baseURI;
			return true;
		} catch (Exception ex) {
			System.out.println("Some exception occurred while assigning the base URI with RestAssured");
			return false;
		}
	}

	private static RequestSpecification createRequest(String contentType, Map<String, String> tokenMap,
			Map<String, String> paramsMap, boolean log) {
		// Preparation of the request in steps 1,2,3 and 4th
		RequestSpecification request;
		// 1st the given
		if (log) {
			request = RestAssured.given().log().all();
		} else {
			request = RestAssured.given();
		}
		// 2nd when the token is passed
		if (tokenMap.size() > 0) {
			// for apis which do not have a bearer token
			// request.header("Authorization", "Bearer " + token);
			request.headers(tokenMap);
		}
		// 3rd when the query param is being passed
		if (!(paramsMap == null)) {
			request.queryParams(paramsMap);
		}
		// 4th content type
		if (contentType != null) {
			if (contentType.equalsIgnoreCase("JSON")) {
				request.contentType(ContentType.JSON);
			}
			if (contentType.equalsIgnoreCase("XML")) {
				request.contentType(ContentType.XML);
			}
			if (contentType.equalsIgnoreCase("TEXT")) {
				request.contentType(ContentType.TEXT);
			}
			if (contentType.equalsIgnoreCase("multipart")) {
				// request.contentType(ContentType.TEXT);
				// image key is the control name of the input which is to be provided as per the
				// upload api
				request.multiPart("image", new File("C:\\Users\\ayesh\\OneDrive\\Desktop\\flowers.jpg"));
			}
		}
		// Request is ready
		return request;
	}

	private static Response getResponse(HttpMethod httpMethod, RequestSpecification request, String basePath) {
		return executeApi(httpMethod, request, basePath);
	}

	private static Response executeApi(HttpMethod httpMethod, RequestSpecification request, String basePath) {
		Response response = null;

		switch (httpMethod) {

		case GET:
			response = request.get(basePath);
			break;
		case POST:
			response = request.post(basePath);
			break;
		case PUT:
			response = request.put(basePath);
			break;
		case DELETE:
			response = request.delete(basePath);
			break;
		default:
			System.out.println("Please pass the correct http method...");
			break;
		}
		return response;
	}

	// Generic Method for Response
	public static JsonPath getJsonPath(Response response) {
		return response.jsonPath();
	}

	public static int getStatusCode(Response response) {
		return response.getStatusCode();
	}

	public static String getHeaderValue(Response response, String headerName) {
		return response.getHeader(headerName);
	}

	public static int getResponseHeaderCount(Response response) {
		return response.getHeaders().size();
	}

	public static List<Header> getResponseHeaders(Response response) {
		Headers headers = response.getHeaders();
		List<Header> headerList = headers.asList();
		return headerList;
	}

	public static void getPrettyPinkResponse(Response response) {
		System.out.println("==============Response of Pretty Pink==============");
		response.prettyPrint();
	}

}
