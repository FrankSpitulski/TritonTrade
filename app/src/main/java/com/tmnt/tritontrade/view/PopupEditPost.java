package com.tmnt.tritontrade.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;

public class PopupEditPost extends AppCompatActivity {

    private Post currPost;

    // Variables for bottom buttons
    private Button submitBtn;
    private Button cancelBtn;

    // Variables for the post object
    static String productName = "";
    static String description;
    static float price;
    static ArrayList<String> tags;
    static int profileID;
    static boolean selling;
    static String contactInfo;

    // Variables for text
    private EditText changeTitle;
    private EditText changeDescr;
    private EditText changePrice;
    private User currUser;
    private Spinner spinner;

    // Variables for adding photos
    private ArrayList<ImageView> imgs = new ArrayList<>();
    private ImageView firstImg;
    private ImageView secondImg;
    private ImageView thirdImg;
    private ImageView fourthImg;
    private ImageView fifthImg;
    private ImageView currImage;
    private static int IMG_RESULT = 1;
    static Uri selectedImage;
    static InputStream is;
    private String type;
    ArrayList<String> photos = new ArrayList<>();
    private int counter = 0;

    /**
     * Private Innner class to upload the new profile pic to the server
     */
    private class EditPhotoTask extends AsyncTask<Void, Void, Void> {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_edit_post);

        // Set up some of the interactable items in the view
        submitBtn = (Button)findViewById(R.id.save_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        changeTitle = (EditText) findViewById(R.id.change_title_text);
        changeDescr = (EditText) findViewById(R.id.change_description_label);
        changePrice= (EditText) findViewById(R.id.change_price_label);

        firstImg = (ImageView) findViewById(R.id.first_img);
        secondImg = (ImageView) findViewById(R.id.second_img);
        thirdImg = (ImageView) findViewById(R.id.third_img);
        fourthImg = (ImageView) findViewById(R.id.fourth_img);
        fifthImg = (ImageView) findViewById(R.id.fifth_img);

        addItemsOnCategorySpinner();

        imgs.add(firstImg);
        imgs.add(secondImg);
        imgs.add(thirdImg);
        imgs.add(fourthImg);
        imgs.add(fifthImg);

        // Re populate the fields with what the user already inputted
        currPost = getIntent().getParcelableExtra("Post");
        changeTitle.setText(currPost.getProductName());
        changeDescr.setText(currPost.getDescription());
        changePrice.setText(Float.toString(currPost.getPrice()));




    }

    // Add the items to the category spinner
    public void addItemsOnCategorySpinner(){
        spinner = (Spinner) findViewById(R.id.category_spinner);
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Clothing");
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
        spinner.setAdapter(dataAdapter);
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

                new EditPhotoTask().execute();

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
        }

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

    // Function for when the cancel button is pressed
    public void cancelPressed(View v){
        finish();
    }

    // Function for when the save button is pressed
    public void savePressed(View v){

        Log.d("DEBUG", "Save Pressed");
        try {
            productName = changeTitle.getText().toString();
            if(productName.equals("")){
                throw new Exception("NO_TITLE");
            }
            description = changeDescr.getText().toString();

            price = Float.parseFloat(changePrice.getText().toString());
            // round price to 100ths place
            price *= 100.0f;
            price = Math.round(price) / 100.0f;

        } catch (Exception e) {
            if(e.getMessage().equals("NO_TITLE")){
                Toast.makeText(PopupEditPost.this, "Need a Title", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(PopupEditPost.this, "Invalid Price", Toast.LENGTH_SHORT).show();
            }
        }
        String selectedItemText = spinner.getSelectedItem().toString();

        tags = new ArrayList<String>();
        tags.add(changeTitle.getText().toString().toLowerCase());
        tags.add(selectedItemText.toLowerCase());

        photos = currPost.getPhotos();
        
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
            photos.add(currPost.getDefaultImage());
        }

        new EditPostTask().execute();
    }

    private class EditPostTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                currPost.setPrice(price);
                currPost.setProductName(productName);
                currPost.setDescription(description);
                currPost.setTags(tags);
                currPost.setPhotos(photos);
                Boolean res = Server.modifyExistingPost(currPost);
                return res;
            }
            catch(IOException e) {
                e.printStackTrace();
            } catch (IllegalFormatException e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                //new UpdateUserTask().execute(currUser);
                finish();
                Toast.makeText(PopupEditPost.this, "Post Edited", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Profile.class));
            } else {
                Toast.makeText(PopupEditPost.this, "Edit Post Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
