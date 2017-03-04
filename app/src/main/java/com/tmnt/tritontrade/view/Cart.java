package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.content.Context;
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

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart extends AppCompatActivity {

    Button removeFromCart;
    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


       /* public Post(String productName, ArrayList<String> photos, String description,
        float price, ArrayList<String> tags, int profileID, int postID,
        boolean selling, boolean active , Date dateCreated, String contactInfo, boolean deleted) */


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
            View view = inflater.inflate(R.layout.cart_item, null);

            TextView description = (TextView) view.findViewById(R.id.item_description);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            TextView price = (TextView) view.findViewById(R.id.item_price);
            ImageView image = (ImageView) view.findViewById(R.id.image);

//            //set address and description
//            String completeAddress = post.getStreetNumber() + " " + post.getStreetName() + ", " + property.getSuburb() + ", " + property.getState();
//            address.setText(completeAddress);

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
            //description.setText(String.valueOf(post.getDescription()));


            String firstPhoto = post.getPhotos().get(0);

            //get the image associated with this property
            int imageID = context.getResources().getIdentifier(firstPhoto, "drawable", context.getPackageName());
            image.setImageResource(imageID);

            return view;
        }


    }


}
