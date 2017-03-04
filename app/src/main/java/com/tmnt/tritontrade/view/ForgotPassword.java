package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;

public class ForgotPassword extends AppCompatActivity {
    static String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button emailForgot =  (Button)findViewById(R.id.emailForgotButton);
        emailForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = ((TextView) findViewById(R.id.emailForgotText)).getText().toString();
                new ForgotPassword.forgotPasswordTask().execute();
            }
        });
    }

    private class forgotPasswordTask extends AsyncTask<Object, Object, Boolean> {
        @Override
        protected Boolean doInBackground(Object... params) {
            try{
                return Server.sendPasswordResetEmail(email);
            }
            catch(IOException e){
                Log.d("DEBUG", e.toString());
                return false;
            }
            catch(IllegalArgumentException e2){
                Log.d("DEBUG", e2.toString());
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result){
            Toast toast = new Toast(ForgotPassword.this);
            if(result){
                toast.cancel();
                toast.makeText(ForgotPassword.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else{
                toast.cancel();
                toast.makeText(ForgotPassword.this, "Password reset failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
