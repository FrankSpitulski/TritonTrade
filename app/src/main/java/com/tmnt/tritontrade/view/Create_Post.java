package com.tmnt.tritontrade.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

//import static com.tmnt.tritontrade.R.id.imgButton4;
//import static com.tmnt.tritontrade.R.id.imgButton5;


public class Create_Post extends AppCompatActivity {

    static String productName = "";
    static String description;
    static float price;
    static ArrayList<String> tags;
    static int profileID;
    static boolean selling;
    static String contactInfo;
    static String thePath;
    static InputStream is;
    static String extension;
    private static int IMG_RESULT = 1;
    Button createPostButton;
    private Spinner spinner1;
    private Spinner spinner2;
    private User currUser;


    ImageView firstImg;
    ImageView secondImg;
    ImageView thirdImg;
    ImageView fourthImg;
    ImageView fifthImg;
    ImageView currImage;

    ArrayList<ImageView> imgs = new ArrayList<>();
    ArrayList<String> photos = new ArrayList<>();

    private int counter = 0;

    static Uri selectedImage;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);
        setTitle("Create a Post");


        addItemsOnCategorySpinner();
        addItemsOnBuyOrSellSpinner();
        //auto keyboard pop up stopped
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firstImg = (ImageView) findViewById(R.id.firstImg);
        secondImg = (ImageView) findViewById(R.id.secondImg);
        thirdImg = (ImageView) findViewById(R.id.thirdImg);
        fourthImg = (ImageView) findViewById(R.id.fourthImg);
        fifthImg = (ImageView) findViewById(R.id.fifthImg);

        imgs.add(firstImg);
        imgs.add(secondImg);
        imgs.add(thirdImg);
        imgs.add(fourthImg);
        imgs.add(fifthImg);

        //bottom tool bar
//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener(){
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        if(item.getItemId() == bottom_mainfeed){
//                            Intent in=new Intent(getBaseContext(),Mainfeed.class);
//                            startActivity(in);
//                            return true;
//                        }
//                        else if (item.getItemId() == bottom_cart){
//                            Intent in=new Intent(getBaseContext(),Cart.class);
//                            startActivity(in);
//                            return true;
//
//                        }
//                        else if(item.getItemId() == bottom_upload){
//                            Intent in=new Intent(getBaseContext(),Create_Post.class);
//                            startActivity(in);
//                            return true;
//                        }
//                        else if(item.getItemId() == bottom_profile){
//                            Intent in=new Intent(getBaseContext(),Profile.class);
//                            startActivity(in);
//                            return true;
//                        }
//                        return false;
//                    }
//                }
//        );
        currUser = CurrentState.getInstance().getCurrentUser();
        createPostButton = (Button) findViewById(R.id.createButton);

        createPostButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText theName = (EditText) findViewById(R.id.itemTitleLabel);
                EditText theDescription = (EditText) findViewById(R.id.inputDescriptionLabel);
                EditText thePrice = (EditText) findViewById(R.id.priceInputLabel);
                spinner1 = (Spinner) findViewById(R.id.categorySpinner);
                spinner2 = (Spinner) findViewById(R.id.buyOrSellSpinner);

                try {
                    productName = theName.getText().toString();
                    if(productName.equals("")){
                        throw new IllegalArgumentException("NO_TITLE");
                    }
                    description = theDescription.getText().toString();

                    price = Float.parseFloat(thePrice.getText().toString());

                } catch (IllegalArgumentException e) {
                    if(e.toString().equals("NO_TITLE")){
                        Toast.makeText(Create_Post.this, "Need a Title", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Create_Post.this, "Invalid Price", Toast.LENGTH_SHORT).show();
                    }
                }
                String selectedItemText = spinner1.getSelectedItem().toString();

                tags = new ArrayList<String>();
                tags.add(theName.getText().toString());
                tags.add(selectedItemText);

                profileID = CurrentState.getInstance().getCurrentUser().getProfileID();
                contactInfo = CurrentState.getInstance().getCurrentUser().getMobileNumber();


                if(spinner2.getSelectedItem().toString().equals("Buying")){
                    selling = false;
                }else{
                    selling = true;
                }

                //photos = new ArrayList<String>();
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
                if(photos.size()==0){
                    photos.add(currUser.getDefaultImage());
                }

                new CreatePostTask().execute();
                //new UpdateUserTask().execute(currUser);
                //new getCurrentStateTask().execute();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

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

                // Set the Image in ImageView after decoding the String
                currImage.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                ContentResolver cR = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                type = mime.getExtensionFromMimeType(cR.getType(selectedImage));

                new Create_Post.UploadPhotoTask().execute();
                //new UpdateUserTask().execute(currUser);
                //new getCurrentStateTask().execute();


            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Category selection spinner for the post item
     */
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

    /**
     * User selection spinner for buying or selling product
     */
    public void addItemsOnBuyOrSellSpinner(){
        spinner2 = (Spinner) findViewById(R.id.buyOrSellSpinner);
        List<String> buyOrSellList = new ArrayList<>();
        buyOrSellList.add("Selling");
        buyOrSellList.add("Buying");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, buyOrSellList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }


    /*private class GetPathTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {

                String uploaded = Server.uploadImage(is, extension);
                photos.add(uploaded);//THIS IS THE ARRAYLIST THAT IS POPULATING THE POST
                return uploaded;
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


    public void addImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        currImage = imgs.get(counter);
        counter = (counter + 1) % 5;

        startActivityForResult(intent,IMG_RESULT);
    }




    /**
     * Private Innner class to upload the new profile pic to the server
     */
    private class UploadPhotoTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            try {
                String s = Server.uploadImage(is, type);
                photos.add(s);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Private Innner class to update the server with the new user info
     */
    private class UpdateUserTask extends AsyncTask<User, Void, Void> {
        protected Void doInBackground(User... params) {
            try {
                Server.modifyExistingUser(params[0]);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }/*
    private class getCurrentStateTask extends AsyncTask<User,User,User>{
        protected User doInBackground(User...params){
            try{
                return CurrentState.getInstance().getCurrentUser();
            }catch(Exception e){
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(User theUser) {
            if(theUser!=null){
                finish();
                Toast.makeText(Create_Post.this, "Post Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
            }
        }
    }*/
    /**
     * Create Post Inner class
     */
    private class CreatePostTask extends AsyncTask<Post, Post, Post> {
        @Override
        protected Post doInBackground(Post... params) {
            try {
                for(int i = 0; i< photos.size(); i++){
                    Log.d("DEBUG", photos.get(i));
                }
                return Server.addPost(productName,photos,description,price,
                        tags,profileID,selling,contactInfo);
            }
            catch(IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            } catch (IllegalFormatException e){
                Log.d("DEBUG", e.toString());
                return null;
            }
        }
        @Override
        protected void onPostExecute(Post result) {
            if (result != null) {
                new UpdateUserTask().execute(currUser);
                CurrentState.getInstance().setCurrentUser(currUser);
                finish();
                Toast.makeText(Create_Post.this, "Post Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
            } else {
                Toast.makeText(Create_Post.this, "Create Post Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
