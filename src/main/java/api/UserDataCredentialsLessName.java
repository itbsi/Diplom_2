package api;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class UserDataCredentialsLessName {
    public final String email;
    public final String name;

    public UserDataCredentialsLessName(String email, String name) {
        this.email = email;
        this.name= name;
    }
    public static UserDataCredentialsLessName getRandom() {
        final String email = "alrt" +  RandomStringUtils.random(5) + "@gmail.su";
        final String name = RandomStringUtils.randomAlphabetic(10).toLowerCase(Locale.ROOT);

        return new UserDataCredentialsLessName(email, name);
    }

}
