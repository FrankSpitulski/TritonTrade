package com.tmnt.tritontrade.view;

import android.app.ProgressDialog;
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
    private Post thePost;


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
                        throw new Exception("NO_TITLE");
                    }
                    description = theDescription.getText().toString();

                    price = Float.parseFloat(thePrice.getText().toString());
                    // round price to 100ths place
                    price *= 100.0f;
                    price = Math.round(price) / 100.0f;

                } catch (Exception e) {
                    Log.d("DEBUG", e.toString());
                    if(e.getMessage().equals("NO_TITLE")){
                        Toast.makeText(Create_Post.this, "Need a Title", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Create_Post.this, "Invalid Price", Toast.LENGTH_SHORT).show();
                    }
                }
                String selectedItemText = spinner1.getSelectedItem().toString();

                tags = new ArrayList<String>();
                tags.add(theName.getText().toString().toLowerCase());
                tags.add(selectedItemText.toLowerCase());
                String[] splitTitle = theName.getText().toString().split(" ");
                for(String s : splitTitle){
                    s = s.toLowerCase();
                    if(s.equals("") || s.equals("in") || s.equals("at") || s.equals("on") || s.equals("the") || s.equals("a") || s.equals("an") || s.equals("by") ||s.equals("of")){
                        // do not add
                    }else{
                        tags.add(s);
                    }
                }

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
                    photos.add(thePost.getDefaultImage());
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

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getBaseContext(),Mainfeed.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
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

    /**
     * Adds the image to the view
     * @param view
     */
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
//    private class UpdateUserTask extends AsyncTask<User, Void, Void> {
//        protected Void doInBackground(User... params) {
//            try {
//                Server.modifyExistingUser(params[0]);
//
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//        }
//}
/*
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

        private ProgressDialog dialog=new ProgressDialog(Create_Post.this);

        @Override
        protected void onPreExecute(){
            this.dialog.setMessage("Creating post...");
            this.dialog.show();
        }

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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result != null) {
                //new UpdateUserTask().execute(currUser);
                currUser.addToPostHistory(result.getPostID());
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
