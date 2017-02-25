package com.tmnt.tritontrade.view;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tmnt.tritontrade.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfeed);
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

        //DEMO
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<String> photos = new ArrayList<>();
        ArrayList<String> photos2 = new ArrayList<>();
        ArrayList<String> photos3 = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("fine");
        Date s = new Date();
        photos.add("https://storage.googleapis.com/gweb-uniblog-publish-prod/static/blog/images" +
                "/google-200x200.7714256da16f.png");
        photos2.add("https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg");
        photos3.add("http://farm7.staticflickr.com/6047/7036787275_951cb768fe.jpg");
        Post post1 = new Post("Stuff", photos, "Description stuff",
        0, tags, 1, 1, true, true , s , "Phone number", false);
        posts.add(post1);
        Post post2 = new Post("Stuff2", photos2, "Description stuff",
                0, tags, 1, 1, true, true , s , "Phone number", false);

        Post post3 = new Post("Stuff3", photos3, "DEMO",
                0, tags, 1, 1, true, true , s , "Phone number", false);
        posts.add(post2);
        posts.add(post3);
        setFilter();
        list = (ListView) this.findViewById(R.id.listFeed);
        adapter=new CustomAdapter(this, posts);
        list.setAdapter(adapter);


        //MyTask task = new MyTask();
        //task.execute(tags);

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
            //    list.setAdapter(new CustomAdapter(getApplicationContext(), result));
            }
        }
    }
}


