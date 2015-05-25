package com.sleepy.smsApp;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

public class MySimpleArrayAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private final Object[] values;

    public MySimpleArrayAdapter(Context context, Object[] values) {
        super(context, R.layout.row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView smsNum = (TextView) rowView.findViewById(R.id.lblNumber);
        TextView smsBody = (TextView) rowView.findViewById(R.id.lblMsg);

        Pair<String,String> pair = (Pair<String,String>)values[position];
        smsNum.setText(pair.first);
        smsBody.setText(pair.second);
        // change the icon for Windows and iPhone
        return rowView;
    }
}