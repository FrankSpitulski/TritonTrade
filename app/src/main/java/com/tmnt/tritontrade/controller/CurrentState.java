package com.tmnt.tritontrade.controller;

/**
 * Created by frank on 2/23/17.
 */
public class CurrentState {
    private static CurrentState ourInstance = new CurrentState();

    public static CurrentState getInstance() {
        return ourInstance;
    }

    private CurrentState() {}

    private User currentUser;

    public synchronized void setCurrentUser(User u) {
        currentUser = u;
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    public synchronized void logOut(){
        currentUser=null;
    }
}
