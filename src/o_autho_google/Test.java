package o_autho_google;

import static io.restassured.RestAssured.given;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

public class Test {
//	private static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
//		String password = "password";
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\rosha\\Desktop\\Aut\\Selenium\\drivers\\chromedriver.exe");
//		driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.get("https://accounts.google.com/v3/signin/identifier?opparams=%253Fauth_url%253Dhttps%25253A%25252F%25252Faccounts.google.com%25252Fo%25252Foauth2%25252Fv2%25252Fauth&dsh=S-2009787821%3A1755240147187746&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&o2v=2&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&response_type=code&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&service=lso&flowName=GeneralOAuthFlow&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hAMfMOSBnJ_7T9PMFvXvRF_HidYW423AuOz5Die9b412B2s6JJWt9HdL9B_3x4ufCDnKVSXbf4rOnXZi1ujTLIM3UTFJqjO4IIKbO1XnDZCPMBd4c2aqmhkvJAE6hMNagrRVTx89K-HaBsl2vfkcccTijelFgh4tdjr1kJdob6PUykvTqaAC1sTLMSkjxXgub0MqItnVRMVVLAK0Uh6FbdSDPw4CRVpIGp1EdTqn2p0mGsUiBbtjGx9zNyXq1ajnZXvNlhrJvzq7OAEbvxueoQj69EQTtFqHL7Qso4E9paoeK6D5gV-ddnrzh_kjBJ7VtfUDCVdSxYhMhJ4DUiOb6Di1fZgfdDbaEqH5xwLvpiz6EZ5tQ4JbAysYITMdLR-OH8AuUX97DiAn1vTDrJoU2Z6SmfzjCg_lNlsyYYScVFt6cP3BCy5DmcgccqJ67kpkfxVGVF679fuWwaMgEwlymHuLO1Y6vA%26flowName%3DGeneralOAuthFlow%26as%3DS-2009787821%253A1755240147187746%26client_id%3D692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com%23&app_domain=https%3A%2F%2Frahulshettyacademy.com&rart=ANgoxcfXESSASZ-9hLAECtcdu5cB_mZeabuvheTIhqOSxoAWDJPTnYtcoQ4vhT1UrlZFklrhKH2m78C1aI0HrhyYBVitfujtTxvkSyPhcd-ra3y-NTD6foE");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("email.com");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		## SELENIUM AUTOMATION CAN'T BE USED AS GOOGLE HAS RESTRICTED LOGIN THROUGH AUTOMATION

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVMBsAWek95qW0VhYYY9ER5T8WWf0jenms08xJWOurwWSwhBDcNA4sN2o8j623JA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=4&prompt=consent";
		String partialUrl = url.split("code=")[1];
		String code = partialUrl.split("&scope=")[0];
		System.out.println(code);

		String response =given()
						.urlEncodingEnabled(false)
						.queryParams("code", code)
						.queryParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
						.queryParams("grant_type", "authorization_code")
						.queryParams("state", "verifyfjdss")
						.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
						.when().log().all()
						.post("https://www.googleapis.com/oauth2/v4/token").asString();
// System.out.println(response);
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println("line 50"+accessToken);
		ValidatableResponse r2 = given().contentType("application/json").
				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.then()
			    .log().all();
		
	}
}
