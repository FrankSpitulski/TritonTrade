package com.tmnt.tritontrade.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;

public class ForgotPassword extends AppCompatActivity {
    private static String email = "";

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
        private ProgressDialog dialog = new ProgressDialog(ForgotPassword.this);
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
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast toast = new Toast(ForgotPassword.this);
            if(result){
                toast.cancel();
                Toast.makeText(ForgotPassword.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else{
                toast.cancel();
                Toast.makeText(ForgotPassword.this, "Password reset failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
