package com.tmnt.tritontrade.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Edit_Categories extends AppCompatActivity{

    private ToggleButton textbook;
    private ToggleButton clothes;
    private ToggleButton furniture;
    private ToggleButton foods;
    private ToggleButton technology;
    private ToggleButton supplies;
    private ToggleButton storage;
    private ToggleButton services;
    private ToggleButton miscs;
    private ToggleButton trans;

    private Button follow;
    //private Button cancel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__categories);

        textbook = (ToggleButton) findViewById(R.id.TEXTBOOK1);
        clothes = (ToggleButton) findViewById(R.id.CLOTHES1);
        furniture = (ToggleButton) findViewById(R.id.FURNITURE1);
        foods = (ToggleButton) findViewById(R.id.FOOD1);
        technology = (ToggleButton) findViewById(R.id.TECHNOLOGY1);
        supplies = (ToggleButton) findViewById(R.id.SUPPLIES1);
        storage = (ToggleButton) findViewById(R.id.STORAGE1);
        services = (ToggleButton) findViewById(R.id.SERVICES1);
        miscs = (ToggleButton) findViewById(R.id.MISCS1);
        trans = (ToggleButton) findViewById(R.id.TRANS1);

        follow = (Button) findViewById(R.id.FOLLOW1);
       // cancel = (Button) findViewById(R.id.CANCEL);

        final String ID = Integer.toString(CurrentState.getInstance().getCurrentUser().getProfileID());
        final SharedPreferences prefs = getSharedPreferences(ID, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        final Set<String> backupset = prefs.getStringSet(ID,new HashSet<String>());
        final Set<String> savedPrefs = prefs.getStringSet(ID,new HashSet<String>());
        savedPrefs.remove("Furnitures");
        savedPrefs.remove("Clothing");
        final boolean[] bools = new boolean[10];
        final String[] fields = {"Textbooks","Clothes","Furniture","Food","Technology","Supplies","Storage","Services","Miscellaneous","Transportation"};


        System.out.println(savedPrefs);

        Iterator<String> iterator = savedPrefs.iterator();
        while(iterator.hasNext()){
            String cate = iterator.next();
            switch(cate) {
                case "Textbooks":
                    textbook.setChecked(true);
                    bools[0] = true;
                    break;
                case "Clothes":
                    clothes.setChecked(true);
                    bools[1] = true;
                    break;
                case "Furniture":
                    furniture.setChecked(true);
                    bools[2] = true;
                    break;
                case "Food":
                    foods.setChecked(true);
                    bools[3] = true;
                    break;
                case "Technology":
                    technology.setChecked(true);
                    bools[4] = true;
                    break;
                case "Supplies":
                    supplies.setChecked(true);
                    bools[5] = true;
                    break;
                case "Storage":
                    storage.setChecked(true);
                    bools[6] = true;
                    break;
                case "Services":
                    services.setChecked(true);
                    bools[7] = true;
                    break;
                case "Miscellaneous":
                    miscs.setChecked(true);
                    bools[8] = true;
                    break;
                case "Transportation":
                    trans.setChecked(true);
                    bools[9] = true;
                    break;
                default:
                    break;
            }
        }
        Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                new HashSet<String>()));

        textbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textbook.isChecked())
                {
                    //textbook.setChecked(true);
                    bools[0] = true;
                }
                else
                {
                    //textbook.setChecked(false);
                    //savedPrefs.remove("Textbooks");
                    bools[0] = false;
                }
            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clothes.isChecked())
                {
                    bools[1] = true;
                    //savedPrefs.add("Clothing");
                }
                else
                {
                    bools[1] = false;
                    //clothes.setChecked(false);
                    //savedPrefs.remove("Clothing");
                }
            }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(furniture.isChecked())
                {
                    bools[2] = true;
                    //savedPrefs.add("Furniture");
                }
                else
                {
                    bools[2] = false;
                    //furniture.setChecked(false);
                    //savedPrefs.remove("Furniture");
                }
            }
        });
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foods.isChecked())
                {
                    bools[3] = true;
                    //savedPrefs.add("Food");
                }
                else
                {
                    bools[3] = false;
                    //foods.setChecked(false);
                    //savedPrefs.remove("Food");
                }
            }
        });
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(technology.isChecked())
                {
                    bools[4] = true;
                    //savedPrefs.add("Technology");
                }
                else
                {
                    bools[4] = false;
                    //technology.setChecked(false);
                    //savedPrefs.remove("Technology");
                }
            }
        });
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(supplies.isChecked())
                {
                    bools[5] = true;
                    //savedPrefs.add("Supplies");
                }
                else
                {
                    bools[5] = false;
                    //supplies.setChecked(false);
                    //savedPrefs.remove("Supplies");
                }
            }
        });
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storage.isChecked())
                {
                    bools[6] = true;
                    //storage.setChecked(true);
                    //savedPrefs.add("Storage");
                }
                else
                {
                    bools[6] = false;
                    //storage.setChecked(false);
                    //savedPrefs.remove("Storage");
                }
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(services.isChecked())
                {
                    bools[7] = true;
                    //services.setChecked(true);
                    //savedPrefs.add("Services");
                }
                else
                {
                    bools[7] = false;
                    //services.setChecked(false);
                    //savedPrefs.remove("Services");
                }
            }
        });
        miscs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(miscs.isChecked())
                {
                    bools[8] = true;
                    //miscs.setChecked(true);
                    //savedPrefs.add("Miscellaneous");

                }
                else
                {
                    bools[8] = false;
                    //miscs.setChecked(false);
                    //savedPrefs.remove("Miscellaneous");
                }
            }
        });
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trans.isChecked())
                {
                    bools[9] = true;
                    //trans.setChecked(true);
                    //savedPrefs.add("Transportation");
                }
                else
                {
                    bools[9] = false;
                    //trans.setChecked(false);
                    //savedPrefs.remove("Transportation");
                }
            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // loop through our boolean array to see which category preferences we will update.
                for(int i=0; i<10; i++){
                    if(bools[i])
                    {
                        savedPrefs.add(fields[i]);
                    }
                    else
                    {
                        savedPrefs.remove(fields[i]);
                    }
                }

                editor.clear();
                editor.putStringSet(ID,savedPrefs);
                editor.apply();
                Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                        new HashSet<String>()));

                if(savedPrefs.isEmpty()){
                    Toast.makeText(Edit_Categories.this, "You did not follow any categories!", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                }
            }
        });


        /*
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.putStringSet(ID,backupset);
                editor.apply();
                Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                        new HashSet<String>()));
                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
            }
        });
*/
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getBaseContext(),Mainfeed.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }



}

