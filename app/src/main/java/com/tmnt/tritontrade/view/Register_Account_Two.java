package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tmnt.tritontrade.R;

public class Register_Account_Two extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__account__two);
    }
    //If the user clicks "Finish" they will be redirected to the
    //verify email screen
    public void sendToVerifyEmail(View view){
        Intent intent = new Intent(this, Verify_Account.class);
        startActivity(intent);
    }
    //If the user clicks "Log In" they will be redirected to the
    //log in screen
    public void sendToLogIn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
