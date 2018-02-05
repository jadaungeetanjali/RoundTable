package com.silive.pc.roundtable;

/**
 * Created by PC on 2/2/2018.
 */

public class User {
    private String username;
    private String email;
    private String password;

    public User(String email, String password, String username) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
