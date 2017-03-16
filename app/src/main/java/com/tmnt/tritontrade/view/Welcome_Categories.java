package com.tmnt.tritontrade.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;

import java.util.HashSet;
import java.util.Set;

import static com.tmnt.tritontrade.view.MainActivity.CAT_5;
import static com.tmnt.tritontrade.view.MainActivity.CAT_6;

public class Welcome_Categories extends AppCompatActivity{
    private ToggleButton textbook;
    private ToggleButton clothes;
    private ToggleButton furnitures;
    private ToggleButton foods;
    private ToggleButton technology;
    private ToggleButton supplies;
    private ToggleButton storage;
    private ToggleButton services;
    private ToggleButton miscs;
    private ToggleButton trans;

    private Button follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__categories);

        textbook = (ToggleButton) findViewById(R.id.TEXTBOOK1);
        clothes = (ToggleButton) findViewById(R.id.CLOTHES1);
        furnitures = (ToggleButton) findViewById(R.id.FURNITURE1);
        foods = (ToggleButton) findViewById(R.id.FOOD1);

        technology = (ToggleButton) findViewById(R.id.TECHNOLOGY1);
        supplies = (ToggleButton) findViewById(R.id.SUPPLIES1);

        storage = (ToggleButton) findViewById(R.id.STORAGE1);
        services = (ToggleButton) findViewById(R.id.SERVICES1);
        miscs = (ToggleButton) findViewById(R.id.MISCS1);
        trans = (ToggleButton) findViewById(R.id.TRANS1);

        follow = (Button) findViewById(R.id.FOLLOW1);

        if(!CurrentState.getInstance().isLoggedIn()){
            CurrentState.getInstance().killLogin(this);
            return;
        }

        final String ID = Integer.toString(CurrentState.getInstance().getCurrentUser().getProfileID());
        SharedPreferences prefs = getSharedPreferences(ID, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = prefs.edit();
        final Set<String> set = prefs.getStringSet(ID,new HashSet<String>());

        textbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textbook.isChecked())
                {
                    textbook.setChecked(true);
                    set.add("Textbooks");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
                else
                {
                    textbook.setChecked(false);
                    set.remove("Textbooks");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clothes.isChecked())
                {
                    clothes.setChecked(true);
                    set.add("Clothes");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
                else
                {
                    clothes.setChecked(false);
                    set.remove("Clothes");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
            }
        });
        furnitures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(furnitures.isChecked())
                {
                    furnitures.setChecked(true);
                    set.add("Furniture");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();

                }
                else
                {
                    furnitures.setChecked(false);
                    set.remove("Furniture");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
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
                }
                else
                {
                    foods.setChecked(false);
                    set.remove("Food");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();;
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
                }
                else
                {
                    technology.setChecked(false);
                    set.remove("Technology");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
            }
        });
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(supplies.isChecked())
                {
                    supplies.setChecked(true);
                    set.add(CAT_6);
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();

                }
                else
                {
                    supplies.setChecked(false);
                    set.remove(CAT_6);
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
            }
        });
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storage.isChecked())
                {
                    storage.setChecked(true);
                    set.add(CAT_5);
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();

                }
                else
                {
                    storage.setChecked(false);
                    set.remove(CAT_5);
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
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
                }
                else
                {
                    services.setChecked(false);
                    set.remove("Services");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
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
                }
                else
                {
                    miscs.setChecked(false);
                    set.remove("Miscellaneous");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
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

                }
                else
                {
                    trans.setChecked(false);
                    set.remove("Transportation");
                    editor.clear();
                    editor.putStringSet(ID,set);
                    editor.apply();
                }
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set.isEmpty()){
                    Toast.makeText(Welcome_Categories.this, "You need to pick at least one category!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("DEBUG", "2.set = "+ set);
                    startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                }
            }
        });

        Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                new HashSet<String>()));

    }


}
