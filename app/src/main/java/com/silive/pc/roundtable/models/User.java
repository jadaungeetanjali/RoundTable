package com.silive.pc.roundtable.models;

/**
 * Created by PC on 2/2/2018.
 */

public class User {
    private String user;
    private String email;
    private String password;
    private String token;

    public User(String email, String password, String user, String token) {
        this.token = token;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
