package com.tmnt.tritontrade.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;

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


        System.out.println(savedPrefs);

        Iterator<String> iterator = savedPrefs.iterator();
        while(iterator.hasNext()){
            String cate = iterator.next();
            switch(cate) {
                case "Textbooks":
                    textbook.setChecked(true);
                    break;
                case "Clothing":
                    clothes.setChecked(true);
                    break;
                case "Furniture":
                    furniture.setChecked(true);
                    break;
                case "Food":
                    foods.setChecked(true);
                    break;
                case "Technology":
                    technology.setChecked(true);
                    break;
                case "Supplies":
                    supplies.setChecked(true);
                    break;
                case "Storage":
                    storage.setChecked(true);
                    break;
                case "Services":
                    services.setChecked(true);
                    break;
                case "Miscellaneous":
                    miscs.setChecked(true);
                    break;
                case "Transportation":
                    trans.setChecked(true);
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
                    savedPrefs.add("Textbooks");
                }
                else
                {
                    //textbook.setChecked(false);
                    savedPrefs.remove("Textbooks");
                }
            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clothes.isChecked())
                {
                    //clothes.setChecked(true);
                    savedPrefs.add("Clothing");
                }
                else
                {
                    //clothes.setChecked(false);
                    savedPrefs.remove("Clothing");
                }
            }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(furniture.isChecked())
                {
                    savedPrefs.add("Furniture");
                }
                else
                {
                    //furniture.setChecked(false);
                    savedPrefs.remove("Furniture");
                }
            }
        });
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foods.isChecked())
                {
                    savedPrefs.add("Food");
                }
                else
                {
                    //foods.setChecked(false);
                    savedPrefs.remove("Food");
                }
            }
        });
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(technology.isChecked())
                {
                    savedPrefs.add("Technology");
                }
                else
                {
                    //technology.setChecked(false);
                    savedPrefs.remove("Technology");
                }
            }
        });
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(supplies.isChecked())
                {
                    savedPrefs.add("Supplies");
                }
                else
                {
                    //supplies.setChecked(false);
                    savedPrefs.remove("Supplies");
                }
            }
        });
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storage.isChecked())
                {
                    //storage.setChecked(true);
                    savedPrefs.add("Storage");
                }
                else
                {
                    //storage.setChecked(false);
                    savedPrefs.remove("Storage");
                }
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(services.isChecked())
                {
                    //services.setChecked(true);
                    savedPrefs.add("Services");
                }
                else
                {
                    //services.setChecked(false);
                    savedPrefs.remove("Services");
                }
            }
        });
        miscs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(miscs.isChecked())
                {
                    //miscs.setChecked(true);
                    savedPrefs.add("Miscellaneous");

                }
                else
                {
                    //miscs.setChecked(false);
                    savedPrefs.remove("Miscellaneous");
                }
            }
        });
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trans.isChecked())
                {
                    //trans.setChecked(true);
                    savedPrefs.add("Transportation");
                }
                else
                {
                    //trans.setChecked(false);
                    savedPrefs.remove("Transportation");
                }
            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.putStringSet(ID,savedPrefs);
                editor.apply();

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



}

