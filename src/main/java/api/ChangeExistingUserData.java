package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ChangeExistingUserData extends RestAssuredSpec {
    public static void main( String[] args ) {}
    private static final String UPD_USERDATA_PATH = "api/auth/user";

    Response response;

    @Step
    public Response updateUserData(String token, UserDataCredentialsLessName userDataCredentialsLessName) {
        return response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(userDataCredentialsLessName)
                .when()
                .patch(UPD_USERDATA_PATH);
    }
    @Step
    public Response updateUserDataSameMail(String token, UserCredentials userCredentials) {
        return response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(userCredentials)
                .when()
                .patch(UPD_USERDATA_PATH);
    }
}
