import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import files.Payload;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Validate if Add place of Google Map API is working as expected
// Rest assured works on 3 principle
		// given -> all inputs details
		// when -> Submit the API
		// then -> Validate the response
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		given().log().all()                                // log().all() will show what all parameters were sent.
			.queryParam("key","qaclick123")
			.header("Content-Type","application/json")
			.body(Payload.AddPlace())
		.when()
			.post("/maps/api/place/add/json")
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server","Apache/2.4.52 (Ubuntu)");

}
}
