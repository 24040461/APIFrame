package restassuredTests;

import baseElements.BaseElements;
import buildTeardown.Build_Teardown;
import io.restassured.response.Response;
import org.junit.*;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class CardTests {

    private static BaseElements base = new BaseElements();
    private static Build_Teardown NewID = new Build_Teardown();
    public static String myCardID;

    public void checkCardNoLongerExists(){
        given()
                .when()
                .get(base.BaseURL + "cards/" + myCardID + base.key + base.token)
                .then()
                .statusCode(404);
    }

    @BeforeClass
    public static void setupCards(){
        NewID.createBoard();
        NewID.createList();
    }
    @Test
    public void testPostCard(){
        CardBuilder card = CardBuilder.builder().name("To test").desc("this is again to test").idBoard(NewID.BoardID).idList(NewID.listID).build();
           Response myResponse =
            given()
                .contentType("application/json")
                .body(card)
                .when()
                .post(base.BaseURL + "cards/"+ base.key+ base.token);
                myResponse.then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body("name", equalTo("To test"));
                myCardID = myResponse.jsonPath().get("id");
    }
    @Test
    public void getCard(){
            given()
                .when()
                .get(base.BaseURL + "cards/" + base.MyCardID + base.key + base.token)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body("name", equalTo("Test again"));

    }
    @Test
    public void updateCard(){
        CardBuilder card = CardBuilder.builder().name("To test - updated").desc("this is again to test - updated").idBoard(base.idBoard).idList(base.idList).build();
        Response myResponse =
        given()
                .contentType("application/json")
                .body(card)
                .when()
                .put(base.BaseURL + "cards/" + base.MyCardIDTest + base.key + base.token);
                myResponse.then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                        .body("name", equalTo("To test - updated"));
    }
//    @Test
//    public void deleteCard(){
//        given()
//                .when()
//                .delete(base.BaseURL + "cards/" + myCardID + base.key + base.token)
//                .then()
//                .statusCode(200)
//                .statusLine("HTTP/1.1 200 OK");
//                checkCardNoLongerExists();
//    }


    @AfterClass
    public static void tearDown(){
        NewID.deleteBoard();
    }

}