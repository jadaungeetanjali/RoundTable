package com.silive.pc.roundtable.models;

/**
 * Created by PC on 3/6/2018.
 */

public class AddUserModel {
    private String name, email, avatarName, avatarColor, _id;

    public AddUserModel(String name, String email, String avatarName, String avatarColor){
        this.name = name;
        this.email = email;
        this.avatarName = avatarName;
        this.avatarColor = avatarColor;
    }

    public AddUserModel(String name, String email, String avatarName, String avatarColor, String _id){
        this.name = name;
        this.email = email;
        this.avatarName = avatarName;
        this.avatarColor = avatarColor;
        this._id = _id;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public String getAvatarColor() {
        return avatarColor;
    }
}
