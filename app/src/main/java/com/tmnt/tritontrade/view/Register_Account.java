package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.IOException;

public class Register_Account extends AppCompatActivity {

    Button registerButton;
    static String theName = "";
    static String theEmail = "";
    static String thePassword = "";
    static String thePhone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__account);

        registerButton = (Button) findViewById(R.id.nextPageLabel);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText userName = (EditText) findViewById(R.id.nameLabel);
                EditText userEmail = (EditText) findViewById(R.id.emailLabel);
                EditText userPassword = (EditText) findViewById(R.id.passwordLabel);
                EditText userPhone = (EditText) findViewById(R.id.phoneLabel);

                try {
                    theName = userName.getText().toString();
                    if(theName.equals("")){
                        throw new IllegalArgumentException("NAME");
                    }
                    theEmail = userEmail.getText().toString();
                    if(!theEmail.matches(".*ucsd.edu$")){
                        throw new IllegalArgumentException("EMAIL");
                    }

                    thePassword = userPassword.getText().toString();
                    if(thePassword.equals("")) {
                        throw new IllegalArgumentException("PASSWORD");
                    }
                    thePhone = userPhone.getText().toString();
                    if(!thePhone.matches("^\\+[0-9][0-9][0-9][0-9] \\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$") // full
                            && !thePhone.matches("^\\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$") // without country code
                            && !thePhone.matches("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$") // just 10 numbers
                            && !thePhone.matches("^[0-9][0-9][0-9] [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$") // 10 numbers with space and two dashes
                            && !thePhone.matches("^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$")){ // 10 numbers with dashes
                        throw new IllegalArgumentException("PHONE");
                    }else if(thePhone.matches("^\\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$")){
                        thePhone = "+0001 " + thePhone;
                    }else if(thePhone.matches("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$")){
                        thePhone = "+0001 (" + thePhone.substring(0, 3) + ") " + thePhone.substring(3, 6) + "-" + thePhone.substring(6);
                    }else if(thePhone.matches("^[0-9][0-9][0-9] [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$")){
                        thePhone = "+0001 (" + thePhone.substring(0, 3) + ")" + thePhone.substring(3);
                    }else if(thePhone.matches("^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$")){
                        thePhone = "+0001 (" + thePhone.substring(0, 3) + ") " + thePhone.substring(4);
                    }
                    Toast.makeText(Register_Account.this, "Phone is " + thePhone, Toast.LENGTH_SHORT).show();
                    new RegisterTask().execute();
                } catch(IllegalArgumentException e) {
                    Log.d("DEBUG", e.toString());

                    if(e.getMessage().equals("NAME")) {
                        Toast.makeText(Register_Account.this, "Bad Username ", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("EMAIL")) {
                        Toast.makeText(Register_Account.this, "Bad Email", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("PASSWORD")) {
                        Toast.makeText(Register_Account.this, "Bad Password", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("PHONE")) {
                        Toast.makeText(Register_Account.this, "Bad Phone", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //If the user clicks "Log In" they will be redirected to the
    //log in screen
    public void sendToLogIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //If the user clicks "Next" they will be redirected to the Verify email page
    public void sendToVerifyAccount(View view) {
        Intent intent = new Intent(this, Verify_Account.class);
        startActivity(intent);
    }

    private class RegisterTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                return Server.addNewUser(theName, "", "", thePhone, theEmail, thePassword);
            } catch (IOException e){
                Log.d("DEBUG", e.toString());
                return null;
            } catch (IllegalArgumentException e){
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if(result!=null){
                startActivity(new Intent(getApplicationContext(), Verify_Account.class));
            }else{
                Toast.makeText(Register_Account.this, "Register Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
