package buildTeardown;

import baseElements.BaseElements;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class Build_Teardown {
    public String BoardID;
    public String listID;
    private static BaseElements base = new BaseElements();
   public static JSONObject json = new JSONObject();

   public void setup(){
       createBoard();
       createList();
   }
    public void createBoard(){
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        json.put("name","TestingCards - Board Creation");
        request.body(json.toJSONString());
        Response response = request.post(base.BaseURL +"boards/"+base.key+base.token);
        BoardID = response.jsonPath().get("id");
    }
    public void createList(){
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        json.put("name","TestingCards - List Creation");
        request.body(json.toJSONString());
        Response response = request.post(base.BaseURL+ "boards/" + BoardID  +"/lists/"+base.key+base.token);
        listID = response.jsonPath().get("id");
    }
    public void deleteBoard(){
        RequestSpecification request = RestAssured.given();
        request.delete(base.BaseURL+ "boards/" + BoardID +base.key+base.token);
    }


}
