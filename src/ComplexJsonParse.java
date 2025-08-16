import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		/*
		 *
		 * 
		 *
		 * 
		 * 5. Print no of copies sold by RPA Course
		 * 
		 * 6.
		 */
		JsonPath coursePrice = ReusableMethods.rawToJson(Payload.CoursePrice());
		int sum = 0;
		//1. Print No of courses returned by API
		
		int count = coursePrice.getInt("courses.size()"); // since courses is array so we can directly use size()		
		System.out.println("Count of the course is "+count);
		
		//2.Print Purchase Amount
		int purchaseAmt = coursePrice.getInt("dashboard.purchaseAmount");
		System.out.println("Total purchase amt of the courses is "+purchaseAmt);
		
		// 3. Print Title of the first course
		String titleFirstCourse = coursePrice.getString("courses[0].title");
		System.out.println("Title of the first course is "+titleFirstCourse);
		
		// 4. Print All course titles and their respective Prices
		for(int i=0;i<count;i++) {
			String title = coursePrice.getString("courses["+i+"].title"); 
			String price = coursePrice.getString("courses["+i+"].price"); 
			
			System.out.println("title is "+title+" and price is "+price);
		}
		
		//5. Print no of copies sold by RPA Course
		for(int i=0;i<count;i++) {
			String title = coursePrice.getString("courses["+i+"].title"); 
			if(title.equalsIgnoreCase("RPA")) {
				int copies = coursePrice.getInt("courses["+i+"].copies"); 
				System.out.println("price of "+title+" is "+copies);
			}
		}
		
		// Verify if Sum of all Course prices matches with Purchase Amount
		for(int i = 0;i<count;i++) {
			int price = coursePrice.getInt("courses["+i+"].price");
			int copies = coursePrice.getInt("courses["+i+"].copies");
			sum = sum + price*copies;
		}
		System.out.println(sum);
		Assert.assertEquals(sum, purchaseAmt);
	}

}
