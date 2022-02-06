package api;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class TestLogin {
    Register register = new Register();
    Login login = new Login();
    DeleteExistingUser deleteExistingUser = new DeleteExistingUser();

    @Test
    @Description("Успешная авторизация")
    public void loginByUser() {
        UserData userData = UserData.getRandom();
        LoginCredentials loginCredentials = new LoginCredentials(userData.email, userData.password);
        register.create(userData);
        login.login(loginCredentials);
        login.response.then().assertThat().statusCode(200);
        login.response.path("success").equals(true);
        login.response.path("user.email").equals(userData.email);
        login.response.path("user.name").equals(userData.name);
        assertNotNull(login.response.path("accessToken"));
    }

    @Test
    @Description("Некорректные креды - email")
    public void loginWithInvalidMail() {
        UserData userData = UserData.getRandom();
        LoginCredentials loginCredentials = new LoginCredentials("§jhghjg§@zxc.fh", userData.password);
        register.create(userData);
        login.login(loginCredentials);
        login.response.then().assertThat().statusCode(401);
        login.response.path("message").equals("email or password are incorrect");
    }

    @Test
    @Description("Некорректные креды - pass")
    public void loginWithInvalidPassword() {
        UserData userData = UserData.getRandom();
        LoginCredentials loginCredentials = new LoginCredentials(userData.email, "zxcvbn");
        register.create(userData);
        login.login(loginCredentials);
        login.response.then().assertThat().statusCode(401);
        login.response.path("message").equals("email or password are incorrect");
    }

    @After
    public void delete () {
        deleteExistingUser.delete(register.response.path("accessToken").toString().substring(7));
    }
}
