package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

public class EditProfile extends AppCompatActivity {

    private User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        currUser = getIntent().getParcelableExtra("user");

        populateFields(currUser);

    }


    private void populateFields(User user){

        //Get references to all the relevant fields in the profle
        EditText username = (EditText) findViewById(R.id.username);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText bio = (EditText) findViewById(R.id.bio);

        //set the Edit text fields

        username.setText(user.getName());
        phone.setText(user.getMobileNumber().substring(6));
        bio.setText(user.getBio());


        //set profile photo

        new DownloadPhotosAsyncTask((ImageView) findViewById(R.id.profile_image))
                .execute(user.getPhoto());


    }

    public void cancelEdit(View view){
        finish();
    }

    public void saveInfo(View view) {
        //Get references to all the relevant fields in the profle
        EditText username = (EditText) findViewById(R.id.username);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText bio = (EditText) findViewById(R.id.bio);

        if(!nameIsValid(username.getText().toString())){
            Toast.makeText(EditProfile.this, "Username is invalid", Toast.LENGTH_SHORT).show();
            return;
        } else if (!phoneIsValid(phone.getText().toString())) {
            Toast.makeText(EditProfile.this, "Phone number must be in format\n(000) 000-0000", Toast.LENGTH_SHORT).show();
            return;
        } else if (!bioIsValid(bio.getText().toString())){
            Toast.makeText(EditProfile.this, "Please enter a short description about yourself", Toast.LENGTH_SHORT).show();
            return;
        } else {

            currUser.setName(username.getText().toString());
            currUser.setMobileNumber("+0001 " + phone.getText().toString());
            currUser.setBio(bio.getText().toString());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedUser", currUser);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }


    private boolean nameIsValid(String username){
        if(username.isEmpty()){
            return false;
        }

        return true;
    }

    private boolean phoneIsValid(String phoneNumber){

        //Format must be: +0000 (000) 000-0000
        if(phoneNumber.length() != 14) {
            return false;
        }

        char[] numbers = phoneNumber.toCharArray();

        for(int i = 0; i < numbers.length; i++){
            if(i == 0){
                if(numbers[i] != '(') {return false;}
            } else if (i == 4){
                if(numbers[i] != ')') {return false;}
            } else if (i == 5) {
                if (numbers[i] != ' ') { return false;}
            } else if (i == 9) {
                if (numbers[i] != '-') { return false;}
            } else {
                if(!Character.isDigit(numbers[i])) {
                    return false;
                }
            }
        }


        return true;
    }

    private boolean bioIsValid(String bio){

        if(bio.isEmpty()){
            return false;
        }


        return true;
    }

}
