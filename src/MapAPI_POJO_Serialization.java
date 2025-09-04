import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import mapPojo.AddPlace;
import mapPojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAPI_POJO_Serialization {
	AddPlace pojoMap = new AddPlace();
	HashMap<String, Object> jsonMap = new HashMap<>();
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
		
		pojoMap.setAccuracy(50);
		pojoMap.setAddress("29, side layout, cohen 09");
		pojoMap.setLanguage("French-IN");
		pojoMap.setName("Frontline house");
		pojoMap.setPhone_number("(+91) 983 893 3937");
		pojoMap.setWebsite("http://google.com");
		pojoMap.setTypes(myList);
		pojoMap.setLocation(l);
		
//		BY 	USING HASH MAP
		// Outer map
//        HashMap<String, Object> jsonMap = new HashMap<>();

        // Nested "location" map
        HashMap<String, Object> locationMap = new HashMap<>();
        locationMap.put("lat", -38.383494);
        locationMap.put("lng", 33.427362);

        // Add location to outer map
        jsonMap.put("location", locationMap);

        // Add simple key-value pairs
        jsonMap.put("accuracy", 50);
        jsonMap.put("name", "Frontline house");
        jsonMap.put("phone_number", "(+91) 983 893 3937");
        jsonMap.put("address", "29, side layout, cohen 09");

        // Add array (list in Java)
        List<String> typesList = new ArrayList<>();
        typesList.add("Bottle park");
        typesList.add("shop");
        jsonMap.put("types", typesList);

        jsonMap.put("website", "http://google.com");
        jsonMap.put("language", "French-IN");

	}
	
	@Test
	public void addPlace() {
		Response res = given().queryParam("key", "qaclick123")
		.body(jsonMap).log().body()								//to uese pojo technique , give pojoMap variable in body
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		System.out.println(res.asPrettyString());
	}
}
