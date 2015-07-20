package com.example.facebook_volley.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.facebook_volley.R;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.RequestItem;

import java.util.List;

/**
 * Created by PHI LONG on 18/06/2015.
 */
public class MessengerAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RequestItem> requestItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public MessengerAdapter(Activity activity, List<RequestItem> requestItems) {
        this.activity = activity;
        this.requestItems = requestItems;
    }
    @Override
    public int getCount() {
        return requestItems.size();
    }

    @Override
    public Object getItem(int location) {
        return requestItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.messenger_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView mess = (TextView) convertView.findViewById(R.id.tv_mess);

        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);

        RequestItem item = requestItems.get(position);

        name.setText(item.getName());

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        return convertView;
    }
}
