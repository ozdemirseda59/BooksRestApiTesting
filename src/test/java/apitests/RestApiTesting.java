package ApiBookTests;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import utilities.RestUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestApiTesting  extends RestUtil {
    private JSONObject requestParams;

    @Before
    public void setup()  {
        requestParams=new JSONObject();
        setBaseURI("http://localhost:3000");
        deleteBooks();
    }

    @Test
    public void T01_getResponseBody_noBooksStored() {
        getResponse();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.getBody().asString(),"[]");
    }

    @Test
    public void T02_postResponseBody_verifyTitleRequired() {
        requestParams.put("title", "title1");
        postResponse(requestParams);

        Assert.assertEquals(response.jsonPath().get("error"),"Field 'authar' is required");
        Assert.assertEquals(response.statusCode(),500);
    }

   @Test
    public void T03_postResponseBody_verifyAutharRequired() {
        requestParams.put("authar", "authar1");
        postResponse(requestParams);
        Assert.assertEquals(response.jsonPath().get("error"), "Field 'title' is required");
        Assert.assertEquals(response.statusCode(),500);
}

   @Test
    public void T04_postResponseBody_verifyIdReadOnly(){
        requestParams.put("id", "2");
        requestParams.put("title", "title1");
        requestParams.put("authar", "authar1");

        postResponse(requestParams);
        Assert.assertEquals(response.statusCode(),500);
    }
    @Test
    public void T05_postResponseBody_verifyCreatNewBook(){
        requestParams.put("title", "title1");
        requestParams.put("authar", "authar1");
        postResponse(requestParams);

        Assert.assertEquals(response.statusCode(),201);
        Assert.assertEquals(response.jsonPath().get("title"),"title1");
        Assert.assertEquals(response.jsonPath().get("authar"),"authar1");
    }
    @Test
    public void T05_postResponseBody_verifyNotCreatDublicateBook() throws Exception{
        //ilk post işlemi yapılır
        requestParams.put("title", "title1");
        requestParams.put("authar", "authar1");
        postResponse(requestParams);

        verifyNotCreateDublicateBook(requestParams);
    }
}