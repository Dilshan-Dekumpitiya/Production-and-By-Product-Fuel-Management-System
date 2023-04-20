package lk.ijse.palmoilfactory.util;


public class Regex {
    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{3,}$";
    private static final String PASSWORD_REGEX = "[aA-zZ0-9]{8,20}$";

    public static boolean validateUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }


}