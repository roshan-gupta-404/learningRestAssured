import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import mapPojo.AddPlace;
import mapPojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class MapAPI_POJO_Serialization {
	AddPlace p = new AddPlace();
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
	}
	
	@Test
	public void addPlace() {
		Response res = given().queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		System.out.println(res.asPrettyString());
	}
}
