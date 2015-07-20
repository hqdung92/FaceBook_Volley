package com.example.facebook_volley;

import android.os.Bundle;

import com.example.facebook_volley.friend_menu.BaseActivity;
import com.example.facebook_volley.friend_menu.SampleListFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class FriendMenu extends BaseActivity {
    public FriendMenu() {
        super(R.string.app_name);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSlidingMenu().setMode(SlidingMenu.RIGHT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        setContentView(R.layout.activity_main);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layout1, new SampleListFragment())
//                .commit();

        getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
        //  getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame_two, new SampleListFragment())
                .commit();
    }

}
