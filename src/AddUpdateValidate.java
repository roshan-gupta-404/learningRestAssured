import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class AddUpdateValidate {

	public static void main(String[] args) {
		// Add Place -> Update Place -> Validate place
		
		//		Adding place
		System.out.println("***********************Adding place******************************");
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given()
			.queryParam("key","qaclick123")
			.header("Content-Type", "application/json")
			.body(Payload.AddPlace())
		.when()
			.post("/maps/api/place/add/json")
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server","Apache/2.4.52 (Ubuntu)")
				.extract().asString();
		
		System.out.println("***********************Updating place******************************");
		
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		
		System.out.println(placeID);
		
		// Updating the place
		String newAddress = "Jharkhand";
		given()
			.queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+ placeID +"\",\r\n"
					+ "\"address\":\""+newAddress+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}\r\n"
					+ "")
			.when()
				.put("/maps/api/place/update/json")
			.then().log().all()
				.assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		System.out.println("***********************Getting place******************************");
		
//		Getting the place to validate
		String updatedPlace = given().log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", placeID)
		.when()
			.get("/maps/api/place/get/json")
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.body("address", equalTo(newAddress)).extract().asPrettyString();
		
		JsonPath place = ReusableMethods.rawToJson(updatedPlace);
		String actualAddress = place.getString("address");
		
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}
//	
}
