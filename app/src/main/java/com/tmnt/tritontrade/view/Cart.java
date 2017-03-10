package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tmnt.tritontrade.R.id.bottom_cart;
import static com.tmnt.tritontrade.R.id.bottom_edit_category;
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
        setTitle("My Cart");

       /*
       TO USE LATER:

        USER:
        public String getCartIDsString()
        public ArrayList<Integer> getCartIDs()
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
        cartInt = user.getCartIDs();
        postsToView = new ArrayList<>();

        //get the arraylist of posts in the cart of the user, this method will then populate
        //the cart once the data is retrieved
        new SearchPostTask().execute();


        //bottom tool bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == bottom_mainfeed){
                            Intent in=new Intent(getBaseContext(),Mainfeed.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        else if (item.getItemId() == bottom_edit_category) {
                            Intent in = new Intent(getBaseContext(), Edit_Categories.class);
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
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
                        }
                        else if(item.getItemId() == bottom_profile){
                            Intent in=new Intent(getBaseContext(),Profile.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            return true;
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
                new ModifyUserCart().execute();
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

    void removeShiftMode(BottomNavigationView view) {
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


    //-------------------------------ASYNC TASKS----------------------------------//

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
            int descriptionLength;
            if (post.getDescription() == null) {
                descriptionLength = 0;
                description.setText("");
            } else {

                descriptionLength = post.getDescription().length();

                if (descriptionLength >= 100) {
                    String descriptionTrim = post.getDescription().substring(0, 100) + "...";
                    description.setText(descriptionTrim);
                } else {
                    description.setText(post.getDescription());
                }

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

                //set that the posts retrieved from server are to be displayed
                postsToView = posts;

                //create our new array adapter
                ArrayAdapter<Post> adapter = new postArrayAdapter(Cart.this, 0, postsToView);

                //Find list view and bind it with the custom adapter
                ListView listView = (ListView) findViewById(R.id.cart_list);
                listView.setAdapter(adapter);
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

    public class ModifyUserCart extends AsyncTask<Object, Object, Object> {
        @Override
        protected Boolean doInBackground(Object... params) {
            try {
                Server.modifyExistingUser(user);
                return true;
            } catch (IOException e) {
                Log.d("DEBUG", e.toString());
                return false;
            } catch (IllegalArgumentException e2) {
                Log.d("DEBUG", e2.toString());
                return false;
            }
        }


        @Override
        protected void onPostExecute(Object result) { //nothing
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





