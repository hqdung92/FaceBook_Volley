package com.example.facebook_volley;

import com.example.facebook_volley.Event.Photo;
import com.example.facebook_volley.Event.Status;
import com.example.facebook_volley.adapter.FeedListAdapter;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.FeedItem;
import com.example.facebook_volley.customer.QuickReturnListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.widget.AbsListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.annotation.SuppressLint;
import android.view.ViewGroup;
import android.os.Build;
import android.view.ViewTreeObserver;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class Main_Feed extends Fragment {
    private static final String TAG = Main_Feed.class.getSimpleName();
    private QuickReturnListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    private LinearLayout mQuickReturnView;
    private int mQuickReturnHeight;
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;
    private View header;
    private TranslateAnimation anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mQuickReturnView = (LinearLayout) v.findViewById(R.id.footer);

        listView = (QuickReturnListView) v.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        LinearLayout stt = (LinearLayout) v.findViewById(R.id.stt);
        LinearLayout pt = (LinearLayout) v.findViewById(R.id.pt);
        LinearLayout lc = (LinearLayout) v.findViewById(R.id.lc);

        //request = (ImageView) v.findViewById(R.id.request);

        header = inflater.inflate(R.layout.new_header,null);
        listView.addHeaderView(header);

        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);
        scrollScreen();

        RelativeLayout status = (RelativeLayout) header.findViewById(R.id.rl1);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_status);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        RelativeLayout status_2 = (RelativeLayout) header.findViewById(R.id.ln);
        status_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_status);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        RelativeLayout photo = (RelativeLayout) header.findViewById(R.id.rl2);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_photo);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        RelativeLayout check = (RelativeLayout) header.findViewById(R.id.rl3);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_check_in);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_status);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
        pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_photo);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        lc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.item_check_in);

                ImageView img_back = (ImageView) dialog.findViewById(R.id.img);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        //getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

        return v;
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */

    public void scrollScreen() {

        // bat dau cho code Quick Return

        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mQuickReturnHeight = mQuickReturnView.getHeight();
                listView.computeScrollY();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @SuppressLint("NewApi")
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                mScrollY = 0;
                int translationY = 0;

                if (listView.scrollYIsComputed()) {
                    mScrollY = listView.getComputedScrollY();
                }

                int rawY = mScrollY;

                switch (mState) {
                    case STATE_OFFSCREEN:
                        if (rawY >= mMinRawY) {
                            mMinRawY = rawY;
                        } else {
                            mState = STATE_RETURNING;
                        }
                        translationY = rawY;
                        break;

                    case STATE_ONSCREEN:
                        if (rawY > mQuickReturnHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                        translationY = rawY;
                        break;

                    case STATE_RETURNING:

                        translationY = (rawY - mMinRawY) + mQuickReturnHeight;

                        System.out.println(translationY);
                        if (translationY < 0) {
                            translationY = 0;
                            mMinRawY = rawY + mQuickReturnHeight;
                        }

                        if (rawY == 0) {
                            mState = STATE_ONSCREEN;
                            translationY = 0;
                        }

                        if (translationY > mQuickReturnHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                        break;
                }

                /** this can be used if the build is below honeycomb **/
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    anim = new TranslateAnimation(0, 0, translationY,
                            translationY);
                    anim.setFillAfter(true);
                    anim.setDuration(0);
                    mQuickReturnView.startAnimation(anim);
                } else {
                    mQuickReturnView.setTranslationY(translationY);
                }

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });

        // het cho Quick Return
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                item.setCountLike(new Random().nextInt(100));
                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
