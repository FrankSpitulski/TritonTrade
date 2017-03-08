package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void sendToDevTeam(View view){
        Intent intent = new Intent(this, DeveloperTeam.class);
        startActivity(intent);
    }

    public void sendToLicesnse(View view) {
        Intent intent = new Intent(this, License_Displays.class);
        startActivity(intent);
    }

    public void sendToChangePW(View view) {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }


    public void logoutUser(View view) {
        CurrentState.getInstance().setCurrentUser(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setTheme(View view) {
    }
}
