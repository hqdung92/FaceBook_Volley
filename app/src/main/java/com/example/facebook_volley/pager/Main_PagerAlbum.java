package com.example.facebook_volley.pager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.facebook_volley.Albums_photo.SlidingTabAlbum;
import com.example.facebook_volley.R;
import com.example.facebook_volley.adapter.ViewPageAlbumAdapter;
import com.example.facebook_volley.adapter.ViewPagerAdapter;
import com.example.facebook_volley.friend_menu.SampleListFragment;

/**
 * Created by PHI LONG on 14/07/2015.
 */
public class Main_PagerAlbum extends FragmentActivity {
    SearchView searchView;
    ViewPager viewPager;
    private int a;
    SlidingTabAlbum tabs;
    ViewPageAlbumAdapter pagerAdapter;
    CharSequence Titles[]={"PHOTOS OF YOU","UPLOAD","ALBUM","SYNCED"};
    int Numboftabs =4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_album);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));

        pagerAdapter =  new ViewPageAlbumAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabAlbum) findViewById(R.id.tab);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabAlbum.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.link);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(viewPager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_photo);
        searchView = (SearchView) itemSearch.getActionView();
        // searchView.setOnQueryTextListener(this);
        // return true;

//        MenuItem item = menu.findItem(R.id.action_frend);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.menu_frame_two, new SampleListFragment())
//                        .commit();
//
//                return true;
//            }
//        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
        }
        return super.onOptionsItemSelected(item);
    }
}
