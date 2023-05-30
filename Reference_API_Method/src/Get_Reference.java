

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import io.restassured.RestAssured;


public class Get_Reference {

	public static void main(String[] args) {
		
		//Step 1 : Declare Base URL
		RestAssured.baseURI= "https://reqres.in";
		
		//Step 2: Configure 
		
		int statusCode = given().header("Content-Type","application/json").when()
				.get("/api/users?page=2").then().extract().statusCode();
		
		String responseBody = given().header("Content-Type","application/json").log().all()
				.when().get("/api/users?page=2").then().log().all().extract().response().asString();
		
		System.out.println(statusCode);
		System.out.println(responseBody);
		
		

		int id [] = {7,8,9,10,11,12};
		String[] email = {"michael.lawson@reqres.in","lindsay.ferguson@reqres.in",
				"tobias.funke@reqres.in","byron.fields@reqres.in","george.edwards@reqres.in","rachel.howell@reqres.in"};
		String[] first_name = {"Michael","Lindsay","Tobias","Byron","George","Rachel"};
		String[] last_name = {"Lawson", "Ferguson", "Funke","Fields", "Edwards", "Howell"} ;
		
		JsonPath jsp = new JsonPath(responseBody);
		int count = jsp.getList("data").size(); //6
		
		System.out.println(count);
		
		//validate each object in data array
		
	       for(int i = 0; i < count; i++) {
	    	
	    	int exp_id = id[i];
	    	String exp_email = email[i];
	    	String exp_firstName = first_name[i];
	    	String exp_lastName = last_name [i];
	    	
	    	String res_id = jsp.getString("data["+i+"].id");
	    	int res_int_id = Integer.parseInt(res_id);
	    	String res_email = jsp.getString("data["+i+"].email");
	    	String res_first_name = jsp.getString("data["+i+"].first_name");
	    	String res_last_name = jsp.getString("data["+i+"].last_name");
	    	
	    	Assert.assertEquals(res_int_id, exp_id, " ID at index "+ i);
	    	Assert.assertEquals(res_email, exp_email, "email at index " +i);
	    	Assert.assertEquals(res_first_name, exp_firstName, "firstName at index " +i);
	    	Assert.assertEquals(res_last_name, exp_lastName, "lastName at index " +i);
	    	
	    		
	    }
	    System.out.println(statusCode);
	    }
}