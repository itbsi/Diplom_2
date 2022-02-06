package api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class TestCreateNewOrder {

    CreateNewOrder createNewOrder = new CreateNewOrder();

    @Test
    @DisplayName("Ошибка создания заказа без авторизации")
    public void CreateOrderNoAuthTest() {
        createNewOrder.createOrderNoAuth("61c0c5a71d1f82001bdaaa75");
        createNewOrder.response.then().assertThat().statusCode(400);
        createNewOrder.response.path("message").equals("You should be authorised");
        createNewOrder.response.path("success").equals(false);
    }
}

