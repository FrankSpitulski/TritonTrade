package com.tmnt.tritontrade.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.tmnt.tritontrade.controller.Post;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by edward on 2/9/17.
 */

public class CustomAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private CustomFilter filter;
    private ArrayList<Post> posts;
    private ArrayList<Post> filterList;
    private LayoutInflater inflater;
    private int count; //Current amount of items displayed
    private int stepNumber; //Amount of items loaded on next display
    private final int startCount=20; //Start amount of items being displayed

    public CustomAdapter(Context context, ArrayList<Post> posts) {
        this.posts=posts;
        this.filterList=posts;
        this.context = context;
        this.count = startCount;
        this.stepNumber=10;
    }

    @Override
    public int getCount() {
        return count; //returns total of items in the list
    }

    public void setCount(int count){
        this.count=count;
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
        TextView name;
        ImageView image;
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
            //TODO Inflate item layout catView = inflater.inflate(R.layout.listcategory, null);
        }
        ViewHolder postHolder = new ViewHolder();

        //TODO set image via url here
        postHolder.name = (TextView) catView.findViewById(R.id.title);
        postHolder.image = (ImageView) catView.findViewById(R.id.row_pic);
        postHolder.name.setText(posts.get(position).getProductName());
        new DownloadImageTask(postHolder.image)
                .execute(posts.get(position).getPhotos().get(0));

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

    /**
     * Filter List of items
     * @return
     */
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
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
            posts = (ArrayList<Post>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    /**
     * Async task to load image from url
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
