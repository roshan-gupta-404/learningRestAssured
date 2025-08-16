package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	public static JsonPath rawToJson(String raw) {
			return new JsonPath(raw);
	}
}
