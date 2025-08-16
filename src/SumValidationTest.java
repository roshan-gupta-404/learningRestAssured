import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class SumValidationTest {
	@Test
	public void sumOfCourse() {
		int sum = 0;
		JsonPath coursePrice = ReusableMethods.rawToJson(Payload.CoursePrice());
		
		int purchaseAmt = coursePrice.getInt("dashboard.purchaseAmount");
		int count = coursePrice.getInt("courses.size()");
		
		for(int i = 0;i<count;i++) {
			int price = coursePrice.getInt("courses["+i+"].price");
			int copies = coursePrice.getInt("courses["+i+"].copies");
			sum = sum + price*copies;
		}
		
		System.out.println(sum);
		Assert.assertEquals(sum, purchaseAmt);
	}
	
}
