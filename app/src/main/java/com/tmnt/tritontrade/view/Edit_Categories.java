package com.tmnt.tritontrade.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.tmnt.tritontrade.view.MainActivity.CAT_1;
import static com.tmnt.tritontrade.view.MainActivity.CAT_10;
import static com.tmnt.tritontrade.view.MainActivity.CAT_2;
import static com.tmnt.tritontrade.view.MainActivity.CAT_3;
import static com.tmnt.tritontrade.view.MainActivity.CAT_4;
import static com.tmnt.tritontrade.view.MainActivity.CAT_5;
import static com.tmnt.tritontrade.view.MainActivity.CAT_6;
import static com.tmnt.tritontrade.view.MainActivity.CAT_7;
import static com.tmnt.tritontrade.view.MainActivity.CAT_8;
import static com.tmnt.tritontrade.view.MainActivity.CAT_9;

public class Edit_Categories extends AppCompatActivity{

    private ToggleButton cat1;
    private ToggleButton cat2;
    private ToggleButton cat3;
    private ToggleButton cat4;
    private ToggleButton cat5;
    private ToggleButton cat6;
    private ToggleButton cat7;
    private ToggleButton cat8;
    private ToggleButton cat9;
    private ToggleButton cat10;

    private Button follow;
    //private Button cancel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__categories);

        cat1 = (ToggleButton) findViewById(R.id.TEXTBOOK1);
            cat1.setText(CAT_8);
        cat2 = (ToggleButton) findViewById(R.id.CLOTHES1);
        cat3 = (ToggleButton) findViewById(R.id.FURNITURE1);
        cat4 = (ToggleButton) findViewById(R.id.FOOD1);
        cat5 = (ToggleButton) findViewById(R.id.TECHNOLOGY1);
        cat6 = (ToggleButton) findViewById(R.id.SUPPLIES1);
        cat7 = (ToggleButton) findViewById(R.id.STORAGE1);
        cat8 = (ToggleButton) findViewById(R.id.SERVICES1);
        cat9 = (ToggleButton) findViewById(R.id.MISCS1);
        cat10 = (ToggleButton) findViewById(R.id.TRANS1);

        follow = (Button) findViewById(R.id.FOLLOW1);
       // cancel = (Button) findViewById(R.id.CANCEL);

        if(!CurrentState.getInstance().isLoggedIn()){
            CurrentState.getInstance().killLogin(this);
        }

        final String ID = Integer.toString(CurrentState.getInstance().getCurrentUser().getProfileID());
        final SharedPreferences prefs = getSharedPreferences(ID, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        final Set<String> backupset = prefs.getStringSet(ID,new HashSet<String>());
        final Set<String> savedPrefs = prefs.getStringSet(ID,new HashSet<String>());
        savedPrefs.remove("Supplies");
        savedPrefs.remove("Storage");

        final boolean[] bools = new boolean[10];
        final String[] fields = {CAT_8,CAT_1,CAT_3,CAT_2,CAT_7,CAT_6,CAT_5,CAT_4,CAT_10,CAT_9};


        System.out.println(savedPrefs);

        Iterator<String> iterator = savedPrefs.iterator();
        while(iterator.hasNext()){
            String cate = iterator.next();
            switch(cate) {
                case CAT_8:
                    cat1.setChecked(true);
                    bools[0] = true;
                    break;
                case CAT_1:
                    cat2.setChecked(true);
                    bools[1] = true;
                    break;
                case CAT_3:
                    cat3.setChecked(true);
                    bools[2] = true;
                    break;
                case CAT_2:
                    cat4.setChecked(true);
                    bools[3] = true;
                    break;
                case CAT_7:
                    cat5.setChecked(true);
                    bools[4] = true;
                    break;
                case CAT_6:
                    cat6.setChecked(true);
                    bools[5] = true;
                    break;
                case CAT_5:
                    cat7.setChecked(true);
                    bools[6] = true;
                    break;
                case CAT_4:
                    cat8.setChecked(true);
                    bools[7] = true;
                    break;
                case CAT_10:
                    cat9.setChecked(true);
                    bools[8] = true;
                    break;
                case CAT_9:
                    cat10.setChecked(true);
                    bools[9] = true;
                    break;
                default:
                    break;
            }
        }
        Log.i("DEBUG", "2.set = "+prefs.getStringSet("set",
                new HashSet<String>()));

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cat1.isChecked())
                {
                    //cat1.setChecked(true);
                    bools[0] = true;
                    cat1.setAnimation(new Animation() {
                    });
                }
                else
                {
                    //cat1.setChecked(false);
                    //savedPrefs.remove("Textbooks");
                    bools[0] = false;
                }
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedPrefs.add("Clothing");
//cat2.setChecked(false);
//savedPrefs.remove("Clothing");
                bools[1] = cat2.isChecked();
            }
        });
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedPrefs.add("Furniture");
//furniture.setChecked(false);
//savedPrefs.remove("Furniture");
                bools[2] = cat3.isChecked();
            }
        });
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedPrefs.add("Food");
//foods.setChecked(false);
//savedPrefs.remove("Food");
                bools[3] = cat4.isChecked();
            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedPrefs.add("Technology");
//technology.setChecked(false);
//savedPrefs.remove("Technology");
                bools[4] = cat5.isChecked();
            }
        });
        cat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedPrefs.add("Supplies");
//supplies.setChecked(false);
//savedPrefs.remove("Supplies");
                bools[5] = cat6.isChecked();
            }
        });
        cat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //storage.setChecked(true);
//savedPrefs.add("Storage");
//storage.setChecked(false);
//savedPrefs.remove("Storage");
                bools[6] = cat7.isChecked();
            }
        });
        cat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cat8.setChecked(true);
//savedPrefs.add("Services");
//cat8.setChecked(false);
//savedPrefs.remove("Services");
                bools[7] = cat8.isChecked();
            }
        });
        cat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cat9.setChecked(true);
//savedPrefs.add("Miscellaneous");
//cat9.setChecked(false);
//savedPrefs.remove("Miscellaneous");
                bools[8] = cat9.isChecked();
            }
        });
        cat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trans.setChecked(true);
//savedPrefs.add("Transportation");
//trans.setChecked(false);
//savedPrefs.remove("Transportation");
                bools[9] = cat10.isChecked();
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
                    Intent in=new Intent(getBaseContext(),Mainfeed.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
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

