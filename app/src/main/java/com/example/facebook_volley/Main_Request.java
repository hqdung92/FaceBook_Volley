package com.example.facebook_volley;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facebook_volley.customer.QuickReturnListView;
import com.example.facebook_volley.customer.PullToRefreshListView;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.facebook_volley.adapter.RequestAdapter;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.RequestItem;

import android.view.ViewTreeObserver;
import android.os.Build;
import android.os.AsyncTask;
import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.animation.TranslateAnimation;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by PHI LONG on 17/06/2015.
 */
public class Main_Request extends Fragment {
    private static final String TAG = Main_Request.class.getSimpleName();
    private ListView listView;
    private RequestAdapter listAdapter;
    private List<RequestItem> requestItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    private LinearLayout mQuickReturnView;
    private int mQuickReturnHeight;
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;

    private TranslateAnimation anim;

    //  ImageView feed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.request_activity, container, false);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.request_activity);

        listView = (ListView) v.findViewById(R.id.list);
        mQuickReturnView = (LinearLayout) v.findViewById(R.id.footer_request);
        //feed = (ImageView) v.findViewById(R.id.feed);

//        feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), Main_Feed.class);
//                startActivity(i);
//            }
//        });

        requestItems = new ArrayList<RequestItem>();

        listAdapter = new RequestAdapter(getActivity(), requestItems);
        listView.setAdapter(listAdapter);
       // scrollScreen();

//        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new GetDataTask().execute();
//            }
//        });

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
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
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
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
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                RequestItem item = new RequestItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                item.setProfilePic(feedObj.getString("profilePic"));

                requestItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
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

//    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
//
//        @Override
//        protected String[] doInBackground(Void... params) {
//            // Simulates a background job.
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String[] result) {
//
//            // Call onRefreshComplete when the list has been refreshed.
//            ((PullToRefreshListView) listView).onRefreshComplete();
//
//            super.onPostExecute(result);
//        }
//    }
}
