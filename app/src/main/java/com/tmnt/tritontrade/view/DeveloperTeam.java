package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.tmnt.tritontrade.R;

import java.util.ArrayList;

public class DeveloperTeam extends AppCompatActivity {

    String nameBase = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_team);

        //array list that stores references to all the name text views
        ArrayList<TextView> names = new ArrayList<>();
        ArrayList<TextView> positions = new ArrayList<>();
        ArrayList<TextView> bios = new ArrayList<>();


        //add all the name text views to the array list
        for(int i = 1; i <=10; i++) {

            int nameId = getResources().getIdentifier("name" + i, "id", getPackageName());
            int posId = getResources().getIdentifier("position" + i, "id", getPackageName());
            int bioId = getResources().getIdentifier("bio" + i, "id", getPackageName());

            names.add((TextView) findViewById(nameId));
            positions.add((TextView) findViewById(posId));
            bios.add((TextView) findViewById(bioId));
        }

        //iterate through the arraylist to do something to each name
        for(TextView name: names) {
            //name.setTextAppearance(R.style.fontForDevTeamName);
            name.setPadding(20, 0, 0, 0);
        }

        for(TextView pos: positions){
            //pos.setTextAppearance(R.style.fontForDevTeamPos);
            pos.setPadding(20, 0, 0, 0);
        }

        for(TextView bio: bios){
            //bio.setTextAppearance(R.style.fontForDevTeamBio);
            bio.setPadding(20, 0, 0, 0);
        }

        //bottom tool bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_mainfeed:
                                startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                                break;
                            case R.id.bottom_cart:
                                startActivity(new Intent(getApplicationContext(), Cart.class));
                                break;
                            case R.id.bottom_upload:
                                startActivity(new Intent(getApplicationContext(), Create_Post.class));
                                break;
                            case R.id.bottom_profile:
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                                break;
                        }
                        return false;
                    }
                }
        );
    }

}
