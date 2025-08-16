
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import files.Payload;
import files.ReusableMethods;
import files.Secured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientCredOAuth2_POJO {
	
	@BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }
  @Test
  public void createIssue() {
	// baseURI is already set before this method runs
	  String res = given()
	  	.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	  	.formParams("grant_type", "client_credentials")
	  	.formParams("scope", "trust")
	  	.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
	  	.when().log().all().post("/oauthapi/oauth2/resourceOwner/token")
	  	.then()
	  	.assertThat().statusCode(200).extract().asString();
	  
	  JsonPath js = ReusableMethods.rawToJson(res);
	  String access_token = js.getString("access_token");
	  
	  System.out.println(access_token);
	  System.out.println("*****************************************************************************");
	  
	  GetCourse gc = given()
	  			.param("access_token", access_token)
			  	.when()
			  	.get("/oauthapi/getCourseDetails").as(GetCourse.class);
	  
	  System.out.println("LinkedIn of instructor: "+gc.getLinkedIn());
	  
	  // get the course fees of courses
	  System.out.println("Getting the price of SoapUI Webservices testing --------");
	  
	  List<Api> apiCourse = gc.getCourses().getApi();
	  for(byte i = 0; i<apiCourse.size();i++) {
		  if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
			  System.out.println(apiCourse.get(i).getPrice());
			  break;
		  }
		  
	  }
	  // getting list of webautomations and comparing
	  String[] expectedCourses = {"Selenium Webdriver Java", "Cypress", "Protractor"};
	  List<String> expectedCoursesList = Arrays.asList(expectedCourses); // making list out of an array
	  
	  ArrayList<String> actualCoursesList = new ArrayList<String>();
	  List<WebAutomation> webAutCourses = gc.getCourses().getWebAutomation();
	  
	  for(byte i = 0; i<webAutCourses.size();i++) {
		  actualCoursesList.add(webAutCourses.get(i).getCourseTitle());		  
	  }
	  
	  Assert.assertTrue(actualCoursesList.equals(expectedCoursesList));
	  
			  
			  
	  
  }
}
