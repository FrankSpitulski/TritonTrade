package com.tmnt.tritontrade.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;

import java.util.HashSet;
import java.util.Set;

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

        textbook = (ToggleButton) findViewById(R.id.TEXTBOOK);
        clothes = (ToggleButton) findViewById(R.id.CLOTHES);
        furnitures = (ToggleButton) findViewById(R.id.FURNITURE);
        foods = (ToggleButton) findViewById(R.id.FOOD);
        technology = (ToggleButton) findViewById(R.id.TECHNOLOGY);
        supplies = (ToggleButton) findViewById(R.id.SUPPLIES);
        storage = (ToggleButton) findViewById(R.id.STORAGE);
        services = (ToggleButton) findViewById(R.id.SERVICES);
        miscs = (ToggleButton) findViewById(R.id.MISCS);
        trans = (ToggleButton) findViewById(R.id.TRANS);

        follow = (Button) findViewById(R.id.FOLLOW);

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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "TEXTBOOK Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    textbook.setChecked(false);
                    set.remove("Textbooks");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "TEXTBOOK Deselected", Toast.LENGTH_SHORT).show();

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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "CLOTHES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    clothes.setChecked(false);
                    set.remove("Clothing");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "CLOTHES Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        furnitures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(furnitures.isChecked())
                {
                    furnitures.setChecked(true);
                    set.add("furnitures");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "FURNITURES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    furnitures.setChecked(false);
                    set.remove("furnitures");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "FURNITURES Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "FOODS Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    foods.setChecked(false);
                    set.remove("Food");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "FOODS Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "TECHNOLOGY Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    technology.setChecked(false);
                    set.remove("Technology");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "TECHNOLOGY Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "SUPPLIES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    supplies.setChecked(false);
                    set.remove("Supplies");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "SUPPLIES Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "STORAGE Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    storage.setChecked(false);
                    set.remove("Storage");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "STORAGE Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "SERVICES Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    services.setChecked(false);
                    set.remove("Services");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "SERVICES Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "MISCS Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    miscs.setChecked(false);
                    set.remove("Miscellaneous");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "MISCS Deselected", Toast.LENGTH_SHORT).show();
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
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "TRANS Selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    trans.setChecked(false);
                    set.remove("Transportation");
                    editor.putStringSet(ID,set);
                    editor.apply();
                    Toast.makeText(Welcome_Categories.this, "TRANS Deselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set.isEmpty()){
                    Toast.makeText(Welcome_Categories.this, "You did not follow any category", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                }
            }
        });

        Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                new HashSet<String>()));

    }


}
