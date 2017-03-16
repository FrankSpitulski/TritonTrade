package com.tmnt.tritontrade.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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


        displayLogoutDialog();

    }


    //////////////////confirmation button for remove from cart//////////////////
    private void displayLogoutDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout?");
        alert.setMessage("Are you sure you want to log out?");
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing happens
            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "User logged out", Toast.LENGTH_SHORT).show();

                //deletes post and reloads page without this  removed post
                CurrentState.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
