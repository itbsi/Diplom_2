package api;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.commons.lang3.RandomStringUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class TestRegisterParameterized {
    private final String mail;
    private final String password;
    private final String name;
    private final boolean expected;
    Register register = new Register();
    DeleteExistingUser deleteExistingUser = new DeleteExistingUser();

    public TestRegisterParameterized(String mail, String password, String name, boolean expected) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.expected = expected;
    }


    @Parameterized.Parameters(name = "{index}:Registration({0};{1};{2};{3})")
    public static Object[][] Registration() {
        return new Object[][]{
                {null, "zxcv", "John", false},
                {"st" + RandomStringUtils.random(5) + "@yandex.ru", null, "John", false},
                {"sm" + RandomStringUtils.random(5) + "@yandex.ru", "zxcv", null, false},
                {"sv" + RandomStringUtils.random(5) + "@yandex.ru", "zxcv", "John", true},
        };
    }

    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    public void failedRegistrationWithNullMail() {
        UserData userData = new UserData(mail, password, name);
        register.create(userData);
        assertEquals(register.response.body().path("success"), expected);
        if (register.response.body().path("success").equals(false)) {
            register.response.path("message").equals("Email, password and name are required fields");
            register.response.then().assertThat().statusCode(403);
        } else {
            register.response.then().assertThat().statusCode(200);
            register.response.path("user.email").equals(userData.email);
            register.response.path("user.name").equals(userData.name);
            assertNotNull(register.response.path("accessToken"));
            assertNotNull(register.response.path("refreshToken"));
        }
    }

    @After
    public void clear() {
        if (register.response.body().path("success").equals(false)){
            return;
        }
        register.response.then().assertThat().statusCode(200);
        deleteExistingUser.delete(register.response.path("accessToken").toString().substring(7));
    }
}