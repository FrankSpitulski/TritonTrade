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
    private Button cancel;




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
        //editor.clear();
        //editor.apply();
        final Set<String> backupset = prefs.getStringSet(ID,new HashSet<String>());

        //editor.clear();
        //editor.apply();
        Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                new HashSet<String>()));

        final Set<String> set = prefs.getStringSet(ID,new HashSet<String>());

        Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                new HashSet<String>()));

        Iterator<String> iterator = set.iterator();
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
                    set.add("Textbooks");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "TEXTBOOK Selected", Toast.LENGTH_SHORT).show();
                    Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                            new HashSet<String>()));
                }
                else
                {
                    textbook.setChecked(false);
                    set.remove("Textbooks");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "TEXTBOOK Deselected", Toast.LENGTH_SHORT).show();
                    Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                            new HashSet<String>()));
                }
            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clothes.isChecked())
                {
                    clothes.setChecked(true);
                    set.add("Clothing");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "CLOTHING Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    clothes.setChecked(false);
                    set.remove("Clothing");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "CLOTHING Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(furniture.isChecked())
                {
                    furniture.setChecked(true);
                    set.add("Furniture");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "FURNITURES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    furniture.setChecked(false);
                    set.remove("Furniture");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "FURNITURES Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foods.isChecked())
                {
                    foods.setChecked(true);
                    set.add("Food");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "FOOD Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    foods.setChecked(false);
                    set.remove("Food");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "FOOD Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(technology.isChecked())
                {
                    technology.setChecked(true);
                    set.add("Technology");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "TECHNOLOGY Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    technology.setChecked(false);
                    set.remove("Technology");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "TECHNOLOGY Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(supplies.isChecked())
                {
                    supplies.setChecked(true);
                    set.add("Supplies");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "SUPPLIES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    supplies.setChecked(false);
                    set.remove("Supplies");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "SUPPLIES Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storage.isChecked())
                {
                    storage.setChecked(true);
                    set.add("Storage");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "STORAGE Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    storage.setChecked(false);
                    set.remove("Storage");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "STORAGE Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(services.isChecked())
                {
                    services.setChecked(true);
                    set.add("Services");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "SERVICES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    services.setChecked(false);
                    set.remove("Services");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "SERVICES Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        miscs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(miscs.isChecked())
                {
                    miscs.setChecked(true);
                    set.add("Miscellaneous");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "MISCS Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    miscs.setChecked(false);
                    set.remove("Miscellaneous");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "MISCS Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trans.isChecked())
                {
                    trans.setChecked(true);
                    set.add("Transportation");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "TRANS Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    trans.setChecked(false);
                    set.remove("Transportation");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Edit_Categories.this, "TRANS Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                        new HashSet<String>()));
                if(set.isEmpty()){
                    Toast.makeText(Edit_Categories.this, "You did not follow any category", Toast.LENGTH_SHORT).show();
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

