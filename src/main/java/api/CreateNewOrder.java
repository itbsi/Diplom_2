package api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CreateNewOrder extends RestAssuredSpec {
    private static final String ORDER_PATH = "api/orders";

    Response response;

    @Step("Создание заказа")
    public Response createOrder(String ingredientName, String token) {
        String json = "{\"ingredients\": [\"" + ingredientName + "\" ]}";
        return response = given()
                .auth().oauth2(token)
                .spec(getBaseSpec())
                .body(json)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderNoAuth(String ingredientName) {
        String json = "{\"ingredients\": [\"" + ingredientName + "\" ]}";
        return response = given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .post(ORDER_PATH);
    }
}
