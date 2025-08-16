
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import files.Secured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class ClientCredOAuth {
	
	@BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }
  @Test
  public void createIssue() {
	// baseURI is already set before this method runs
	  // adding 
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
	  
	  
	  // attaching the file
	  given()
	  			.param("access_token", access_token)
			  	.when()
			  	.get("/oauthapi/getCourseDetails")
			  .then().log().all();
			  
			  
	  
  }
}
