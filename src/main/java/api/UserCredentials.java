package api;


public class UserCredentials {
        public String name;
        public String email;

        public UserCredentials(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public static UserCredentials from(UserData userData) {
            final String email = userData.email;
            final String name = userData.name;
            return new UserCredentials(email, name);
        }
}
