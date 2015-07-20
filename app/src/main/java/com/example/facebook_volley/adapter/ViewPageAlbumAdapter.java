package com.example.facebook_volley.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.facebook_volley.Albums_photo.Album;
import com.example.facebook_volley.Albums_photo.Photos;
import com.example.facebook_volley.Albums_photo.Sync;
import com.example.facebook_volley.Albums_photo.Upload;

/**
 * Created by PHI LONG on 14/07/2015.
 */
public class ViewPageAlbumAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPageAlbumAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            Photos photo = new Photos();
            return photo;
        } else if (position == 1)        // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Upload upload = new Upload();
            return upload;
        } else if (position == 2) {
            Album album = new Album();
            return album;
        } else {
            Sync sync = new Sync();
            return sync;
        }

    }
    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
