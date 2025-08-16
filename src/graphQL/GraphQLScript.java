package graphQL;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {

	public static void main(String[] args) {
		// Mutation - creating episode data
		
		String res = given().log().all().header("Content-Type", "application/json")
				.body(
				"{\"query\":\"mutation($name:String!,$air_date:String!, $episode:String!){\\n  createEpisode(episode:{name:$name,air_date:$air_date, episode:$episode}){\\n    id\\n  }\\n \\n}\",\"variables\":{\"name\":\"Power\",\"air_date\":\"12/12/2024\",\"episode\":\"1\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().extract().response().asString();

		System.out.println(res);
		JsonPath js = new JsonPath(res);
		int id = (int) js.getInt("data.createEpisode.id");
		System.out.println(id);
		
		// Query - getting episode data and Character data
		
		res = given().log().all().header("Content-Type", "application/json")
				.body(
				"{\"query\":\"query($name:String!,$id:Int!){\\n  characters(filters: {name:$name}){\\n    info{\\n      count\\n    }\\n    result{\\n      name\\n      id\\t\\t\\t\\n    }\\n  }\\n  episode(episodeId:$id){\\n    name\\n  }\\n}\",\"variables\":{\"name\":\"thor\",\"id\":"+id+"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().extract().response().asString();

		System.out.println(res);
		js = new JsonPath(res);
		String name = js.getString("data.episode.name");
		System.out.println(name);
	}

}
//we have to give the query as json, we can get it from Network ->payload from developer options.
