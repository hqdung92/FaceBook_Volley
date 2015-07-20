package com.example.facebook_volley.friend_menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.facebook_volley.ChatBubbleActivity;
import com.example.facebook_volley.R;
import com.example.facebook_volley.adapter.FeedListAdapter;
import com.example.facebook_volley.adapter.SampleAdapter;
import com.example.facebook_volley.app.AppController;
import com.example.facebook_volley.data.FeedItem;
import com.example.facebook_volley.data.RequestItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SampleListFragment extends Fragment {

    private static final String TAG = SampleListFragment.class.getSimpleName();
    NetworkImageView profilePic;
    private List<FeedItem> feedItems;
    ListView listView;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
    private SampleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friend_online_list, container, false);
        //}
        listView = (ListView) v.findViewById(R.id.list);
        //public void onActivityCreated(Bundle savedInstanceState) {
        //	super.onActivityCreated(savedInstanceState);
        feedItems = new ArrayList<FeedItem>();
         adapter = new SampleAdapter(getActivity(), feedItems);
//		for (int i = 0; i < 20; i++) {
//			adapter.add(new SampleItem("Sample List", android.R.drawable.ic_menu_search));
//		}
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final FeedItem item = feedItems.get(position);

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
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                item.setProfilePic(feedObj.getString("profilePic"));

                feedItems.add(item);
            }

            // notify data changes to list adapater
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
//	private class SampleItem {
//		public String tag;
//		public int iconRes;
//		public SampleItem(String tag, int iconRes) {
//			this.tag = tag;
//			this.iconRes = iconRes;
//		}
//	}

//	public class SampleAdapter extends ArrayAdapter<SampleItem> {
//
//		public SampleAdapter(Context context) {
//			super(context, 0);
//		}

//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
//			}
//			 profilePic = (NetworkImageView) convertView.findViewById(R.id.row_icon);
//           // profilePic.setImageResource(getItem(position).iconRes);
//
//			TextView title = (TextView) convertView.findViewById(R.id.row_title);
//			//title.setText(getItem(position).tag);
//
//            final FeedItem item = feedItems.get(position);
//
//            title.setText(item.getName());
//            profilePic.setImageUrl(item.getProfilePic(), imageLoader);
//
//			return convertView;
//		}

        //}
   // }
