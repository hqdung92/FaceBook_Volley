package com.example.facebook_volley.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.facebook_volley.Main_Feed;
import com.example.facebook_volley.Main_Information;
import com.example.facebook_volley.Main_Messenger;
import com.example.facebook_volley.Main_Request;
import com.example.facebook_volley.Main_more;
import com.example.facebook_volley.Main_profile;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int img[];
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(FragmentManager fm, int[] img) {
        super(fm);
        this.img = img;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Main_Feed();
            case 1:
                return new Main_Request();
            case 2:
                return new Main_Messenger();
            case 3:
                return new Main_Information();
            case 4:
                return new Main_more();
        }
        return null;
    }

    @Override
    public int getCount() {
        return img.length;
    }

    public  int getDrawableId(int position){
        return img[position];
    }
}



