package com.example.mychat.Authentication;

public class UserModelClass {
    private String UserId,UserName,UserEmail,UserPhone,UserPassword;

    public UserModelClass() {
    }

    public UserModelClass(String userId, String userName, String userEmail, String userPhone, String userPassword) {
        UserId = userId;
        UserName = userName;
        UserEmail = userEmail;
        UserPhone = userPhone;
        UserPassword = userPassword;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
