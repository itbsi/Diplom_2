package api;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class DeleteExistingUser extends RestAssuredSpec {
    @Step
    public void delete(String token) {

        given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .delete("api/auth/user")
                .then()
                .statusCode(202);
    }
}
