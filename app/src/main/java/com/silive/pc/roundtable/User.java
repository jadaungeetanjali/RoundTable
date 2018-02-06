package com.silive.pc.roundtable;

/**
 * Created by PC on 2/2/2018.
 */

public class User {
    private String user;
    private String email;
    private String password;

    public User(String email, String password, String username) {
        this.user = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
    public String getUsername() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
