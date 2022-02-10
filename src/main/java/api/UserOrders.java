package api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserOrders extends RestAssuredSpec {

    private static final String ORDERS_PATH = "api/orders";

    Response response;

    public Response getAllUserOrdersWitnNOAuth() {
        return response = given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }
    public Response getAllUserOrders(String token) {
        return response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(ORDERS_PATH);
    }
}
