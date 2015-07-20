package com.example.facebook_volley.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facebook_volley.R;
import com.example.facebook_volley.data.MoreItem;

import java.util.ArrayList;

/**
 * Created by PHI LONG on 13/07/2015.
 */
public class MoreAdapter extends BaseAdapter{
    private ArrayList<Object> personArray;
    private LayoutInflater inflater;
    private static final int TYPE_PERSON = 0;
    private static final int TYPE_DIVIDER = 1;

    public MoreAdapter(Context context, ArrayList<Object> personArray) {
        this.personArray = personArray;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return personArray.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return personArray.get(position);
    }

    @Override
    public int getViewTypeCount() {
        // TYPE_PERSON and TYPE_DIVIDER
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof MoreItem) {
            return TYPE_PERSON;
        }

        return TYPE_DIVIDER;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_PERSON);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_PERSON:
                    convertView = inflater.inflate(R.layout.more_item, parent, false);
                    break;
                case TYPE_DIVIDER:
                    convertView = inflater.inflate(R.layout.more_header, parent, false);
                    break;
            }
        }

        switch (type) {
            case TYPE_PERSON:
                MoreItem person = (MoreItem)getItem(position);
                TextView name = (TextView)convertView.findViewById(R.id.name);
                ImageView avata = (ImageView)convertView.findViewById(R.id.avata);
                name.setText(person.getName());
                avata.setImageResource(person.getAvata());
                break;
            case TYPE_DIVIDER:
                TextView title = (TextView)convertView.findViewById(R.id.headerTitle);
                String titleString = (String)getItem(position);
                title.setText(titleString);
                break;
        }

        return convertView;
    }
}
