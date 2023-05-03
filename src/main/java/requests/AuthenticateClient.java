package requests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class AuthenticateClient {

    private String token;

    public AuthenticateClient() {
        new Config();
        token = getLogInToken();
    }

    private static String getLogInToken() {
        JSONObject logInPayload = new JSONObject();
        logInPayload.put("userName", Config.getProperty("auth_username"));
        logInPayload.put("password", Config.getProperty("auth_password"));
        return getResponse("/Account/v1/GenerateToken", logInPayload).getBody().jsonPath().getString("token");
    }

    private static Response getResponse(String authEndpoint, JSONObject payload)  {
        RequestSpecification requestSpecification = new RequestSpecBuilder().setRelaxedHTTPSValidation().setContentType(ContentType.JSON).build();
        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
        return given(requestSpecification)
                .log().ifValidationFails()
                .body(payload.toString())
                .when().post(Config.getProperty("rest_url") + authEndpoint)
                .then()
                .log().ifValidationFails()
                .spec(responseSpecification)
                .extract().response();
    }

    public String getToken()    {   return token; }
}
