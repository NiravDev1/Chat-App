package com.example.mychat.Authentication;

import java.util.Map;

public class UserModelClass  {
    private String UserId,UserName,UserEmail,UserPhone,UserPassword,UserProfile;

    public UserModelClass(String userId, String userName, String userEmail, String userPhone, String userPassword, String userProfile) {
        UserId = userId;
        UserName = userName;
        UserEmail = userEmail;
        UserPhone = userPhone;
        UserPassword = userPassword;
        UserProfile = userProfile;
    }

    public UserModelClass(String userName, String userPhone, String userProfile) {
        UserName = userName;
        UserPhone = userPhone;
        UserProfile = userProfile;
    }

    public UserModelClass() {
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

    public String getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(String userProfile) {
        UserProfile = userProfile;
    }
}
