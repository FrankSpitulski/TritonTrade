package com.tmnt.tritontrade.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tmnt.tritontrade.R;

import java.util.ArrayList;

public class Profile_NonUser extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__non_user);



        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();

        String[] headers = new String[10];
        String[] data = new String[10];

        for(int i = 0; i < headers.length; i++){

            headers[i] = "Product Title " + i ;
            data[i] = "Description of product. This is going to probably be a multiple line " +
                    "description. What were to happen if I added more lines to this shit? " +
                    "Description # " + i;

        }

        list.setAdapter(new ProfileListAdaptor(this, headers, data));
    }
}
