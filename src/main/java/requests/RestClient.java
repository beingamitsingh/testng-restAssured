package requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public abstract class RestClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AuthenticateClient authenticateClient;

    public RestClient(AuthenticateClient authenticateClient) {
        this.authenticateClient = authenticateClient;
        RestAssured.defaultParser = Parser.JSON;
    }

    public static JsonNode buildJson(Object object)  {
        return MAPPER.convertValue(object, JsonNode.class);
    }

    public Response createGetRequest(String apiPath)  {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authenticateClient.getToken())
                .when().get(Config.getProperty("rest_url") + apiPath)
                .then().log().all()
                .extract().response();
    }

    public Response createPostRequest(String apiPath, String payload)   {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authenticateClient.getToken())
                .body(payload)
                .when().post(Config.getProperty("rest_url") + apiPath)
                .then().log().all()
                .extract().response();
    }

    public Response createPutRequest(String apiPath, String payload)   {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authenticateClient.getToken())
                .body(payload)
                .when().put(Config.getProperty("rest_url") + apiPath)
                .then().log().all()
                .extract().response();
    }

    public Response createDeleteRequest(String apiPath)   {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authenticateClient.getToken())
                .when().delete(Config.getProperty("rest_url") + apiPath)
                .then().log().all()
                .extract().response();
    }
}
