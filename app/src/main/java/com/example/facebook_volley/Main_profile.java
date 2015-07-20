package com.example.facebook_volley;

import com.example.facebook_volley.adapter.FeedListAdapter;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.FeedItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.facebook_volley.pager.Main_PagerAlbum;

import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

public class Main_profile extends Activity {

    SearchView searchView;
    private static final String TAG = Main_profile.class.getSimpleName();
    private ListView listView;
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

//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.more_activity, container, false);

    //    }
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);

        mQuickReturnView = (LinearLayout) findViewById(R.id.footer);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        //request = (ImageView) v.findViewById(R.id.request);

//        header = inflater.inflate(R.layout.header_canhan,null);
//        listView.addHeaderView(header);
        header = View.inflate(this, R.layout.header_canhan, null);
        listView.addHeaderView(header);

        listAdapter = new FeedListAdapter(Main_profile.this, feedItems);
        listView.setAdapter(listAdapter);

        RelativeLayout trangthai = (RelativeLayout) header.findViewById(R.id.rl_trangthai);
        trangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Main_profile.this, R.style.FullHeightDialog);
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

        RelativeLayout photo = (RelativeLayout) header.findViewById(R.id.rl_anh);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Main_profile.this, R.style.FullHeightDialog);
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

        LinearLayout album = (LinearLayout) header.findViewById(R.id.ln_anh);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main_profile.this, Main_PagerAlbum.class);
                startActivity(in);
            }
        });

        RelativeLayout click_anhbia = (RelativeLayout) header.findViewById(R.id.rl1);
        click_anhbia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Main_profile.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_dialog_edit);


                dialog.show();
            }
        });

        RelativeLayout click_anhcanhan = (RelativeLayout) header.findViewById(R.id.ln1);
        click_anhcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Main_profile.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_dialog_edit);


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

//        return v;
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */

//    public void scrollScreen() {
//
//        // bat dau cho code Quick Return
//
//        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mQuickReturnHeight = mQuickReturnView.getHeight();
//                listView.computeScrollY();
//            }
//        });
//
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//
//                mScrollY = 0;
//                int translationY = 0;
//
//                if (listView.scrollYIsComputed()) {
//                    mScrollY = listView.getComputedScrollY();
//                }
//
//                int rawY = mScrollY;
//
//                switch (mState) {
//                    case STATE_OFFSCREEN:
//                        if (rawY >= mMinRawY) {
//                            mMinRawY = rawY;
//                        } else {
//                            mState = STATE_RETURNING;
//                        }
//                        translationY = rawY;
//                        break;
//
//                    case STATE_ONSCREEN:
//                        if (rawY > mQuickReturnHeight) {
//                            mState = STATE_OFFSCREEN;
//                            mMinRawY = rawY;
//                        }
//                        translationY = rawY;
//                        break;
//
//                    case STATE_RETURNING:
//
//                        translationY = (rawY - mMinRawY) + mQuickReturnHeight;
//
//                        System.out.println(translationY);
//                        if (translationY < 0) {
//                            translationY = 0;
//                            mMinRawY = rawY + mQuickReturnHeight;
//                        }
//
//                        if (rawY == 0) {
//                            mState = STATE_ONSCREEN;
//                            translationY = 0;
//                        }
//
//                        if (translationY > mQuickReturnHeight) {
//                            mState = STATE_OFFSCREEN;
//                            mMinRawY = rawY;
//                        }
//                        break;
//                }
//
//                /** this can be used if the build is below honeycomb **/
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
//                    anim = new TranslateAnimation(0, 0, translationY,
//                            translationY);
//                    anim.setFillAfter(true);
//                    anim.setDuration(0);
//                    mQuickReturnView.startAnimation(anim);
//                } else {
//                    mQuickReturnView.setTranslationY(translationY);
//                }
//
//            }
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//        });
//
//        // het cho Quick Return
//    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearch.getActionView();

        MenuItem item = menu.findItem(R.id.action_frend);

        return true;
    }
}
