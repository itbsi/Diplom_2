package api;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestCreateNewOrderParameterized extends RestAssuredSpec {

    private final int expected;
    private final String ingredientName;

    CreateNewOrder createNewOrder = new CreateNewOrder();
    UserData userData = UserData.getRandom();
    Register register = new Register();
    DeleteExistingUser deleteExistingUser = new DeleteExistingUser();

    public TestCreateNewOrderParameterized(String ingredientName, int expected) {
        this.ingredientName = ingredientName;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}:CreateOrder({0};{1})")
    public static Object[][] CreateNewOrderParameterized() {
        return new Object[][]{
                {"61c0c5a71d1f82001bdaaa6f", 200},
                {null, 500},
                {"sm" ,500},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void CreateOrderAuthTest(){
        register.create(userData);
        createNewOrder.createOrder(ingredientName, register.response.path("accessToken").toString().substring(7));
        createNewOrder.response.then().assertThat().statusCode(expected);
    }
    @After
    public void deleteUser() {
        if (register.response.body().path("success").equals(false)) {
            createNewOrder.response.path("message").equals("Internal Server Error.");
            return;
        }
        Assert.assertNotNull(createNewOrder.response.path("name"));
        createNewOrder.response.path("success").equals(true);
        Assert.assertNotNull(createNewOrder.response.path("order.number"));
        register.response.then().assertThat().statusCode(200);
        deleteExistingUser.delete(register.response.path("accessToken").toString().substring(7));
    }
}
