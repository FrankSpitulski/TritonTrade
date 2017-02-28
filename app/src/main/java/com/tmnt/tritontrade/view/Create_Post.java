package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;


public class Create_Post extends AppCompatActivity {

    private Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);



        addItemsOnCategorySpinner();
    }

    public void addItemsOnCategorySpinner(){
        spinner1 = (Spinner) findViewById(R.id.spinner3);
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

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner3);
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

        @Override
        protected void onPostExecute(Void result){
            startActivity(new Intent(getApplicationContext(), Mainfeed.class));
        }
    }
}
