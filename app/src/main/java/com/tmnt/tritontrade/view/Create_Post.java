package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;

import static com.tmnt.tritontrade.R.id.categorySpinner;
import static com.tmnt.tritontrade.R.id.imgButton1;


public class Create_Post extends AppCompatActivity {

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


                productName = theName.getText().toString();
                description = theDescription.getText().toString();
                price = Float.parseFloat(thePrice.getText().toString());


                String selectedItemText = spinner1.getSelectedItem().toString();
                tags.add(0, selectedItemText);

                profileID = CurrentState.getInstance().getCurrentUser().getProfileID();
                contactInfo = CurrentState.getInstance().getCurrentUser().getMobileNumber();


                if(spinner2.getSelectedItem().toString().equals("Buying")){
                    selling = false;
                }else{
                    selling = true;
                }

                new CreatePostTask().execute();
            }
        });
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
                return Server.addPost(productName,photos,description,price,tags,profileID,selling,contactInfo);
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
}
