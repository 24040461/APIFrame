package restassuredTests;

import baseElements.BaseElements;
import buildTeardown.Build_Teardown;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ListTests {
    private static BaseElements base = new BaseElements();
    private static Build_Teardown NewID = new Build_Teardown();
    public static String myListID;

    public void checkListNoLongerExists(){
        given()
                .when()
                .get(base.BaseURL + "Boards/" + NewID.BoardID + base.key + base.token)
                .then()
                .statusCode(404);
    }
    @BeforeClass
    public static void setupLists(){
        NewID.createBoard();
    }
    @Test
    public void testPostList(){
        ListBuilder list = ListBuilder.builder().name("To test creating a new list").idBoard(NewID.BoardID).build();
        Response myResponse =
                given()
                        .contentType("application/json")
                        .body(list)
                        .when()
                        .post(base.BaseURL + "lists/"+ base.key+ base.token);
        myResponse.then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body("name", equalTo("To test creating a new list"));
                myListID = myResponse.jsonPath().get("id");
    }
    @Test
    public void getList(){
        given()
                .when()
                .get(base.BaseURL + "lists/" + base.idList + base.key + base.token)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body("name", equalTo("My trello list again"));
    }
    @Test
    public void updateList(){
        ListBuilder list = ListBuilder.builder().name("To test list name being - updated again").idBoard(base.idBoard).build();
        Response myResponse =
                given()
                        .contentType("application/json")
                        .body(list)
                        .when()
                        .put(base.BaseURL + "lists/" + base.idListTest + base.key + base.token);
        myResponse.then()
                        .statusCode(200)
                        .statusLine("HTTP/1.1 200 OK")
                        .body("name", equalTo("To test list name being - updated again"));
    }
//    @Test
//    public void deleteCard(){
//        given()
//                .when()
//                .delete(base.BaseURL + "boards/" + NewID.BoardID + base.key + base.token)
//                .then()
//                .statusCode(200)
//                .statusLine("HTTP/1.1 200 OK");
//        checkListNoLongerExists();
//    }



    @AfterClass
    public static void tearDown(){
        NewID.deleteBoard();
    }
}
