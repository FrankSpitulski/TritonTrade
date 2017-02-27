package com.tmnt.tritontrade.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Post;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        list = (ListView) findViewById(R.id.list);

        Post[] posts = new Post[10];

        for(int i = 0; i < posts.length; i++){


            Post p = new Post("Product Title " + i , null, "Description of product. This is going to probably be a multiple line " +
                    "description. What were to happen if I added more lines to this shit? " +
                    "Description # " + i, 69, null, 0, 0, false, false, null, "hi", false);


            posts[i] = p;


        }

        list.setAdapter(new ProfileListAdaptor(this, posts));

    }
}