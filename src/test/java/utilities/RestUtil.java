package utilities;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import groovy.json.JsonException;
import org.json.simple.JSONObject;
import org.mozilla.javascript.EcmaError;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.*;

public class RestUtil {
    public static Response response;

    public void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public Response getResponse() {
        return response = given()
                .when().get("/api/books");
    }

    public Response postResponse(JSONObject requestParams) {
        return response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .when().post("/api/books")
                .then().extract().response();

    }

    public void deleteBooks() {
        response = getResponse();
        ArrayList<Integer> arrayList = response.jsonPath().get("id");
        if (arrayList != null) {
            for (int id : arrayList) {
                given()
                        .header("Content-Type", "application/json")
                        .when().delete("/api/books" + "/" + id);

            }
        }
    }

    public void verifyNotCreateDublicateBook(JSONObject requestParams) throws Exception {
        response = getResponse();
        ArrayList<String> titleList = response.jsonPath().get("title");
        ArrayList<String> autharList = response.jsonPath().get("authar");

        if (titleList.contains(requestParams.get("title")) & autharList.contains(requestParams.get("authar"))){
            try{
              throw new Exception("Another book with similar title and author already exists.");
            }
            catch (Exception e){
                e.getMessage();
            }
        }
        else {
            postResponse(requestParams);
        }
    }
}

