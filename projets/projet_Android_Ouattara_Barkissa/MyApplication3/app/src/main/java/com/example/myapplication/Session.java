package com.example.myapplication;

import com.example.myapplication.Model.User;

public class Session {

    public static User currentUser = null;

    public static void setCurrentUser(User user){
        currentUser = user;
    }

    public static User getCurrentUser(){
        return currentUser;
    }
}
