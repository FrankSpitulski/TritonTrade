package com.tmnt.tritontrade.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmnt.tritontrade.R;

/**
 * Created by nikolasn97 on 2/23/17.
 */

class ProfileListAdaptor extends BaseAdapter {

    Context context;
    String[] head;
    String[] data;
    private LayoutInflater inflater = null;

    public ProfileListAdaptor(Context context, String[] header, String[] data) {
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
        TextView header = (TextView) vi.findViewById(R.id.title);
        header.setText(head[position]);
        text.setText(data[position]);
        return vi;
    }
}
