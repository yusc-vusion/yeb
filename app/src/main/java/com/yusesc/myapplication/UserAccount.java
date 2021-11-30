package com.yusesc.myapplication;

/*
사용자 계정 정보 클래스
 */

public class UserAccount {
    private String idToken;     // Firebase UID 고유 토큰정보)
    private String emailID;
    private String password;
    private String nickname;

    public UserAccount(){}
    public UserAccount(String idToken, String emailID, String password, String nickname) {
        this.idToken = idToken;
        this.emailID = emailID;
        this.password = password;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
