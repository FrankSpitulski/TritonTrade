package com.tmnt.tritontrade.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    static String userName = "";
    static String password = "";

    // Variables for remembering the users login credentials
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;
    private EditText editTextUsername;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login
        loginButton =  (Button)findViewById(R.id.loginButton);

        // Saves the email of the user if the check box is clicked
        editTextUsername = (EditText)findViewById(R.id.userNameText);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            saveLoginCheckBox.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userName = ((TextView) findViewById(R.id.userNameText)).getText().toString();
                password = ((TextView) findViewById(R.id.passwordText)).getText().toString();

                // When the login button is clicked, fill in the username with the stored value
                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", userName);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                new LoginTask().execute();
            }
        });



        // Server.test();

        //Adding posts to server for testing

        TextView logo = (TextView)findViewById(R.id.textView4);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Brotherina.ttf");
        logo.setTypeface(custom_font);


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
        private ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        @Override
        protected Object doInBackground(Object... params) {
            try {
                CurrentState.getInstance().setCurrentUser(Server.login(userName, password));
            }
            catch(IOException e){
                Log.d("DEBUG", e.toString());
            }
            catch(IllegalArgumentException e2){
                Log.d("DEBUG", e2.toString());
            }
            return CurrentState.getInstance();
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Logging in...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Object result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(CurrentState.getInstance().getCurrentUser() != null){
                //Sharepreferences
                String ID = Integer.toString(CurrentState.getInstance().getCurrentUser().getProfileID());
                SharedPreferences prefs = getSharedPreferences(ID, Context.MODE_PRIVATE);
                Set<String> set = prefs.getStringSet(ID,new HashSet<String>());
                if(set.isEmpty()){
                    startActivity(new Intent(getApplicationContext(), Welcome_Categories.class));
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                }
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

    @Override
    public void onBackPressed(){
        //exit the application
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
