package com.tmnt.tritontrade.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.tmnt.tritontrade.R.id.bottom_cart;
import static com.tmnt.tritontrade.R.id.bottom_edit_category;
import static com.tmnt.tritontrade.R.id.bottom_mainfeed;
import static com.tmnt.tritontrade.R.id.bottom_profile;
import static com.tmnt.tritontrade.R.id.bottom_upload;

/**
 * Created by Edward Ji
 */

public class Mainfeed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CustomAdapter adapter;
    private ListView list;
    private SwipeRefreshLayout swipeContainer;
    ArrayList<String> lastSearchedTags;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfeed);
        setTitle("My Feed");
        list = (ListView) this.findViewById(R.id.listFeed);
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
        navigationView.bringToFront();

        ArrayList<String> tags = new ArrayList<>();
        //fillDefaultTags(tags);
        /***SET DATA***/
//        tags.add("food");
        lastSearchedTags=tags;
        fillDefaultTags(tags);
        setAdapterInfo(tags);


        //PULL REFRESH
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                setAdapterInfo(lastSearchedTags);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottom_navigation);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener
//                (new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
//                        Intent in;
//                        switch (item.getItemId()) {
//                            case R.id.bottom_mainfeed:
//                                in =new Intent(getBaseContext(),Mainfeed.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                startActivity(in);
//                                return true;
//                            case R.id.bottom_edit_category:
////                                selectedFragment = EditCategoryFragment.newInstance();
//                                in =new Intent(getBaseContext(),Edit_Categories.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                startActivity(in);
//                                return true;
//                            case R.id.bottom_upload:
////                                selectedFragment = CreatePostFragment.newInstance();
//                                in =new Intent(getBaseContext(),Create_Post.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                startActivity(in);
//                                return true;
//                            case R.id.bottom_cart:
//                                selectedFragment = CartFragment.newInstance();
//                                break;
//                            case R.id.bottom_profile:
//                                selectedFragment = ProfileFragment.newInstance();
//                                break;
//                        }
//                        FragmentTransaction transaction = getSupportFragmentManager().
//                                beginTransaction();
//                        transaction.replace(R.id.frame_layout, selectedFragment);
//                        transaction.commit();
//                        return true;
//                    }
//                });
//        FragmentTransaction transaction = getSupportFragmentManager().
//                beginTransaction();
//        transaction.replace(R.id.frame_layout, MainFeedFragment.newInstance());
//        transaction.commit();
        //bottom tool bar
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        removeShiftMode(bottomNavigationView);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        bottomNavigationView.getMenu().getItem(0);

                        if(item.getItemId() == bottom_mainfeed){
                            Intent in=new Intent(getBaseContext(),Mainfeed.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        else if (item.getItemId() == bottom_edit_category) {
                            Intent in = new Intent(getBaseContext(), Edit_Categories.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        else if (item.getItemId() == bottom_cart){
                            Intent in=new Intent(getBaseContext(),Cart.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;

                        }
                        else if(item.getItemId() == bottom_upload){
                            Intent in=new Intent(getBaseContext(),Create_Post.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                            return true;
                        }
                        else if(item.getItemId() == bottom_profile){
                            Intent in=new Intent(getBaseContext(),Profile.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                            return true;
                        }
                        return false;
                    }
                }
        );
    }

    /**
     * Fills ArrayList with users preferred tags.
     * @param tags
     */
    private void fillDefaultTags(ArrayList<String> tags){
        String userID = Integer.toString(CurrentState.getInstance().getCurrentUser().getProfileID());
        SharedPreferences tagNames = getSharedPreferences(userID, Context.MODE_PRIVATE);
        Set<String> tagSet = tagNames.getStringSet(userID,new HashSet<String>());
        Log.i("DEBUG", "2.set = "+tagNames.getStringSet("set",
                new HashSet<String>()));
        if(tagSet.isEmpty()){
            return;
        }
        for(String s: tagSet){
            tags.add(s);
        }
    }

    /**
     * Takes in tags and set appropriate content in grid
     * @param tags the tags to be displayed
     */
    private void setAdapterInfo(ArrayList<String> tags){
        new FeedSetupTask().execute(tags);
        final SearchView sv = (SearchView) findViewById(R.id.searchView);
        //Search Bar implementation
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // adapter.getFilter().filter(query);
                ArrayList<String> tagsQ = new ArrayList<String>();
                tagsQ.add(query);
                lastSearchedTags=tagsQ;
                new FeedSetupTask().execute(tagsQ);
                sv.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Left navigation bar
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        ArrayList<String> tags= new ArrayList<>();
        if (id == R.id.clothing_sidebar) {
            tags.add("Clothing");
        }
        else if(id==R.id.following_sidebar){
            fillDefaultTags(tags);
        }
        else if (id == R.id.food_sidebar) {
            tags.add("Food");
        } else if (id == R.id.services_sidebar) {
            tags.add("Services");
        } else if (id == R.id.storage_sidebar) {
            tags.add("Storage");
        } else if (id == R.id.supplies_sidebar) {
            tags.add("Supplies");
        } else if (id == R.id.technology_sidebar) {
            tags.add("Technology");
        } else if (id == R.id.textbooks_sidebar) {
            tags.add("Textbooks");
        } else if (id == R.id.transportation_sidebar) {
            tags.add("Transportation");
        } else if (id == R.id.misc_sidebar) {
            tags.add("Miscellaneous");
        }
        lastSearchedTags=tags;
        setAdapterInfo(tags);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Async task used for initial setup of mainfeed(posts)
     */
    private class FeedSetupTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Post>> {
        private ProgressDialog dialog = new ProgressDialog(Mainfeed.this);

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

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading");
            this.dialog.show();
        }

        protected void onPostExecute(ArrayList<Post> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result != null) {
                adapter = new CustomAdapter(Mainfeed.this, result);
                list.setAdapter(adapter);
                list.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        int count = list.getCount();
                        if(list.getLastVisiblePosition()>=count-1){
                            adapter.showMore();
                        }

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                       // adapter.showMore();
                    }


                });
            }
        }
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

    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }








}


