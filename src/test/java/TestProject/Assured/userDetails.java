package TestProject.Assured;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.pojoClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class userDetails {

	@Test(enabled=true)
	public void createUser(ITestContext variable) {
		
		String username="abcefg";
		JSONObject userObj = new JSONObject();
		userObj.put("id", "0");
		userObj.put("username", username);
		userObj.put("firstName", "abc");
		userObj.put("lastName", "efg");
		userObj.put("email", "ab@test.com");
		userObj.put("password", "abc23");	
		userObj.put("phone", "1234567891");	
		userObj.put("userStatus", "0");	
		
		Response resp = 
			RestAssured.
			given().
				baseUri("https://petstore.swagger.io/v2").
				contentType(ContentType.JSON).
				body(userObj.toJSONString()).
			when().
				post("/user").
			then().
				statusCode(200).
				log().
				all().extract().response();
		
		variable.setAttribute("username", username);			
	}
	
	@Test(enabled=true)
	public void updateUser(ITestContext variable) throws JsonProcessingException {
		String userName = variable.getAttribute("username").toString();
		
		pojoClass pojoObj = new pojoClass();
		
		pojoObj.setId("0");
		pojoObj.setUsername("abcefg");
		pojoObj.setFirstname("abc");
		pojoObj.setLastname("efg");
		pojoObj.setEmail("abc@test.com");
		pojoObj.setPassword("abc23");
		pojoObj.setPhone("1234567891");
		pojoObj.setUserstatus("0");
		
		ObjectMapper objMapper = new ObjectMapper();
		String reqBody = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojoObj);
		
		
		RestAssured.
			given().
				baseUri("https://petstore.swagger.io/v2").
				contentType(ContentType.JSON).
				body(reqBody).
			when().
				put("/user/"+userName).
			then().
				statusCode(200).
				log().
				all();
	}
	
	@Test(enabled=true)
	public void userLogin() {
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		
		RestAssured.
			given().
				queryParam("username", "abcefg").
				queryParam("password", "abc23").
			when().
				get("/user/login").
			then().
				statusCode(200).
				log().
				all();
	}
	
	@Test(enabled=true)
	public void deleteUser(ITestContext variable) {
		String userName = variable.getAttribute("username").toString();

		RestAssured.
		given().
			baseUri("https://petstore.swagger.io/v2").
		when().
			delete("/user/"+userName).
		then().
			statusCode(200).
			log().
			all();
	}
	
	@Test(enabled=true)
	public void userLogout() {
			
		RestAssured.
			given().
				baseUri("https://petstore.swagger.io/v2").
			when().
				get("/user/logout").
			then().
				statusCode(200).
				log().
				all();
	}
	
	
}
