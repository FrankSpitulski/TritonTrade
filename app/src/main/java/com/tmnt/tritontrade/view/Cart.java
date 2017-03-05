package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tmnt.tritontrade.R.id.bottom_cart;
import static com.tmnt.tritontrade.R.id.bottom_mainfeed;
import static com.tmnt.tritontrade.R.id.bottom_profile;
import static com.tmnt.tritontrade.R.id.bottom_upload;
import static com.tmnt.tritontrade.R.layout.cart_item;

public class Cart extends AppCompatActivity {


    Button displayContactButton, confirmRemoveButton;
    User user;                   //self or current user
    ArrayList<Integer> cartInt;  //list of cart posts' ids, goes through async task, placed into 'posts '
    ArrayList<Post> posts;       //server retrieves current user's cart
    ArrayList<Post> postsToView; //'posts' goes through check, then displayed on Cart screen
    Post currentPost;            //current post that is clicked on
    User postSeller;        //user that created the post you are currently looking at


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

       /* public Post(String productName, ArrayList<String> photos, String description,
        float price, ArrayList<String> tags, int profileID, int postID,
        boolean selling, boolean active , Date dateCreated, String contactInfo, boolean deleted) */

       /*
       TO USE LATER:

        USER:
        public String getCartIDsString()
        static ArrayList<Integer> getCartIDsFromString(String history)
        public boolean removeFromCart(int id){ return cartIDs.remove((Integer) new Integer(id)); }
        public int getPostID()

        public String getMobileNumber()
        public String getEmail()


        SERVER:
        public static Post searchPostIDs(int id) throws IOException { (SERVER)
        public static User searchUserIDs(int id) throws IOException {

        POST:
        public String getContactInfo() ??? when is used
        public int getProfileID()


        WHEN showing contact info in dialog , get
        int sellerPostID = post.getProfileID();
        User seller = User.searchUserIDs( sellerPostID );
        String sellerEmail = seller.getEmail();
        String sellerPhone = seller.getMobileNumber();
        *
        */


        //gets posts to load
        user = CurrentState.getInstance().getCurrentUser();
        cartInt = user.getCartIDsFromString(user.getCartIDsString());
        postsToView = new ArrayList<>();

        for (int i = 0; i < cartInt.size(); i++) {

            try {
                new SearchPostTask().execute();
                Post postToAdd = posts.get(i);
                if (postToAdd == null) {
                    continue;
                }
                postsToView.add(postToAdd);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }



        //populate posts hardcoded
        ArrayList<String> photos = new ArrayList<>();
        //photos.add("square_boxes");
        photos.add("https://s-media-cache-ak0.pinimg.com/564x/8f/09/93/8f0993aefb355a58ad0745ee6ab14e57.jpg");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("category 1");
        Date date = new Date();

        //create Post elements
        postsToView.add(
                new Post("doge", photos, "doing a bark bark", (float) 5.00,
                        tags, 4504, 10, true, true, date, "dog contact", false));

        postsToView.add(
                new Post("pupper", photos, "heckin' curious", (float) 10.00,
                        tags, 4504, 10, true, true, date, "dog contact", false));

        postsToView.add(
                new Post("woofer", photos, "much surprise want treats", (float) 3.50,
                        tags, 4504, 10, true, true, date, "dog contact", false));

        postsToView.add(
                new Post("doggo", photos, "arf arf land seal", (float) 999.00,
                        tags, 4504, 10, true, true, date, "dog contact", false));


        //create our new array adapter
        ArrayAdapter<Post> adapter = new postArrayAdapter(this, 0, postsToView);

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.cart_list);
        listView.setAdapter(adapter);


