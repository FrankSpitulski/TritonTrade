package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;

public class Register_Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__account);
    }
    //If the user clicks "Log In" they will be redirected to the
    //log in screen
    public void sendToLogin(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //If the user clicks "Next" they will be redirected to the Verify email page
    public void sendToRegisterAccountTwo(View view){
        Intent intent = new Intent(this, Register_Account_Two.class);
        startActivity(intent);
    }

  /*  private class RegisterTask extends AsyncTask<Object, Object, boolean> {
        @Override
        protected boolean doInBackground(Object... params) {
            try {
                Server.sendPasswordResetEmail();
            }
            catch{

            }
        }

        @Override
        protected void onPostExecute(boolean result){

        }
    }*/

}
