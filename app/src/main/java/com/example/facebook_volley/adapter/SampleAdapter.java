package com.example.facebook_volley.adapter;

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
import com.example.facebook_volley.data.FeedItem;

import java.util.List;

/**
 * Created by PHI LONG on 03/07/2015.
 */
public class SampleAdapter extends BaseAdapter {
    NetworkImageView profilePic;
    TextView title;
    private List<FeedItem> feedItems;
    Context context;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SampleAdapter(Context context, List<FeedItem> feedItems) {
        this.context = context;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
        // profilePic.setImageResource(getItem(position).iconRes);

        title = (TextView) convertView.findViewById(R.id.row_title);
        //title.setText(getItem(position).tag);

        final FeedItem item = feedItems.get(position);

        title.setText(item.getName());
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        return convertView;
    }
}
