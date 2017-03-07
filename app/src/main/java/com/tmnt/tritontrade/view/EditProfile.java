package com.tmnt.tritontrade.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.*;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.InputStream;

public class EditProfile extends AppCompatActivity {

    private User currUser;
    private ImageView profileImg;
    private Button changeProfilePic;
    private int IMG_RESULT = 2;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS= 1;
    static InputStream is;
    static Uri selectedImage;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        currUser = getIntent().getParcelableExtra("user");

        populateFields(currUser);
        profileImg = (ImageView) findViewById(R.id.profile_image);

        changeProfilePic = (Button) findViewById(R.id.change_profile_button);

    }


    /**
     * Private Innner class to upload the new profile pic to the server
     */
    private class UploadPhotoTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            try {
                String s = Server.uploadImage(is, type);
                currUser.setPhoto(s);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        /*
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute(Void result) {
        }
        */
    }


    /**
     * Change the profile photo that is being displayed
     * @param view
     */
    public void changePhoto(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(EditProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, IMG_RESULT);
        } else {
            ActivityCompat.requestPermissions(EditProfile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                is = getContentResolver().openInputStream(selectedImage);

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                String imgDecodableString = cursor.getString(columnIndex);

                cursor.close();

                ImageView imgView = (ImageView) findViewById(R.id.profile_image);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                ContentResolver cR = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                type = mime.getExtensionFromMimeType(cR.getType(selectedImage));

                new UploadPhotoTask().execute();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }

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
