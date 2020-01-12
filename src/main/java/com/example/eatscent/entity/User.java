package com.example.eatscent.entity;

import java.util.Objects;

public class User {
    private int UserID;
    private String UserName;
    private String Password;
    private String UserIphone;
    private String UserAdress;
    private String UserGread;
    private String UserComment;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserIphone() {
        return UserIphone;
    }

    public void setUserIphone(String userIphone) {
        UserIphone = userIphone;
    }

    public String getUserAdress() {
        return UserAdress;
    }

    public void setUserAdress(String userAdress) {
        UserAdress = userAdress;
    }

    public String getUserGread() {
        return UserGread;
    }

    public void setUserGread(String userGread) {
        UserGread = userGread;
    }

    public String getUserComment() {
        return UserComment;
    }

    public void setUserComment(String userComment) {
        UserComment = userComment;
    }
    @Override
    public String toString() {
        return "User{" +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", UserIphone='" + UserIphone + '\'' +
                ", UserAdress='" + UserAdress + '\'' +
                ", UserGread='" + UserGread + '\'' +
                ", UserComment='" + UserComment + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return UserID == user.UserID &&
                java.util.Objects.equals(UserName, user.UserName) &&
                java.util.Objects.equals(Password, user.Password) &&
                UserIphone.equals(user.UserIphone) &&
                UserAdress.equals(user.UserAdress) &&
                UserGread.equals(user.UserGread) &&
                UserComment.equals(user.UserComment);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), UserID, UserName, Password, UserIphone, UserAdress, UserGread, UserComment);
    }
}
