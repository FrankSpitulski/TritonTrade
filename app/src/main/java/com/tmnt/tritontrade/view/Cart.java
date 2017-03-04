package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
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
import com.tmnt.tritontrade.controller.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tmnt.tritontrade.R.layout.cart_item;

public class Cart extends AppCompatActivity {

    Button displayContactButton, confirmRemoveButton;
    User user;
    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);



       /* public Post(String productName, ArrayList<String> photos, String description,
        float price, ArrayList<String> tags, int profileID, int postID,
        boolean selling, boolean active , Date dateCreated, String contactInfo, boolean deleted) */


        //From server populate posts
       /* public String getCartIDsString()
        static ArrayList<Integer> getCartIDsFromString(String history)
        public boolean removeFromCart(int id){ return cartIDs.remove((Integer) new Integer(id)); }
        public int getPostID()
          */


        //gets posts to load
        user = CurrentState.getInstance().getCurrentUser();
        //ArrayList<Integer> cartInt = User.getCartIDsFromString( user.getCartIDsString() );
        //get post from postID
        //



        //populate posts hardcoded
        ArrayList<String> photos = new ArrayList<>();
        photos.add("square_boxes");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("category 1");
        Date date = new Date();

        //create Post elements
        posts.add(
                new Post("Product Name", photos, "Description of item", (float) 5.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));

        posts.add(
                new Post("Product Name", photos, "Description of item", (float) 5.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));

        posts.add(
                new Post("Product Name", photos, "Description of item", (float) 5.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));

        posts.add(
                new Post("Product Name", photos, "Description of item", (float) 5.00,
                        tags, 4504, 10, true, true, date, "Contact Info", false));


        //create our new array adapter
        ArrayAdapter<Post> adapter = new postArrayAdapter(this, 0, posts);

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.cart_list);
        listView.setAdapter(adapter);


    }

    ///confirmation button for remove from cart
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

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    ///confirmation button for remove from cart
    public void displayContactDialog() {
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

    //custom ArrayAdapter
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


}





