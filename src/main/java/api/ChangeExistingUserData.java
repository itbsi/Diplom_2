package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ChangeExistingUserData extends RestAssuredSpec {

    Response response;

    @Step("Обновление пользовательских данных без имени")
    public Response updateUserData(String token, UserDataCredentialsLessName userDataCredentialsLessName) {
        return response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(userDataCredentialsLessName)
                .when()
                .patch("api/auth/user");
    }
    @Step("Обновление пользовательских данных для той же почты")
    public Response updateUserDataSameMail(String token, UserCredentials userCredentials) {
        return response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(userCredentials)
                .when()
                .patch("api/auth/user");
    }
}
