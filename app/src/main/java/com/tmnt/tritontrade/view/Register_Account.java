package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;

public class Register_Account extends AppCompatActivity {

    Button registerButton;
    static String theName = "";
    static String theEmail = "";
    static String thePassword = "";
    static String thePhone = "";
    static String autoFill = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__account);

        registerButton = (Button) findViewById(R.id.nextPageLabel);

        /*final EditText userPhone = (EditText) findViewById(R.id.phoneLabel);
        autoFill = userPhone.getText().toString();
        userPhone.addTextChangedListener(new TextWatcher() {
            int len=0;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //String str = userPhone.getText().toString();
                if(autoFill.length()==1 && len < autoFill.length()){
                    userPhone.append("+");
                }
                if(autoFill.length()==6 && len < autoFill.length()){
                    userPhone.append(" (");
                }
                if (autoFill.length() == 10 && len < autoFill.length()) {
                    userPhone.append(") ");
                }
                if (autoFill.length() == 16 && len < autoFill.length()){
                    userPhone.append("-");
                }
                            /*if((autoFill.length()==5 && len <str.length()) || (str.length()==13 && len <str.length())){
                                //checking length  for backspace.
                                etNICNO_Sender.append("-");
                                //Toast.makeText(getBaseContext(), "add minus", Toast.LENGTH_SHORT).show();
                            }*/
          /*  }

        });*/
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText userName = (EditText) findViewById(R.id.nameLabel);
                EditText userEmail = (EditText) findViewById(R.id.emailLabel);
                EditText userPassword = (EditText) findViewById(R.id.passwordLabel);
                EditText userPhone = (EditText) findViewById(R.id.phoneLabel);

                try {
                    theName = userName.getText().toString();
                    if(theName.equals("")){
                        throw new IllegalArgumentException("NO_NAME");
                    }
                    if(theName.toLowerCase().contains("fuck") || theName.toLowerCase().contains("ass") || theName.toLowerCase().contains("bitch")){
                        throw new IllegalArgumentException("NICE_TRY");
                    }
                    theEmail = userEmail.getText().toString();
                    if(!theEmail.matches(".*ucsd.edu$")){
                        throw new IllegalArgumentException("NON_UCSD_EMAIL");
                    }

                    thePassword = userPassword.getText().toString();
                    if(thePassword.equals("")) {
                        throw new IllegalArgumentException("NO_PASSWORD");
                    }
                    if(thePassword.length()<4){
                        throw new IllegalArgumentException("SHORT_PASSWORD");
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

                    if(e.getMessage().equals("NO_NAME")) {
                        Toast.makeText(Register_Account.this, "Sorry, you need to enter a name!", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("NICE_TRY")) {
                        Toast.makeText(Register_Account.this, "Nice try, but do you really want people to call you that?", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("NON_UCSD_EMAIL")) {
                        Toast.makeText(Register_Account.this, "Sorry, you must use your UCSD email.", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("NO_PASSWORD")) {
                        Toast.makeText(Register_Account.this, "You need to enter a password", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("SHORT_PASSWORD")) {
                        Toast.makeText(Register_Account.this, "For safety, your password must be greater than 4 characters.", Toast.LENGTH_SHORT).show();
                    }else if(e.getMessage().equals("PHONE")) {
                        Toast.makeText(Register_Account.this, "Please enter a valid phone number. We promise not to contact you.", Toast.LENGTH_SHORT).show();
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

        //Return the original number, is already in database format
        return number;
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
                Toast.makeText(Register_Account.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
