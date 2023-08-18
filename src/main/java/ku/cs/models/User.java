package ku.cs.models;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class User {
    private String username;
    private String password;

    public User(String username) {
        this.username = username;
        password = null;
    }

    public User(String username, String password) {
        this(username);
        setPassword(password);
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public void setPassword(String password) {
        // more info: https://github.com/patrickfav/bcrypt
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean validatePassword(String password) {
        // more info: https://github.com/patrickfav/bcrypt
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        return result.verified;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}