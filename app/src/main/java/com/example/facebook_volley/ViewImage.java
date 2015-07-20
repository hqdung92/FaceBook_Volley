package com.example.facebook_volley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.ImageLoader;

import com.example.facebook_volley.app.AppController;

public class ViewImage extends Activity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    FeedImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom);
        imageView1 = (FeedImageView) findViewById(R.id.feedImage1);

        Intent callIntent = getIntent();
        Bundle callBundle = callIntent.getBundleExtra("package");
        imageView1.setImageUrl(callBundle.getString("img"), imageLoader);
    }
}