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
                    //get input from the field
                    String numberInput = userPhone.getText().toString();
                    //convert to database format
                    thePhone = convertMobileNumberToDatabaseFormat(numberInput);

                    //if input phone number was not in valid format, throw exception
                    if (thePhone == null)
                    {
                        throw new IllegalArgumentException("PHONE");
                    }

                    //Toast.makeText(Register_Account.this, "Phone is " + thePhone, Toast.LENGTH_SHORT).show();
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

    /**
     * Converts the user input of various formats into the database format. If no country code
     * is given, defaults to +1
     *
     * Accepted formats:
     * Database format: "+xxxx (xxx) xxx-xxxx"
     * No country code: "(xxx) xxx-xxxx"
     * Just 10 digits: "xxxxxxxxxx"
     * 10 Digits with space and 2 dashes: "xxx xxx-xxxx"
     * 10 Digits with dashes: "xxx-xxx-xxxx"
     *
     * @param number The user input number
     * @return The database formatted number, null if the input was not one of the accepted formats
     */
    public static String convertMobileNumberToDatabaseFormat(String number)
    {
        //if doesnt match any of the formats, return null
        if(!number.matches("^\\+[0-9][0-9][0-9][0-9] \\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$") // full
                && !number.matches("^\\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$") // without country code
                && !number.matches("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$") // just 10 numbers
                && !number.matches("^[0-9][0-9][0-9] [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$") // 10 numbers with space and two dashes
                && !number.matches("^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$")) // 10 numbers with dashes
        {
            return null;
        }
        else if(number.matches("^\\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$"))
        {
            //no country code, assume is United States, prepend U.S. country code
            return "+0001 " + number;
        }
        else if(number.matches("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
        {
            // if just 10 numbers, add country code and parenthesis
            return "+0001 (" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6);
        }
        else if(number.matches("^[0-9][0-9][0-9] [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$"))
        {
            //if no parentheses or country code, add them
            return "+0001 (" + number.substring(0, 3) + ")" + number.substring(3);
        }
        else if(number.matches("^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$"))
        {
            //if use dashes to separate area code and number, replace with parentheis and add country code
            return "+0001 (" + number.substring(0, 3) + ") " + number.substring(4);
        }

        //something weird happened, return null
        return null;
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
