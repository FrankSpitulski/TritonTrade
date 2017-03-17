package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.DownloadPhotosAsyncTask;
import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Edward Ji
 */

class CustomAdapter extends BaseAdapter implements Filterable {

    private final int startCount = 10; //Start amount of items being displayed
    private Context context;
    private CustomFilter filter;
    private ArrayList<Post> posts;
    private ArrayList<Post> filterList;
    private ArrayList<Post> allPosts;
    private LayoutInflater inflater;
    private int count; //Current amount of items displayed
    private int stepNumber; //Amount of items loaded on next display

    /**
     * Constructor Stuff
     * @param context
     * @param posts
     */
    public CustomAdapter(Context context,ArrayList<Post> posts) {
        this.posts=posts;
        this.filterList=posts;
        this.context = context;
        this.count = startCount;
        this.stepNumber=5;
        allPosts = new ArrayList<Post>(posts.size());
        for(int i = 0; i < posts.size(); i++){
            allPosts.add(posts.get(i));
        }
    }

    /**
     * Setter for posts
     * @param posts
     */
    private void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
        allPosts = new ArrayList<Post>(posts.size());
        for(int i = 0; i < posts.size(); i++){
            allPosts.add(posts.get(i));
        }
    }

    /**
     * Gets the total amount of items in the list
     * @return
     */
    @Override
    public int getCount() {
        if(posts.size() < count){
            return posts.size(); //returns total of items in the list
        }
        else{
            return count;
        }
    }

    /**
     * Setter for count
     *
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
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
            catView = inflater.inflate(R.layout.feed_row, null);
        }
        ViewHolder postHolder = new ViewHolder();
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());

        //Set everything in feed row
        postHolder.title = (TextView) catView.findViewById(R.id.title);
        postHolder.description = (TextView) catView.findViewById(R.id.description);
        postHolder.category=(TextView) catView.findViewById(R.id.category_text);
        postHolder.price = (TextView) catView.findViewById(R.id.price);
        postHolder.timeago = (TextView) catView.findViewById(R.id.post_age);
        postHolder.postStatusFeed = (TextView) catView.findViewById(R.id.postStatusFeed);
        postHolder.image = (ImageView) catView.findViewById(R.id.row_pic);

        postHolder.title.setText(posts.get(position).getProductName());
        postHolder.description.setText(posts.get(position).getDescription());
        if(posts.get(position).getTags().size() > 1) {
            postHolder.category.setText(posts.get(position).getTags().get(1).toUpperCase());
        }
        postHolder.price.setText(currency.format(posts.get(position).getPrice()));
        postHolder.timeago.setText(DateUtils.getRelativeTimeSpanString(posts.get(position).getDateCreated().getTime()));

        if(!posts.get(position).getActive()){
            postHolder.postStatusFeed.setText("SOLD");
            postHolder.postStatusFeed.setTextColor(ContextCompat.getColor(context, R.color.Sold));
            postHolder.postStatusFeed.setTypeface(null, Typeface.BOLD);
            postHolder.price.setTextColor(ContextCompat.getColor(context, R.color.greyPrice));
        }else if(posts.get(position).getSelling()){
            postHolder.postStatusFeed.setText("SELLING");
            postHolder.postStatusFeed.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            postHolder.postStatusFeed.setTypeface(null, Typeface.NORMAL);
            postHolder.price.setTextColor(ContextCompat.getColor(context, R.color.greenPrice));
        }else{
            postHolder.postStatusFeed.setText("BUYING");
            postHolder.postStatusFeed.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            postHolder.postStatusFeed.setTypeface(null, Typeface.NORMAL);
            postHolder.price.setTextColor(ContextCompat.getColor(context, R.color.greenPrice));
        }

        if(Objects.equals(posts.get(position).getPhotos().get(0), "")){
            postHolder.image.setVisibility(View.GONE);
        }
        else{
            postHolder.image.setVisibility(View.VISIBLE);
            new DownloadPhotosAsyncTask(context, postHolder.image)
                    .execute(posts.get(position).getPhotos().get(0));
            //Glide.with(context).load(posts.get(position).getPhotos().get(0)).into(postHolder.image);
        }

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
        return catView;
    }

    public void setCurrentFilters(String s){
        setPosts(allPosts);
        if(s.equals("Most Recent")){
            //setPosts(allPosts);
        }
        else if(s.equals("Price: Lowest to Highest")){
            class LowToHighComparator implements Comparator<Post> {
                @Override
                public int compare(Post o1, Post o2) {
                    if(o1.getPrice() < o2.getPrice()){
                        return -1;
                    }else if(o1.getPrice() > o2.getPrice()){
                        return 1;
                    }
                    return 0;
                }
            }
            Collections.sort(posts, new LowToHighComparator());
        }
        else if(s.equals("Price: Highest to Lowest")){
            class HighToLowComparator implements Comparator<Post> {
                @Override
                public int compare(Post o1, Post o2) {
                    if(o1.getPrice() < o2.getPrice()){
                        return 1;
                    }else if(o1.getPrice() > o2.getPrice()){
                        return -1;
                    }
                    return 0;
                }
            }
            Collections.sort(posts, new HighToLowComparator());
        }
        else if(s.equals("Buying")){
            //iterate through each post in the list
            Iterator<Post> it = posts.iterator();
            while (it.hasNext()) {
                Post post = it.next();

                //if the post is marked selling, take it out of the list
                if (post.getSelling()) {
                    it.remove();
                }
            }
        }
        else if(s.equals("Selling")){
            //iterate through each post in the list
            Iterator<Post> it = posts.iterator();
            while (it.hasNext()) {
                Post post = it.next();

                //if the post is not marked selling, take it out of the list
                if (!post.getSelling()) {
                    it.remove();
                }
            }
        }

        notifyDataSetChanged();
    }

    /**
     * Filter List of items
     * @return
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    /**
     * Holder for post object
     */
    public class ViewHolder {
        TextView title;
        TextView description;
        TextView price;
        TextView category;
        TextView postStatusFeed;
        TextView timeago;
        ImageView image;
    }

    /**
     * Class used to search in list
     */
    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence != null && charSequence.length() != 0){
                //Convert to uppercase
                charSequence = charSequence.toString().toUpperCase();
                ArrayList<Post> filters = new ArrayList<>();

                for(int i = 0; i < filterList.size(); i++){
                    if(filterList.get(i).getProductName().toUpperCase().contains(charSequence)){
                        filters.add(filterList.get(i));
                    }
                }
                results.count = filters.size();
                results.values=filters;
            }
            else{
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults.values!=null) {
                posts = (ArrayList<Post>) filterResults.values;
            }
            notifyDataSetChanged();
        }
    }

    /**
     * Task that uses searchBar to update adapter
     */
    private class SearchTask extends AsyncTask<String, Object, ArrayList<Post>>{
        private ProgressDialog dialog=new ProgressDialog(context);
        @Override
        protected ArrayList<Post> doInBackground(String... params) {
            try {
                ArrayList<Post> filteredPosts = Server.searchPostTags(params[0]);
                return filteredPosts;
            }
            catch(IOException e){
                Log.d("DEBUG", e.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Post> result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(result!=null){
                posts=result;
            }
            notifyDataSetChanged();
        }
    }




}
