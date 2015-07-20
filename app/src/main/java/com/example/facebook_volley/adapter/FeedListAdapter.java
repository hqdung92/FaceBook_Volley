package com.example.facebook_volley.adapter;

import com.example.facebook_volley.FeedImageView;
import com.example.facebook_volley.R;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.DialogItem;
import com.example.facebook_volley.data.FeedItem;
import com.example.facebook_volley.ViewImage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by PHI LONG on 16/06/2015.
 */
public class FeedListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private DialogAdapter listAdapter;
    private ListView listView;
    private Activity activity;
    List<DialogItem> dialogItems = new ArrayList<DialogItem>();


    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);


        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);

        LinearLayout hienthi = (LinearLayout) convertView.findViewById(R.id.ln_hienthi);
        final TextView btn_like = (TextView) convertView.findViewById(R.id.btn_like);
        final TextView btn_dislike = (TextView) convertView.findViewById(R.id.btn_dislike);
        TextView btn_comment = (TextView) convertView.findViewById(R.id.btn_comment);
        TextView countLike = (TextView) convertView.findViewById(R.id.countlike);
        TextView countComment = (TextView) convertView.findViewById(R.id.countComment);
        RelativeLayout bt_share = (RelativeLayout) convertView.findViewById(R.id.bt_share);

        final FeedItem item = feedItems.get(position);

        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity, R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_share);

                dialog.show();
            }
        });

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setLike(true);
                int a = item.getCountLike();
                item.setCountLike(a + 1);
                notifyDataSetChanged();
            }
        });

        btn_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setLike(false);
                int a = item.getCountLike();
                item.setCountLike(a - 1);
                notifyDataSetChanged();
            }
        });
//Show like
        if (item.getLike() == false) {
            btn_dislike.setVisibility(View.INVISIBLE);
            btn_like.setVisibility(View.VISIBLE);
        } else {
            btn_dislike.setVisibility(View.VISIBLE);
            btn_like.setVisibility(View.INVISIBLE);
        }


        name.setText(item.getName());

        //Show countlike
        if (item.getCountLike() > 0) {
            hienthi.setVisibility(View.VISIBLE);
            countLike.setText(item.getCountLike() + " Liked");
        } else {
            hienthi.setVisibility(View.GONE);
            countLike.setText("");
        }
        //show cmt
        if (feedItems.size() > 0) {
            hienthi.setVisibility(View.VISIBLE);
            countComment.setText(feedItems.size() + " Comment");
        } else{
            hienthi.setVisibility(View.GONE);
            countComment.setText("");
        }
        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image
        if (item.getImge() != null) {
            feedImageView.setImageUrl(item.getImge(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }
        feedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewImage.class);
                Bundle bundle = new Bundle();
                bundle.putString("img", item.getImge());
                intent.putExtra("package", bundle);
                activity.startActivity(intent);
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity, R.style.FullHeightDialog);
                dialog.setContentView(R.layout.dialog_cmt);

                final DialogItem itemComment = new DialogItem();
                listAdapter = new DialogAdapter(activity, dialogItems);

                listView = (ListView) dialog.findViewById(R.id.list_dialog);

                itemComment.setName("Chuột Suki");
                itemComment.setAvt(R.drawable.avata);
                itemComment.setComment("12345");
                dialogItems.add(itemComment);
                listView.setAdapter(listAdapter);

                dialog.show();

                final EditText ed_cmt = (EditText) dialog.findViewById(R.id.ed_comment);
                final ImageView img_cmt = (ImageView) dialog.findViewById(R.id.img_comment);
                final TextView likes = (TextView) dialog.findViewById(R.id.likes);
                final ImageView like = (ImageView) dialog.findViewById(R.id.like);
                final ImageView liked = (ImageView) dialog.findViewById(R.id.liked);
                final ImageView exit = (ImageView) dialog.findViewById(R.id.imageView2);

                if (item.getLike() == false) {
                    like.setVisibility(View.VISIBLE);
                    liked.setVisibility(View.INVISIBLE);
                    likes.setText(item.getCountLike() + " others like it");
                } else {
                    like.setVisibility(View.INVISIBLE);
                    liked.setVisibility(View.VISIBLE);
                    likes.setText("You and " + (item.getCountLike() - 1) + " others like it");
                }

                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setLike(true);
                        int a = item.getCountLike();
                        item.setCountLike(a + 1);
                        likes.setText("You and " + (item.getCountLike() - 1) + " other person liked it");
                        like.setVisibility(View.INVISIBLE);
                        liked.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });

                liked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setLike(false);
                        int a = item.getCountLike();
                        item.setCountLike(a - 1);
                        notifyDataSetChanged();
                        like.setVisibility(View.VISIBLE);
                        liked.setVisibility(View.INVISIBLE);
                        likes.setText(item.getCountLike() + " others person liked it");

                    }
                });


                ed_cmt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strUserName = ed_cmt.getText().toString();
                        img_cmt.setVisibility(View.INVISIBLE);
                        if (strUserName.trim().equals("")) {
                            img_cmt.setVisibility(View.INVISIBLE);
                        } else
                            img_cmt.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                img_cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogItem itemComment = new DialogItem();

                        itemComment.setAvt(R.drawable.avata);
                        itemComment.setComment(ed_cmt.getText().toString());
                        itemComment.setName("Suki Bùi");

                        dialogItems.add(itemComment);

                        ed_cmt.setText("");
                        listAdapter.notifyDataSetChanged();
                    }
                });
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });

            }
        });


        return convertView;

    }


}
