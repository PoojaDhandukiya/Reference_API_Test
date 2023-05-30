

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import static io.restassured.RestAssured.given;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post_Reference {

	public static void main(String[] args) {
		
		//Step 1 : Declare Base URI and request body variables
		String BaseURI = "https://reqres.in";
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}";
		
		//Fetch request Body parameter values.
		
		JsonPath jsprequest = new JsonPath(requestBody);   //Creating s JSON Object 
		String req_name = jsprequest.getString("name");
		String req_job = jsprequest.getString("job");
		
		
		//Declare Base URI
		RestAssured.baseURI= BaseURI;
		
		// Configure Request Body
		int statusCode=given().header("Content-Type","application/json").body(requestBody)
				.when().post("/api/users").then().extract().statusCode();
		
		/*String responseBody = given().header("Content-Type","application/json").body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}").log().all().when().post("/api/users").then().log().all().extract().response().asString();*/
		
		
		//Without Log all
		String responseBody = given().header("Content-Type","application/json").body(requestBody)
				.when().post("/api/users").then().extract().response().asString();
		
		System.out.println(statusCode);
		//System.out.println(responseBody);
		
		//Step :3 Parse the response body
		JsonPath jsp = new JsonPath(responseBody);
		String res_name = jsp.getString("name");
		String res_job = jsp.getString("job");
		String res_id = jsp.getString("id");
		String res_createdAt=jsp.getString("createdAt");
		
		
		//Step 4: Validate the response body parameters
		Assert.assertEquals(statusCode, 201);
		Assert.assertEquals(res_name, req_name);
		Assert.assertEquals(res_job, req_job);
		Assert.assertNotNull(res_id);
		
		
		
		String trimming = res_createdAt.substring(0,10);
		
		String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE).substring(0, 10);
		
	    Assert.assertEquals(res_createdAt.substring(0, 10),date);
	    Assert.assertEquals(trimming,date);
	    
	  }
			
	}