package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.view.MainActivity;

public class Verify_Account_Two extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__account__two);
    }
    //If the user clicks "Log In" they will be redirected to the
    //log in screen
    public void sendToLogIn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
