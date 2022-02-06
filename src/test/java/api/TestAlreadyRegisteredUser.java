package api;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestAlreadyRegisteredUser {

    @Test
    @DisplayName("Проверка уже зарегистрированного пользователя")
    public void failedRegistrationWithSameMail() {
        UserData userData = UserData.getRandom();
        Register register = new Register();
        DeleteExistingUser deleteExistingUser = new DeleteExistingUser();
        register.create(userData);
        assertEquals(register.response.body().path("success"), true);
        String token = register.response.path("accessToken").toString().substring(7);
        Response response2 = register.create(userData);
        response2.then().statusCode(403);
        assertEquals(response2.body().path("success"), false);
        response2.body().path("message").equals("User already exists");
        deleteExistingUser.delete(token);
    }
}
