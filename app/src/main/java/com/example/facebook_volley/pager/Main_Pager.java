package com.example.facebook_volley.pager;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.facebook_volley.adapter.ViewPagerAdapter;
import com.example.facebook_volley.friend_menu.SampleListFragment;
import com.example.facebook_volley.widget.BadgeView;
import com.example.facebook_volley.widget.SlidingTabLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.example.facebook_volley.friend_menu.BaseActivity;
import com.example.facebook_volley.R;

public class Main_Pager extends BaseActivity{
    public Main_Pager() {
        super(R.string.app_name);
    }
    SearchView searchView;
    ViewPager viewPager;
    SlidingTabLayout tabs;
    private int b;
    BadgeView badge1,badge2,badge3;
    ViewPagerAdapter pagerAdapter;

    int icons[] = {R.drawable.bg_newfeed, R.drawable.bg_request, R.drawable.bg_messenger, R.drawable.bg_notifi, R.drawable.bg_more};
    String titles[] = {"New Feed", "Request", "Messenger", "Notification", "More"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        getSlidingMenu().setMode(SlidingMenu.RIGHT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout) findViewById(R.id.tab);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), icons);
        viewPager.setAdapter(pagerAdapter);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActionBar().setTitle(titles[position]);
                if (position == 1) {
                    badge1.hide();
                }
                if (position == 2) {
                    badge2.hide();
                }
                if (position == 3) {
                    badge3.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.custom_tabs_layout, 0);
        tabs.setViewPager(viewPager);

        badge1 = new BadgeView(this,
                tabs.getTabStrip().getChildAt(1));
        badge1.setText("7");
        badge1.show();

        badge2 = new BadgeView(this,
                tabs.getTabStrip().getChildAt(2));
        badge2.setText("21");
        badge2.show();

        badge3 = new BadgeView(this,
                tabs.getTabStrip().getChildAt(3));
        badge3.setText("54");
        badge3.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearch.getActionView();
       // searchView.setOnQueryTextListener(this);
       // return true;

        MenuItem item = menu.findItem(R.id.action_frend);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menu_frame_two, new SampleListFragment())
                        .commit();
                getSlidingMenu().getContext();
                getSlidingMenu().showMenu();

                return true;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_frend:
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//
////        if(TextUtils.isEmpty(newText)){
////            adapter.getFilter().filter("");
////            listSearch.clearTextFilter();
////        }else {
////            adapter.getFilter().filter(newText.toString());
////        }
//        return false;
//    }
}
