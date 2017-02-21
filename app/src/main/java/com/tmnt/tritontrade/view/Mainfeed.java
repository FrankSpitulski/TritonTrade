package com.tmnt.tritontrade.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Post;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Mainfeed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfeed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*ArrayList<Post> posts = new ArrayList<>();
        ArrayList<String> photos = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("fine");
        Date s = new Date();
        photos.add("https://storage.googleapis.com/gweb-uniblog-publish-prod/static/blog/images/google-200x200.7714256da16f.png");
        Post post = new Post("Stuff", photos, "Description stuff",
        0, tags, 1, 1, true, true , s , "Phone number", false);
        list = (ListView) this.findViewById(R.id.listFeed);
        //System.out.println("REACH");
        list.setAdapter(new CustomAdapter(this, posts));*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainfeed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.clothing_sidebar){

        } else if (id == R.id.food_sidebar) {

        } else if (id == R.id.services_sidebar) {

        } else if (id == R.id.storage_sidebar) {

        } else if (id == R.id.supplies_sidebar) {

        } else if (id == R.id.technology_sidebar) {

        } else if (id == R.id.textbooks_sidebar) {

        } else if (id == R.id.transportation_sidebar) {

        } else if (id == R.id.misc_sidebar){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
