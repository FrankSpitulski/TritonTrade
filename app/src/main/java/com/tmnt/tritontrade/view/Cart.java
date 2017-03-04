package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

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
    User user; //self
    private ArrayList<Post> posts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);



       /* public Post(String productName, ArrayList<String> photos, String description,
        float price, ArrayList<String> tags, int profileID, int postID,
        boolean selling, boolean active , Date dateCreated, String contactInfo, boolean deleted) */


        //TO USE?
       /*
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
        ArrayList<Integer> cartInt = user.getCartIDsFromString(user.getCartIDsString());

        for (int i = 0; i < cartInt.size(); i++) {

            try {
                Post postToAdd = Server.searchPostIDs(cartInt.get(i));
                if (postToAdd == null) {
                    continue;
                }
                posts.add(postToAdd);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }




        //populate posts hardcoded
        ArrayList<String> photos = new ArrayList<>();
        photos.add("square_boxes");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("category 1");
        Date date = new Date();

        //create Post elements
        posts.add(
                new Post("Boxes", photos, "5 boxes for sale", (float) 5.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));

        posts.add(
                new Post("Moving", photos, "Truck services $20/hr, will help with moving", (float) 10.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));

        posts.add(
                new Post("Product Name", photos, "Description of item", (float) 3.50,
                        tags, 4504, 10, true, true, date, "Contact Info", false));

        posts.add(
                new Post("Product Name", photos, "Description of item", (float) 999.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));


        //create our new array adapter
        ArrayAdapter<Post> adapter = new postArrayAdapter(this, 0, posts);

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
                            Intent in=new Intent(getBaseContext(),Mainfeed.class);
                            startActivity(in);
                        }
                        else if (item.getItemId() == bottom_cart){
                            Intent in=new Intent(getBaseContext(),Cart.class);
                            startActivity(in);

                        }
                        else if(item.getItemId() == bottom_upload){
                            Intent in=new Intent(getBaseContext(),Create_Post.class);
                            startActivity(in);
                        }
                        else if(item.getItemId() == bottom_profile){
                            Intent in=new Intent(getBaseContext(),Profile.class);
                            startActivity(in);
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
                Toast.makeText(getBaseContext(), "OK clicked", Toast.LENGTH_SHORT).show();
                //code for deleting a post
                //how to know which post i am on?

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    /////////////////////confirmation button for remove from cart//////////////////
    public void displayContactDialog() {
        //how to know im in which post?
        //use code above in oncreate

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Contact Seller");
        alert.setMessage("Email: \nPhone: ");
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


    //////////////////////////////////////custom ArrayAdapter//////////////////
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

            //set price and rental attributes
            String stringPrice = "$" + String.valueOf(post.getPrice());
            price.setText(stringPrice);
            title.setText(String.valueOf(post.getProductName()));


            String firstPhoto = post.getPhotos().get(0);

            //get the image associated with this property
            /////////make so it doesnt take from drawable and from web or something
            int imageID = context.getResources().getIdentifier(firstPhoto, "drawable", context.getPackageName());
            image.setImageResource(imageID);


            //////////////////remove from cart button//////////////////////
            confirmRemoveButton = (Button) view.findViewById(R.id.remove_button);
            confirmRemoveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    displayConfirmationDialog();
                }
            });


            //////////////////display contact info button//////////////////////
            displayContactButton = (Button) view.findViewById(R.id.contact_button);
            displayContactButton.setOnClickListener(new View.OnClickListener() {

                //get current post's userID.get contact info() pass to displaycontactdialog
                @Override
                public void onClick(View v) {
                    displayContactDialog();
                }
            });


            return view;
        }
    }


    //async task???? idk man im sorry :(

}





