package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.User;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nikolasn97 on 2/23/17.
 */

class ProfileListAdaptor extends BaseAdapter {

    private Context context;
    private CustomAdapter.CustomFilter filter;
    private ArrayList<Post> posts;
    private ArrayList<Post> filterList;
    private LayoutInflater inflater;
    private int count; //Current amount of items displayed
    private int stepNumber; //Amount of items loaded on next display
    private final int startCount=20; //Start amount of items being displayed
    private Boolean _selling; // Flag for Selling and Sold

    // Saves the position of the post to be deleted
    private int _pos;
    private User currUser;

    // Asynchronous Task that handles deleting a post
    private class deletePostTask extends AsyncTask<Post, Void, Boolean>{

        private ProgressDialog dialog=new ProgressDialog(context);
        protected Boolean doInBackground(Post... params) {
            try {

                // Modify both the user and post
                Boolean postSuccess = Server.modifyExistingPost(params[0]);
                Boolean userSuccess = Server.modifyExistingUser(currUser);

                return postSuccess && userSuccess;
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

        protected void onPostExecute(Boolean result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // Modify the list if the deletion was successful
            if ( result ) {
                posts.remove(_pos);
                notifyDataSetChanged();
            }

        }
    }

    /**
     * Constructor
     * @param context
     * @param posts
     */
    public ProfileListAdaptor(Context context, ArrayList<Post> posts, Boolean selling) {
        this.posts=posts;
        this.filterList=posts;
        this.context = context;
        this.count = startCount;
        this.stepNumber=10;
        this._selling = selling;
    }

    /**
     * Setter for posts
     * @param posts
     */
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    /**
     * Setter for count
     * @param count
     */
    public void setCount(int count){
        this.count=count;
    }

    /**
     * Gets the total amount of items in the list
     * @return
     */
    @Override
    public int getCount() {
        if (posts.size() < startCount){
            return posts.size(); //returns total of items in the list
        }
        else{
            return count;
        }
    }


    /**
     * Sets the ListView back to its initial count number
     */
    public void reset(){
        count = startCount;
        notifyDataSetChanged();
    }

    /**
     * Show more views, or the bottom
     * @return true if the entire data set is being displayed, false otherwise
     */
    public boolean showMore(){
        if(count == posts.size()) {
            return true;
        }else{
            count = Math.min(count + stepNumber, posts.size()); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
            return count == posts.size();
        }
    }

    /**
     * Returns item at given position in list
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position){
        return posts.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * Holder for post object
     */
    public class ViewHolder{
        TextView title;
        TextView description;
        //TextView userTag;
        //TextView price;
        //TextView category;
        //ImageView image;
    }

    /**
     * Sets items to
     * @param position position of current item
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        View catView = convertView;

        //using adapter to display all categories
        if(convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            catView = inflater.inflate(R.layout.row, null);
        }
        ProfileListAdaptor.ViewHolder postHolder = new ProfileListAdaptor.ViewHolder();

        //Set everything in feed row
        postHolder.title = (TextView) catView.findViewById(R.id.title);
        postHolder.description = (TextView) catView.findViewById(R.id.description);
        //postHolder.userTag = (TextView) catView.findViewById(R.id.username_tag);
        //postHolder.category=(TextView) catView.findViewById(R.id.category_text);
        //postHolder.price = (TextView) catView.findViewById(R.id.price);
        //postHolder.image = (ImageView) catView.findViewById(R.id.row_pic);

        postHolder.title.setText(posts.get(position).getProductName());
        postHolder.description.setText(posts.get(position).getDescription());

        // Button for deleting posts
        Button deleteBtn = (Button)catView.findViewById(R.id.delete_btn);
        Button editBtn = (Button)catView.findViewById(R.id.edit_btn);

        if (!_selling) {

            View b = catView.findViewById(R.id.delete_btn);
            b.setVisibility(View.GONE);
            b = catView.findViewById(R.id.edit_btn);
            b.setVisibility(View.GONE);
        }


        /*
        if(CurrentState.getInstance() != null) {
            postHolder.userTag.setText(CurrentState.getInstance().getCurrentUser().getName());
        }
        */

        //postHolder.category.setText(posts.get(position).getTags().get(1));
        //postHolder.price.setText("$"+String.valueOf(posts.get(position).getPrice()));
        /*
        new DownloadPhotosAsyncTask(postHolder.image)
                .execute(posts.get(position).getPhotos().get(0));
                */

        //Click listener for post
        catView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PopUpPost.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("category", posts.get(position));
                ((Activity)context).startActivityForResult(i, 1);

            }
        });

        // CLICK Listener For Delete Button
        deleteBtn.setOnClickListener(new View.OnClickListener(){

            // When the button is clicked delete the post item
            @Override
            public void onClick(View view){

                // Display the confirmation window for deleting a post
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Set the post to be deleted's deleted field to true
                                posts.get(position).setDeleted(true);

                                // Then modify the list returned by the server
                                _pos = position;

                                if(!CurrentState.getInstance().isLoggedIn()){
                                    // you goofed if you got here
                                    return;
                                }

                                // Modify the user's postHistory
                                currUser = CurrentState.getInstance().getCurrentUser();
                                ArrayList<Integer> currPH = currUser.getPostHistory();

                                int indOfPost= currPH.indexOf(posts.get(position).getPostID());
                                currPH.remove(indOfPost);

                                new deletePostTask().execute(posts.get(position));


                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        // CLICK Listener For Edit Button
        editBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent in = new Intent(context, PopupEditPost.class);
                in.putExtra("Post", posts.get(position));
                in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(in);
            }
        });

        return catView;
    }


}
