package com.example.facebook_volley.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.facebook_volley.FeedImageView;
import com.example.facebook_volley.R;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.FeedItem;
import com.example.facebook_volley.data.RequestItem;

import java.util.List;

/**
 * Created by PHI LONG on 17/06/2015.
 */
public class RequestAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RequestItem> requestItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public RequestAdapter(Activity activity, List<RequestItem> requestItems) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.request_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        final TextView xoa = (TextView) convertView.findViewById(R.id.xoa);
        final TextView xacnhan = (TextView) convertView.findViewById(R.id.xacnhan);
        final TextView banbe = (TextView) convertView.findViewById(R.id.banbe);

        TextView name = (TextView) convertView.findViewById(R.id.name);

        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);

        final RequestItem item = requestItems.get(position);

        name.setText(item.getName());

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestItems.remove(position);
                notifyDataSetChanged();

            }
        });

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoa.setVisibility(View.GONE);
                xacnhan.setVisibility(View.GONE);
                banbe.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }
}
