package com.tmnt.tritontrade.controller;

/**
 * Created by frank on 2/21/17.
 */

public class CurrentState {
    private static User currentUser;

    public static void setCurrentUser(User u) {
        currentUser = u;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
