package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

    ListView listView;
    ArrayAdapter<Post> adapter;
    ArrayList<User> temp;

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
//        postsToView = new ArrayList<>();
//
//        //get the arraylist of posts in the cart of the user, this method will then populate
//        //the cart once the data is retrieved
//        new SearchPostTask().execute();


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

    @Override
    protected void onStart() {
        super.onStart();

        //get the arraylist of posts in the cart of the user, this method will then populate
        //the cart once the data is retrieved

        postsToView = new ArrayList<>();
        new SearchPostTask().execute();
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
                //adapter.notifyDataSetChanged();

                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);

                //  startActivity(new Intent(getApplicationContext(), Cart.class));

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

        alert.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getBaseContext(), "Item removed", Toast.LENGTH_SHORT).show();

                //deletes post and reloads page without this  removed post

                new SearchUserTask().execute();
                User temp = user;
//                if (postSeller!=null){
//                    Toast.makeText(getBaseContext(), ""+postSeller.getProfileID(), Toast.LENGTH_SHORT).show();
//                }
                new UpdateUserTask().execute(postSeller);
                CurrentState.getInstance().setCurrentUser(postSeller);
                Intent toSellerProf = new Intent(getApplicationContext(), Profile_NonUser.class);
                toSellerProf.putExtra("Seller", postSeller);
                startActivity(toSellerProf);

                //CurrentState.getInstance().setCurrentUser(user);


            /*
            new UpdateUserTask().execute(currUser);
            CurrentState.getInstance().setCurrentUser(currUserser);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedUser", currUser);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
                 */




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


            //testing
//            description.setText("testing this is position "+ position + "and postorder:\n" +
//                    user.getCartIDsString());


            ////////NOTE: POSTSTOVIEW IS NOT IN THE RIGHT ORDER)/////////
//            String temptitle = "";
//            for (Post p : postsToView) {
//                String s = ""+p.getPostID();
//                temptitle += s + " ";
//            }
//
//            title.setText(temptitle);
/////////////////////TESTING//////////////



            //set price and rental attributes and default image of post
            String stringPrice = "$" + String.valueOf(post.getPrice());
            price.setText(stringPrice);
            title.setText(String.valueOf(post.getProductName()));
            String firstPhoto = post.getPhotos().get(0); //first photo of the ones you uploaded, "default"
            new DownloadPhotosAsyncTask(image).execute(firstPhoto);

            //DISPLAY TITLE
            if (post.getProductName().length() >= 22) {
                String titleTrim = post.getDescription().substring(0, 21) + "...";
                title.setText(String.valueOf(post.getProductName()));
            } else {
                title.setText(String.valueOf(post.getProductName()));
            }


            //DISPLAY DESCRIPTION
            if (post.getProductName().length() >= 17) {
                if (post.getDescription().length() >= 45) {
                    String descriptionTrim = post.getDescription().substring(0, 45) + "...";
                    description.setText(descriptionTrim);
                } else {
                    description.setText(post.getDescription());
                }
            } else {
                if (post.getDescription().length() >= 100) {
                    String descriptionTrim = post.getDescription().substring(0, 98) + "...";
                    description.setText(descriptionTrim);
                } else {
                    description.setText(post.getDescription());
                }
            }





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
                    String sellerEmail = "dummy@ucsd.edu";
                    String sellerPhone = "+0001 (888) 888-8888";
                    displayContactDialog(sellerEmail, sellerPhone);
                }
            });


//            title.setText("UserID: "+user.getProfileID() + ", and sellerID: " + currentPost.getProfileID()+
//                    ", and PostID: "+ currentPost.getPostID());
//            new SearchUserTask().execute();
//
//            if (postSeller == null){
//                title.setText("this is null");
//            }
//            else if (postSeller.getProfileID() == currentPost.getProfileID()){
//                title.setText("success, this posts sellerID is: "+postSeller.getProfileID());
//
//            }
//            else{
//                title.setText("its just me");
//            }

            return view;
        }
    }

    ///////////////////////ASYNC task for loading posts to cart///////////////////////
    private class SearchPostTask extends AsyncTask<Object, Object, Object> {
        private ProgressDialog dialog = new ProgressDialog(Cart.this);

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
        protected void onPreExecute() {
            this.dialog.setMessage("Loading..");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result != null) {
                posts = (ArrayList<Post>) result;

                //posts will hold stuff from server in order (smallest postIDs at front/top)
                //cartInt continues holding cart order (recent postID at front)
                //postsToView will be populated with posts from posts in the order of cartInt

                for (int cartids = 0; cartids < cartInt.size(); cartids++) {
                    int toLookFor = cartInt.get(cartids);

                    for (int resultids = 0; resultids < posts.size(); resultids++) {
                        int toCheck = posts.get(resultids).getPostID();

                        if (toLookFor == toCheck) {
                            postsToView.add(posts.get(resultids));
                            break;
                        }
                    }

                } //end of loop to get cart order


                //set that the posts retrieved from server are to be displayed
                //postsToView = posts;

                //create our new array adapter
                adapter = new postArrayAdapter(Cart.this, 0, postsToView);

                //Find list view and bind it with the custom adapter
                listView = (ListView) findViewById(R.id.cart_list);
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


//        @Override
//        protected void onPostExecute(Object result) { //nothing
//        }
    }


    private class UpdateUserTask extends AsyncTask<User, Void, Void> {
        protected Void doInBackground(User... params) {
            try {
                Server.modifyExistingUser(params[0]);


                //   CurrentState.getInstance().setCurrentUser(params[0]);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
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





