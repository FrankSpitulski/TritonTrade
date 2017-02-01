package com.tmnt.tritontrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Server.test(this);
    }

    //If the user clicks on the "Forgot Password?" button, they will be redirected to
    //the forgot password activity
    public void sendToForgotActivity(View view) {

        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);

    }

}
