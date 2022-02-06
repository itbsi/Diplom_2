package api;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGetExistingUserOrders {

    UserData userData = UserData.getRandom();
    Register register = new Register();
    DeleteExistingUser deleteExistingUser = new DeleteExistingUser();
    UserOrders userOrders = new UserOrders();
    CreateNewOrder createNewOrder = new CreateNewOrder();

    @Test
    @DisplayName("Получение всех заказов пользователя с авторизацией")
    public void getAllUserOrdersAuth() {
        register.create(UserData.getRandom());
        String token = register.response.path("accessToken").toString().substring(7);
        createNewOrder.createOrder("61c0c5a71d1f82001bdaaa75", token);
        createNewOrder.response.then().assertThat().statusCode(200);
        String orderNumber = createNewOrder.response.body().path("order.number").toString();
        userOrders.getAllUserOrders(token);
        userOrders.response.then().assertThat().statusCode(200);
        userOrders.response.path("success").equals(true);
        assertNotNull(userOrders.response.path("total"));
        assertNotNull(userOrders.response.path("totalToday"));
        assertNotNull(userOrders.response.path("_id"));
        assertNotNull(userOrders.response.path("status"));
        assertNotNull(userOrders.response.path("createdAt"));
        assertNotNull(userOrders.response.path("updatedAt"));
        String userOrderNumber = userOrders.response.path("order.number").toString();
        assertEquals(orderNumber, userOrderNumber);
        deleteExistingUser.delete(token);
    }

    @Test
    @DisplayName("Получение всех заказов пользователя без авторизации")
    public void getAllUserOrdersNoAuth() {
        register.create(userData);
        String token = register.response.path("accessToken").toString().substring(7);
        createNewOrder.createOrder("61c0c5a71d1f82001bdaaa75", register.response.path("accessToken").toString().substring(7));
        userOrders.getAllUserOrdersWitnNOAuth();
        userOrders.response.then().assertThat().statusCode(401);
        assertEquals("You should be authorised", userOrders.response.path("message"));
        deleteExistingUser.delete(token);
    }

}
