package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Server;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Server.test(this);

        Button test = (Button) findViewById(R.id.poptest);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, PopUpPost.class);
                startActivity(t);
            }
        });

    }

    //If the user clicks on the "Forgot Password?" button, they will be redirected to
    //the forgot password activity
    public void sendToForgotActivity(View view) {

        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);

    }

}
