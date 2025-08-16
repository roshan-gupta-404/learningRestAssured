import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class LiberaryAPI {
	String id;
	
	@BeforeTest
    public void setup() {
        RestAssured.baseURI = "http://216.10.245.166";
    }
  @Test(dataProvider = "BooksData")
  public void addBook(String isbn, String aisle) {
	// baseURI is already set before this method runs
	  // adding 
	  String res = given()
	  	.header("Content-Type","application/json")
	  	.body(Payload.liberaryAdd(isbn,aisle))
	  .when()
	  	.post("/Library/Addbook.php")
	  .then().log().all()
	  	.assertThat()
	  	.statusCode(200).extract().asString();
	  
	  String msg = "successfully added";
	  JsonPath js = ReusableMethods.rawToJson(res);
	  String actMsg = js.getString("Msg");
	  id = js.getString("ID");
	  
	  Assert.assertEquals(actMsg, msg);
	  
	  // deleting the book
	  String delRes = given()
			  	.header("Content-Type","application/json")
			  	.body("{\n"
			  			+ "    \"ID\":\""+id+"\"\n"
			  			+ "}")
			  .when()
			  	.post("/Library/DeleteBook.php")
			  .then().log().all()
			  	.assertThat()
			  	.statusCode(200).extract().asString();
			  
			  String delMsg = "book is successfully deleted";
			  JsonPath delJs = ReusableMethods.rawToJson(delRes);
			  String actDelMsg = delJs.getString("msg");
			  
			  Assert.assertEquals(actDelMsg, delMsg);
	  
  }
  
  @DataProvider(name = "BooksData")
  public Object[][] getData(){
	  return new Object[][] {{"qwert","11234"},{"adsf","211"},{"asdf","432"}};
  }
//adding 
// @Test
// public void delBook() {
//	// baseURI is already set before this method runs
//	 System.out.println(id);
//	  String res = given()
//	  	.header("Content-Type","application/json")
//	  	.body("{\n"
//	  			+ "    \"ID\":\""+id+"\"\n"
//	  			+ "}")
//	  .when()
//	  	.post("/Library/DeleteBook.php")
//	  .then().log().all()
//	  	.assertThat()
//	  	.statusCode(200).extract().asString();
//	  
//	  String msg = "book is successfully deleted";
//	  JsonPath js = ReusableMethods.rawToJson(res);
//	  String actMsg = js.getString("msg");
//	  
//	  Assert.assertEquals(actMsg, msg);
//	  
// }
  
  //to get the json data from json file.
  		//String payload = new String(Files.readAllBytes(Paths.get(path)))
}
