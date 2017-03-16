package com.tmnt.tritontrade.controller;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tmnt.tritontrade.view.MainActivity;

/**
 * Created by frank on 2/23/17.
 */
public class CurrentState {
    private static CurrentState ourInstance = new CurrentState();

    public static CurrentState getInstance() {
        if(ourInstance == null){
            ourInstance = new CurrentState();
            Log.d("DEBUG", "signleton is null. you're in for a ride.");
        }
        return ourInstance;
    }

    private CurrentState() {}

    private User currentUser;

    public synchronized void setCurrentUser(User u) {
        currentUser = u;
    }

    public synchronized User getCurrentUser() throws UnsupportedOperationException{
        if (currentUser == null) throw new UnsupportedOperationException();
        return currentUser;
    }

    public synchronized  boolean isLoggedIn(){
        return currentUser != null;
    }

    public synchronized void logOut(){
        currentUser=null;
    }

    public void killLogin(Activity a){
        Log.d("DEBUG", "User session expired from " + a.toString());
        Toast.makeText(a.getBaseContext(), "User session expired.", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(a.getBaseContext(), MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(in);
    }
}
