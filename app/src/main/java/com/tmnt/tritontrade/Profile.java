package com.tmnt.tritontrade;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();

        String[] headers = new String[10];
        String[] data = new String[10];

        for(int i = 0; i < headers.length; i++){

            headers[i] = "Product Title " + i ;
            data[i] = "Description of product. This is going to probably be a multiple line " +
                    "description. What were to happen if I added more lines to this shit? " +
                    "Description # " + i;

        }

        list.setAdapter(new yourAdapter(this, headers, data));
    }


    class yourAdapter extends BaseAdapter {

        Context context;
        String[] head;
        String[] data;
        private LayoutInflater inflater = null;

        public yourAdapter(Context context, String[] header, String[] data) {
            this.context = context;
            this.data = data;
            this.head = header;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.row, null);
            TextView text = (TextView) vi.findViewById(R.id.description);
            TextView header = (TextView) vi.findViewById(R.id.head);
            header.setText(head[position]);
            text.setText(data[position]);
            return vi;
        }
    }
}
