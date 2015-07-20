package com.example.facebook_volley.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.facebook_volley.R;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.DialogItem;

import java.util.List;

/**
 * Created by PHI LONG on 22/06/2015.
 */
public class DialogAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<DialogItem> dialogItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public DialogAdapter(Context context, List<DialogItem> dialogItems) {
        this.context = context;
        this.dialogItems = dialogItems;
    }
    @Override
    public int getCount() {
        return dialogItems.size();
    }

    @Override
    public Object getItem(int location) {
        return dialogItems.get(location);
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
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_dialog, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name_dialog);
        TextView comment = (TextView) convertView.findViewById(R.id.comment_dialog);

        ImageView profilePic = (ImageView) convertView
                .findViewById(R.id.profilePic);

        DialogItem item = dialogItems.get(position);

        name.setText(item.getName());
        comment.setText(item.getComment());

        profilePic.setImageResource(item.getAvt());

        return convertView;
    }
}
