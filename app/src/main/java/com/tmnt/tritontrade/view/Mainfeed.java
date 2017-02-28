package com.tmnt.tritontrade.view;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Edward Ji
 */

public class Mainfeed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView list;
    CustomAdapter adapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //bottom tool bar
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfeed);

        list = (ListView) this.findViewById(R.id.listFeed);

        //bottom tool bar
/*        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottom_nav_items);

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                setOnNavigationItemSelectedListener(menuItemId,true);
                showToast(menuItemId, false);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                showToast(menuItemId, true);
            }
        });
*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DRAWERS
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.left_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationView navigationView2 = (NavigationView) findViewById(R.id.right_drawer);

        //SET UP FILTER

        ArrayList<String> tags = new ArrayList<>();
        tags.add("food");


        MyTask task = new MyTask();
        task.execute(tags);
        SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

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

        if (id == R.id.clothing_sidebar) {

        } else if (id == R.id.food_sidebar) {

        } else if (id == R.id.services_sidebar) {

        } else if (id == R.id.storage_sidebar) {

        } else if (id == R.id.supplies_sidebar) {

        } else if (id == R.id.technology_sidebar) {

        } else if (id == R.id.textbooks_sidebar) {

        } else if (id == R.id.transportation_sidebar) {

        } else if (id == R.id.misc_sidebar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Mainfeed Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void setFilter(){
        SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private class MyTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Post>>{
        protected ArrayList<Post> doInBackground(ArrayList<String>... id) {
            try {
                ArrayList<Post> posts = Server.searchPostTags(id[0]);
                return posts;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<Post> result) {
            if(result!=null) {
                adapter = new CustomAdapter(Mainfeed.this, result);
                list.setAdapter(adapter);
            }
        }
    }
/*
    //Bottom Tool Bar

    private void setOnNavigationItemSelectedListener(int menuId, boolean selected){
        if (menuId == R.id.bottom_mainfeed) {
    //        setContentView(R.layout.activity_mainfeed);
        } else if (menuId == R.id.bottom_upload) {
    //        setContentView(R.layout.activity_create__post);
        } else if (menuId == R.id.bottom_cart) {
    //        setContentView(R.layout.activity_cart);
        } else if (menuId == R.id.bottom_profile){
    //        setContentView(R.layout.activity_profile);
        }
    }

    private void showToast(int menuId, boolean isReselected) {
        if (menuId == R.id.bottom_mainfeed) {

        } else if (menuId == R.id.bottom_upload) {

        } else if (menuId == R.id.bottom_cart) {

        } else if (menuId == R.id.bottom_profile){

        }
    }

    private void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
*/


}


