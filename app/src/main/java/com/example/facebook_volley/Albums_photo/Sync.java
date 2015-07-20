package com.example.facebook_volley.Albums_photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facebook_volley.R;

/**
 * Created by PHI LONG on 14/07/2015.
 */
public class Sync extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.albums_list, container, false);

        return v;
    }
}
