package com.yusesc.myapplication;

import android.app.Application;

public class CurrentUser extends Application {
    private UserAccount user;

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
