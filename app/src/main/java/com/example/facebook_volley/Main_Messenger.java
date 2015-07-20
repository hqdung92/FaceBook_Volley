package com.example.facebook_volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.facebook_volley.adapter.ChatArrayAdapter;
import com.example.facebook_volley.adapter.MessengerAdapter;
import com.example.facebook_volley.adapter.RequestAdapter;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.FeedItem;
import com.example.facebook_volley.data.RequestItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PHI LONG on 18/06/2015.
 */
public class Main_Messenger extends Fragment {

    private static final String TAG = Main_Messenger.class.getSimpleName();
    private ListView listView;
    private MessengerAdapter listAdapter;
    private List<RequestItem> requestItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    //  ImageView feed;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.messenger_activity, container, false);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.request_activity);

        listView = (ListView) v.findViewById(R.id.list);

        //feed = (ImageView) v.findViewById(R.id.feed);

//        feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), Main_Feed.class);
//                startActivity(i);
//            }
//        });

        requestItems = new ArrayList<RequestItem>();

        listAdapter = new MessengerAdapter(getActivity(), requestItems);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(view.getContext(), ChatBubbleActivity.class);
//                startActivity(i);
                final RequestItem item = requestItems.get(position);

                Intent intent = new Intent(view.getContext(), ChatBubbleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", item.getName());
                intent.putExtra("package", bundle);
                view.getContext().startActivity(intent);
            }
        });

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
}
