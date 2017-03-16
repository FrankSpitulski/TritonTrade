package com.tmnt.tritontrade.view;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.util.ArrayList;

import static com.tmnt.tritontrade.R.id.bottom_cart;
import static com.tmnt.tritontrade.R.id.bottom_edit_category;
import static com.tmnt.tritontrade.R.id.bottom_mainfeed;
import static com.tmnt.tritontrade.R.id.bottom_profile;
import static com.tmnt.tritontrade.R.id.bottom_upload;

public class Profile extends AppCompatActivity {

    private ListView list;
    private int EDIT_PROFILE = 1;
    private Button forSale;
    private Button sold;
    private User currUser;
    private ProfileListAdaptor adapter;
    private boolean isSelling = true;



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Private inner class that populates the List view for the current profile
     */
    private class PopulateListTask extends AsyncTask<ArrayList<Integer>, Void, ArrayList<Post>>{
        private ProgressDialog dialog=new ProgressDialog(Profile.this);
        ArrayList<Post> selling = new ArrayList<>();
        ArrayList<Post> productSold = new ArrayList<>();

        /**
         * get the post history from the server
         * @param params the list of ID's
         * @return the list of posts
         */
        protected ArrayList<Post> doInBackground(ArrayList<Integer>... params) {
            try {
                params[0] = currUser.getPostHistory();
                ArrayList<Post> posts = Server.searchPostIDs(params[0]);
                return posts;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Display a loading sign as it loads
         */
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading");
            this.dialog.show();
        }

        /**
         * Populate the List view using the elements from the return of doInBackground
         * @param result the result of doInBackground
         */
        protected void onPostExecute(ArrayList<Post> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }


            if(result != null){

                for(int i = 0; i < result.size(); i++){
                    if(true) {
                        productSold.add(result.get(i));
                    }

                    if(result.get(i).getActive()){
                        selling.add(result.get(i));
                    }
                }

                if(isSelling) {
                    adapter = new ProfileListAdaptor(Profile.this, selling, true);
                } else {
                    adapter = new ProfileListAdaptor(Profile.this, productSold, true);
                }

                list.setAdapter(adapter);
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!CurrentState.getInstance().isLoggedIn()){
            CurrentState.getInstance().killLogin(this);
            return;
        }

        currUser = CurrentState.getInstance().getCurrentUser();
        if(currUser == null) {
            throw new NullPointerException("The User is Null");
        }


        //Set the selling and for sale buttons
        forSale = (Button) findViewById(R.id.forSaleButton);
        forSale.setTextColor(Color.parseColor("#FFEE00"));

        sold = (Button) findViewById(R.id.soldButton);

        //Set the profile photp
        new DownloadPhotosAsyncTask(this, (ImageView) findViewById(R.id.userPic))
                .execute(currUser.getPhoto());

        //populate the fields with the relevant info
        populateUserInfo(currUser);
        populateList();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //bottom tool bar

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        bottomNavigationView.getMenu().getItem(4);

                        Intent in;
                        switch(item.getItemId()){
                            case bottom_mainfeed:
                                in=new Intent(getBaseContext(),Mainfeed.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                return true;
                            case bottom_edit_category:
                                in = new Intent(getBaseContext(), Edit_Categories.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(in);
                                return true;
                            case bottom_cart:
                                in=new Intent(getBaseContext(),Cart.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                return true;
                            case bottom_upload:
                                in=new Intent(getBaseContext(),Create_Post.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(in);
                                return true;
                            case bottom_profile:
                                in=new Intent(getBaseContext(),Profile.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(in);
                                return true;
                            default:
                                return false;
                        }
                    }
                }
        );

    }


    /**
     * Send the user to edit profile if they click the button
     * @param view
     */
    public void sendToEditProfile(View view) {

        Intent intent = new Intent(this, EditProfile.class);

        startActivityForResult(intent, EDIT_PROFILE);

    }

    /**
     * If the user selects the for sale tab, highlight it and display the relevant posts in the list
     * @param view
     */
    public void onForSaleClick (View view){
        forSale.setTextColor(Color.parseColor("#FFEE00"));
        sold.setTextColor(Color.WHITE);

        //Update list to only show items for sale

        isSelling = true;

        list = (ListView) findViewById(R.id.list);
        //random posts
        ArrayList<Integer> postIds = new ArrayList<>();
        new PopulateListTask().execute(postIds);

        /*
        adapter = new ProfileListAdaptor(Profile.this, selling, true);
        list.setAdapter(adapter);
        list.deferNotifyDataSetChanged();
        */

    }

    /**
     * If the user selects the history tab, highlight it and display the relevent history posts
     * @param view
     */
    public void onSoldClick (View view){
        sold.setTextColor(Color.parseColor("#FFEE00"));
        forSale.setTextColor(Color.WHITE);


        isSelling = false;
        list = (ListView) findViewById(R.id.list);
        //random posts
        ArrayList<Integer> postIds = new ArrayList<>();
        new PopulateListTask().execute(postIds);
        /*
        //Update list to only show Items that have been sold
        adapter = new ProfileListAdaptor(Profile.this, productSold, false);

        list.setAdapter(adapter);
        list.deferNotifyDataSetChanged();
        */
    }

    /**
     * Called when EditProfile is finished, processes the new user data and displays it
     * @param requestCode code sent with the activity
     * @param resultCode code returned from the activity
     * @param data user object returned from EditProfile
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_PROFILE){
            if(resultCode == Activity.RESULT_OK) {
                currUser = data.getParcelableExtra("updatedUser");
                populateUserInfo(currUser);
            }
        }
    }


    /**
     * Updates the fields in Profile to reflect the User information
     * @param user the current User
     */
    private void populateUserInfo(User user) {

        //Get references to all the relevant fields in the profle
        TextView username = (TextView) findViewById(R.id.username);
        TextView email = (TextView) findViewById(R.id.email);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView bio = (TextView) findViewById(R.id.bio);



        //populate the fields with the information obtained from the user

        new DownloadPhotosAsyncTask(this, (ImageView) findViewById(R.id.userPic))
                .execute(currUser.getPhoto());

        username.setText(user.getName());
        email.setText(user.getEmail());

        if((user.getMobileNumber().substring(0,5).equals("+0001"))){
            phone.setText(user.getMobileNumber().substring(6));
        } else {
            phone.setText(user.getMobileNumber());
        }

        bio.setText(user.getBio());

    }


    /**
     * Called when you want to populate the list
     */
    private void populateList() {

        list = (ListView) findViewById(R.id.list);
        //random posts
        ArrayList<Integer> postIds = new ArrayList<>();
        new PopulateListTask().execute(postIds);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Profile Page")
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

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getBaseContext(), Mainfeed.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }

}