package ohtu.services;

import ohtu.domain.User;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password

        return usernameTooShort(username)
                || passwordTooShort(password)
                || passwordContainsOnlyLetters(password)
                || usernameNotOnlyLowerCaseLetters(username);
    }

    private boolean usernameTooShort(String username) {
        return username.length() < 3;
    }

    private boolean passwordTooShort(String password) {
        return password.length() < 8;
    }

    private boolean usernameNotOnlyLowerCaseLetters(String username) {
        return !username.matches("[a-z]+");
    }

    private boolean passwordContainsOnlyLetters(String password) {
        return password.matches("[a-zA-Z]+");
    }
}