        //bottom tool bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == bottom_mainfeed){
                            startActivity(new Intent(getApplicationContext(), Mainfeed.class));
                        }
                        else if (item.getItemId() == bottom_cart){
                            startActivity(new Intent(getApplicationContext(), Cart.class));

                        }
                        else if(item.getItemId() == bottom_upload){
                            startActivity(new Intent(getApplicationContext(), Create_Post.class));
                        }
                        else if(item.getItemId() == bottom_profile){
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }
                        return false;
                    }
                }
        );




    }

    //////////////////confirmation button for remove from cart//////////////////
    public void displayConfirmationDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm");
        alert.setMessage("Are you sure you want to remove this item?");
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                //nothing happens
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Item removed", Toast.LENGTH_SHORT).show();

                //deletes post and reloads page without this  removed post
                int removePost = currentPost.getPostID();
                user.removeFromCart(removePost);
                startActivity(new Intent(getApplicationContext(), Cart.class));

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    //-------------------------------BUTTONS----------------------------------//

    /////////////////////confirmation button for remove from cart//////////////////
    public void displayContactDialog(String sellerEmail, String sellerPhone) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Contact Seller");
        alert.setMessage("Email:  " + sellerEmail + "\nPhone:  " + sellerPhone);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                //nothing happens
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    //////////////////////////////////////custom ArrayAdapter/////////////////////////////
    private class postArrayAdapter extends ArrayAdapter<Post> {

        private Context context;
        private List<Post> cartItems;

        //constructor, call on creation
        private postArrayAdapter(Context context, int resource, ArrayList<Post> objects) {
            super(context, resource, objects);

            this.context = context;
            this.cartItems = objects;
        }

        //called when rendering the list
        public View getView(int position, View convertView, ViewGroup parent) {

            //get the property we are displaying
            Post post = cartItems.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(cart_item, null);

            TextView description = (TextView) view.findViewById(R.id.item_description);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            TextView price = (TextView) view.findViewById(R.id.item_price);
            ImageView image = (ImageView) view.findViewById(R.id.image);


            //display trimmed excerpt for description
            int descriptionLength = post.getDescription().length();
            if (descriptionLength >= 100) {
                String descriptionTrim = post.getDescription().substring(0, 100) + "...";
                description.setText(descriptionTrim);
            } else {
                description.setText(post.getDescription());
            }

            //set price and rental attributes and default image of post
            String stringPrice = "$" + String.valueOf(post.getPrice());
            price.setText(stringPrice);
            title.setText(String.valueOf(post.getProductName()));
            String firstPhoto = post.getPhotos().get(0); //first photo of the ones you uploaded, "default"
            new DownloadPhotosAsyncTask(image).execute(firstPhoto);



            //////////////////remove from cart button//////////////////////
            confirmRemoveButton = (Button) view.findViewById(R.id.remove_button);
            currentPost = postsToView.get(position);
            //confirmRemoveButton.setTag(position);
            confirmRemoveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    displayConfirmationDialog();
                }
            });



            //////////////////display contact info button//////////////////////
            displayContactButton = (Button) view.findViewById(R.id.contact_button);
            currentPost = postsToView.get(position);
            //displayRemoveButton.setTag(position);
            displayContactButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    *****wont work because user is null, uncomment later*****
//                    new SearchUserTask().execute();
//                    String sellerEmail = postSeller.getEmail();
//                    String sellerPhone = postSeller.getMobileNumber();
//                    displayContactDialog(sellerEmail, sellerPhone);
                }
            });


            return view;
        }
    }


    //-------------------------------ASYNC TASKS----------------------------------//

    ///////////////////////ASYNC task for loading posts to cart///////////////////////
    private class SearchPostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            try {
                return Server.searchPostIDs(cartInt);
            } catch (IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result != null) {
                posts = (ArrayList<Post>) result;
            } else {
                Toast.makeText(Cart.this, "Load Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /////////////////////////ASYNC task for finding user of current post///////////////////////
    private class SearchUserTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            try {
                return Server.searchUserIDs(currentPost.getProfileID());
            } catch (IOException e) {
                Log.d("DEBUG", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result != null) {
                postSeller = (User) result;
            } else {
                Toast.makeText(Cart.this, "User not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }


//
//    //ASYNC Task for removing a post from cart
//    private class RemovePostTask extends AsyncTask<Object, Object, Object> {
//        @Override
//        protected Object doInBackground(Object... params) {
//            try {
//                return Server.searchPostIDs(cartInt);
//            } catch (IOException e){
//                Log.d("DEBUG", e.toString());
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Object result) {
//            if(result!=null){
//                posts = (ArrayList<Post>) result;
//                startActivity(new Intent(getApplicationContext(), Cart.class));
//            }else{
//                Toast.makeText(Cart.this, "Load Failed", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }







}





