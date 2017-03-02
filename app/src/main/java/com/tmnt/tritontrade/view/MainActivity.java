package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    static String userName = "";
    static String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login
        loginButton =  (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userName = ((TextView) findViewById(R.id.userNameText)).getText().toString();
                password = ((TextView) findViewById(R.id.passwordText)).getText().toString();
                new LoginTask().execute();
            }
        });



        // Server.test();

        //Adding posts to server for testing


    }

    //If the user clicks on the "Forgot Password?" button, they will be redirected to
    //the forgot password activity
    public void sendToForgotActivity(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
    //If the user clicks on the "Sign Up" button, they will be redirected to
    //the register account activity
    public void sendToRegisterAccount(View view){
        Intent intent = new Intent(this, Register_Account.class);
        startActivity(intent);
    }

    private class LoginTask extends AsyncTask<Object, Object, Object>{
        @Override
        protected Object doInBackground(Object... params) {
            try {
                CurrentState.getInstance().setCurrentUser(Server.login(userName, password));
            }
            catch(IOException e){
                Log.d("DEBUG", e.toString());
            }
            return CurrentState.getInstance();
        }

        @Override
        protected void onPostExecute(Object result){
            if(CurrentState.getInstance().getCurrentUser() != null){
                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
            }
            else{
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CreatePostTask extends AsyncTask<Post, Void, Void> {
        @Override
        protected Void doInBackground(Post... params) {
            try {
                Post post = params[0];

                Server.addPost(post.getProductName(), post.getPhotos(), post.getDescription(),
                        post.getPrice(), post.getTags(), post.getProfileID(), post.getSelling(), post.getContactInfo());
            }
            catch(IOException e){
                Log.d("DEBUG", e.toString());
            }
            return null;
        }
/*
        @Override
        protected void onPostExecute(Void result){
            startActivity(new Intent(getApplicationContext(), Mainfeed.class));
        }*/
    }

    public void postTest(){
        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<String> photos = new ArrayList<>();
        photos.add("http://www.gankoramen.com/wp-content/uploads/2015/03/Miso-Ramen1.jpg");
        tags.add("yum");
        tags.add("food");
        Post a = new Post("Ramen", photos, "Yummy ramen for sale",
                100f, tags,1,1,true, true, new Date(),"None",false);
        new CreatePostTask().execute(a);
    }



}
