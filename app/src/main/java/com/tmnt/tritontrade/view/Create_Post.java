package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;

import static com.tmnt.tritontrade.R.id.categorySpinner;
import static com.tmnt.tritontrade.R.id.imgButton1;
import static com.tmnt.tritontrade.R.id.imgButton4;
import static com.tmnt.tritontrade.R.id.imgButton5;


public class Create_Post extends AppCompatActivity {

    private static int IMG_RESULT = 1;
    Button createPostButton;
    private Spinner spinner1;
    private Spinner spinner2;
    static String productName = "";
    static ArrayList<String> photos;
    static String description;
    static float price;
    static ArrayList<String> tags;
    static int profileID;
    static boolean selling;
    static String contactInfo;
    static String thePath;

    static InputStream is;
    static String extension;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);

        addItemsOnCategorySpinner();
        addItemsOnBuyOrSellSpinner();

        createPostButton = (Button) findViewById(R.id.createButton);

        createPostButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText theName = (EditText) findViewById(R.id.itemTitleLabel);
                EditText theDescription = (EditText) findViewById(R.id.inputDescriptionLabel);
                EditText thePrice = (EditText) findViewById(R.id.priceInputLabel);
                spinner1 = (Spinner) findViewById(R.id.categorySpinner);
                spinner2 = (Spinner) findViewById(R.id.buyOrSellSpinner);
                ImageButton firstImg = (ImageButton) findViewById(R.id.imgButton1);
                ImageButton secondImg = (ImageButton) findViewById(R.id.imgButton2);
                ImageButton thirdImg = (ImageButton) findViewById(R.id.imgButton3);
                ImageButton fourthImg = (ImageButton) findViewById(imgButton4);
                ImageButton fifthImg = (ImageButton) findViewById(imgButton5);


                productName = theName.getText().toString();
                description = theDescription.getText().toString();
                price = Float.parseFloat(thePrice.getText().toString());


                String selectedItemText = spinner1.getSelectedItem().toString();
                tags.add(0, productName);
                tags.add(1, selectedItemText);

                profileID = CurrentState.getInstance().getCurrentUser().getProfileID();
                contactInfo = CurrentState.getInstance().getCurrentUser().getMobileNumber();


                if(spinner2.getSelectedItem().toString().equals("Buying")){
                    selling = false;
                }else{
                    selling = true;
                }

                firstImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent,IMG_RESULT);

                    }
                });
                secondImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent,IMG_RESULT);

                    }
                });
                thirdImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent,IMG_RESULT);

                    }
                });
                fourthImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent,IMG_RESULT);

                    }
                });
                fifthImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent,IMG_RESULT);

                    }
                });

                new CreatePostTask().execute();

                //bottom tool bar
                BottomNavigationView bottomNavigationView = (BottomNavigationView)
                        findViewById(R.id.bottom_navigation);

                bottomNavigationView.setOnNavigationItemSelectedListener(
                        new BottomNavigationView.OnNavigationItemSelectedListener(){
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.bottom_mainfeed:
                                        startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                                        break;
                                    case R.id.bottom_cart:
                                        startActivity(new Intent(getApplicationContext(), Cart.class));
                                        break;
                                    case R.id.bottom_upload:
                                        startActivity(new Intent(getApplicationContext(), Create_Post.class));
                                        break;
                                    case R.id.bottom_profile:
                                        startActivity(new Intent(getApplicationContext(), Profile.class));
                                        break;
                                }
                                return false;
                            }
                        }
                );
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();

                //get the inputstream to the URI file
                is = getContentResolver().openInputStream(URI);

                //Get the file name
                String[] FILE = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String temp = cursor.getString(columnIndex);

                //Get the extension
                int i = temp.lastIndexOf('.');
                if (i > 0) {
                    extension = temp.substring(i+1);
                }
                //upload the image to the server
                thePath = Server.uploadImage(is,extension);
                photos.add(thePath); //THIS IS THE ARRAYLIST THAT IS POPULATING THE POST
                cursor.close();


            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
        }

    }


    public void addItemsOnCategorySpinner(){
        spinner1 = (Spinner) findViewById(R.id.categorySpinner);
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Clothes");
        categoryList.add("Food");
        categoryList.add("Furniture");
        categoryList.add("Storage");
        categoryList.add("Supplies");
        categoryList.add("Technology");
        categoryList.add("Textbooks");
        categoryList.add("Transportation");
        categoryList.add("Miscellaneous");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }
    public void addItemsOnBuyOrSellSpinner(){
        spinner2 = (Spinner) findViewById(R.id.buyOrSellSpinner);
        List<String> buyOrSellList = new ArrayList<>();
        buyOrSellList.add("Buying");
        buyOrSellList.add("Selling");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, buyOrSellList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }



    private class CreatePostTask extends AsyncTask<Post, Post, Post> {
        @Override
        protected Post doInBackground(Post... params) {
            try {
                return Server.addPost(productName,photos,description,price,
                        tags,profileID,selling,contactInfo);
            }
            catch(IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Post result) {
            if (result != null) {
                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
            } else {

                Toast.makeText(Create_Post.this, "Create Post Failed", Toast.LENGTH_SHORT).show();
            }


        }
    }

    //ASYNC TASK FOR THE UPLOAD TO SERVER
    /*private class GetPathTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return Server.uploadImage(is, extension);
            }
            catch(IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                startActivity(new Intent(getApplicationContext(), Create_Post.class));
            } else {

                Toast.makeText(Create_Post.this, "Post Upload Failed", Toast.LENGTH_SHORT).show();
            }


        }
    }*/

}
