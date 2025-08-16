import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import mapPojo.AddPlace;
import mapPojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class UsingBuilder {
	AddPlace p = new AddPlace();
	RequestSpecification reqSpec ;
	ResponseSpecification resSpec ;
	@BeforeClass
	public void setup() {
//		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		// Serialization
		
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setTypes(myList);
		p.setLocation(l);
		
		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
									.addQueryParam("key", "qaclick123")
									.setContentType(ContentType.JSON)
									.build();
		resSpec = new ResponseSpecBuilder().expectStatusCode(201)
										.expectContentType(ContentType.JSON)
										.build();
	}
	
	@Test
	public void addPlace() {
		RequestSpecification req = given().spec(reqSpec).body(p);
		
		Response response = req.when().post("/maps/api/place/add/json");
		
		response.then().spec(resSpec).extract().response(); 
		// this line of code is not mandatory, the above line can also hold the data of response.
		
		System.out.println(response.asPrettyString());
	}
}
