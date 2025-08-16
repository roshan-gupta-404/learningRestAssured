import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import files.Secured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraAPI {
	
	@BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://webdevapitesting.atlassian.net";
    }
  @Test
  public void createIssue() {
	// baseURI is already set before this method runs
	  // adding 
	  String res = given()
	  	.header("Content-Type","application/json")
	  	.header("Authorization",Secured.jiraToken())
	  	.body("{\n"
	  			+ "  \"fields\": {\n"
	  			+ "    \"project\":\n"
	  			+ "       {\n"
	  			+ "          \"key\": \"SCRUM\"\n"
	  			+ "       },\n"
	  			+ "    \"description\": {\n"
	  			+ "      \"content\": [\n"
	  			+ "        {\n"
	  			+ "          \"content\": [\n"
	  			+ "            {\n"
	  			+ "              \"text\": \"This is the second issue through api\",\n"
	  			+ "              \"type\": \"text\"\n"
	  			+ "            }\n"
	  			+ "          ],\n"
	  			+ "          \"type\": \"paragraph\"\n"
	  			+ "        }\n"
	  			+ "      ],\n"
	  			+ "      \"type\": \"doc\",\n"
	  			+ "      \"version\": 1\n"
	  			+ "    },\n"
	  			+ "    \"issuetype\": {\n"
	  			+ "      \"name\": \"Bug\"\n"
	  			+ "    },\n"
	  			+ "    \"summary\": \"content not working\"\n"
	  			+ "  }\n"
	  			+ "}")
	  .when()
	  	.post("/rest/api/3/issue")
	  .then().log().all()
	  	.assertThat()
	  	.statusCode(201).extract().asString();
	  
	  JsonPath js = ReusableMethods.rawToJson(res);
	  String id = js.getString("id");
	  
	  System.out.println(id);
	  System.out.println("*****************************************************************************");
	  
	  
	  // attaching the file
	  given()
	  			.pathParam("key", id)
			  	.header("Content-Type","multipart/form-data; boundary=<calculated when request is sent>")
			  	.header("Authorization",Secured.jiraToken())
			  	.header("X-Atlassian-Token","no-check")
			  	.multiPart("file",new File("C:\\Users\\rosha\\eclipse-workspace\\DemoAPITestingProject\\resources\\Screenshot (3).png"))
			  .when()
			  	.post("/rest/api/3/issue/{key}/attachments")
			  .then().log().all()
			  	.assertThat()
			  	.statusCode(200);
			  
			  
	  
  }
}
