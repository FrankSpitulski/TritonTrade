package com.tmnt.tritontrade;

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

    public CustomAdapter(Context context, ArrayList<Post> posts) {
        this.posts=posts;
        this.filterList=posts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position){
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

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
        //postHolder.name = (TextView) catView.findViewById(R.id.textList);
        //postHolder.image = (ImageView) catView.findViewById(R.id.imageList);
        postHolder.name.setText(posts.get(position).getProductName());
        new DownloadImageTask(postHolder.image)
                .execute(posts.get(position).getPhotos().get(0));

        //Click listener for post
        catView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Create_Post.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("category", posts.get(position));
                ((Activity)context).startActivityForResult(i, 1);

            }
        });
        return catView;
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
