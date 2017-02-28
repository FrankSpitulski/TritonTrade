package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        phone.setText(user.getMobileNumber());
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


        if(username.getText().toString().matches("")) {
            //error
        } else {
            currUser.setName(username.getText().toString());
        }


        if(phone.getText().toString().matches("")) {
            //error
        } else {
            currUser.setMobileNumber(phone.getText().toString());
        }

        currUser.setBio(bio.getText().toString());


        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedUser", currUser);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
