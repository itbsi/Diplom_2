package api;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestChangeExistingUserData {

    Register register = new Register();
    UserDataCredentialsLessName userDataCredentialsLessName = UserDataCredentialsLessName.getRandom();
    UserData userData = UserData.getRandom();
    ChangeExistingUserData changeUserData = new ChangeExistingUserData();
    DeleteExistingUser deleteExistingUser = new DeleteExistingUser();
    Login login = new Login();
    LoginCredentials loginCredentials = new LoginCredentials(userData.email, userData.password);
    GetExistingUserData getExistingUserData = new GetExistingUserData();

    @Test
    @DisplayName("Изменение данных существующего пользователя")
    public void ChangeUserDataTest() {
        register.create(userData);
        login.login(loginCredentials);
        changeUserData.updateUserData(register.response.path("accessToken").toString().substring(7), userDataCredentialsLessName);
        changeUserData.response.then().assertThat().statusCode(200);
        getExistingUserData.getUserData(register.response.path("accessToken").toString().substring(7));
        assertEquals(getExistingUserData.response.body().path("user.email"), userDataCredentialsLessName.email);
        assertEquals(getExistingUserData.response.body().path("user.name"), userDataCredentialsLessName.name);
    }

    @Test
    @DisplayName("Изменение данных не существующего пользователя")
    public void notSuccessfulChangeUserDataNoAuthTest() {
        register.create(userData);
        changeUserData.updateUserData("Token", userDataCredentialsLessName);
        changeUserData.response.then().assertThat().statusCode(403);
        changeUserData.response.path("message").equals("You should be authorised");
        changeUserData.response.path("success").equals("failed");
    }

    @Test
    @DisplayName("Изменение данных существующего пользователя на те же")
    public void failedChangeUserDataTestWithSameEmail() {
        register.create(userData);
        String name = register.response.path("name");
        String email = register.response.path("email");
        UserCredentials userCredentials = new UserCredentials(email, name);
        changeUserData.updateUserDataSameMail(register.response.path("accessToken").toString().substring(7), userCredentials);
        changeUserData.response.then().assertThat().statusCode(403);
        changeUserData.response.path("message").equals("User with such email already exists");
        changeUserData.response.path("success").equals("failed");
    }
    @After
    public void deleteUser() {
        if (register.response.body().path("success").equals(false)) {
            return;
        }
        register.response.then().assertThat().statusCode(200);
        deleteExistingUser.delete(register.response.path("accessToken").toString().substring(7));
    }
}
