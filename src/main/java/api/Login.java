package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Login extends RestAssuredSpec {
    private static final String LOGIN_PATH = "api/auth/login";

    Response response;

    @Step
    public Response login(LoginCredentials loginCredentials) {

        return response = given()
                .spec(getBaseSpec())
                .body(loginCredentials)
                .when()
                .post(LOGIN_PATH);
    }
}
