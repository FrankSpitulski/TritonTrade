package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tmnt.tritontrade.R;

public class Verify_Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__account);

    }
    //If the user clicks "Log In" they will be redirected to the
    //log in screen
    public void sendToLogIn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
