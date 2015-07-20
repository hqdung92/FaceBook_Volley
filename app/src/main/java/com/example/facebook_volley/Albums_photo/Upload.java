package com.example.facebook_volley.Albums_photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.facebook_volley.R;
import com.example.facebook_volley.adapter.AlbumAdapter;

/**
 * Created by PHI LONG on 14/07/2015.
 */
public class Upload extends Fragment{

    private ListView listViewLeft;
    private ListView listViewRight;
    private AlbumAdapter leftAdapter;
    private AlbumAdapter rightAdapter;

    int[] leftViewsHeights;
    int[] rightViewsHeights;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.albums_list, container, false);

        listViewLeft = (ListView) v.findViewById(R.id.list_view_left);
        listViewRight = (ListView) v.findViewById(R.id.list_view_right);

        loadItems();

        listViewLeft.setOnTouchListener(touchListener);
        listViewRight.setOnTouchListener(touchListener);
        listViewLeft.setOnScrollListener(scrollListener);
        listViewRight.setOnScrollListener(scrollListener);


        return v;
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        boolean dispatched = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.equals(listViewLeft) && !dispatched) {
                dispatched = true;
                listViewRight.dispatchTouchEvent(event);
            } else if (v.equals(listViewRight) && !dispatched) {
                dispatched = true;
                listViewLeft.dispatchTouchEvent(event);
            }

            dispatched = false;
            return false;
        }
    };

    /**
     * Synchronizing scrolling
     * Distance from the top of the first visible element opposite list:
     * sum_heights(opposite invisible screens) - sum_heights(invisible screens) + distance from top of the first visible child
     */
    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView v, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            if (view.getChildAt(0) != null) {
                if (view.equals(listViewLeft) ){
                    leftViewsHeights[view.getFirstVisiblePosition()] = view.getChildAt(0).getHeight();

                    int h = 0;
                    for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
                        h += rightViewsHeights[i];
                    }

                    int hi = 0;
                    for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
                        hi += leftViewsHeights[i];
                    }

                    int top = h - hi + view.getChildAt(0).getTop();
                    listViewRight.setSelectionFromTop(listViewRight.getFirstVisiblePosition(), top);
                } else if (view.equals(listViewRight)) {
                    rightViewsHeights[view.getFirstVisiblePosition()] = view.getChildAt(0).getHeight();

                    int h = 0;
                    for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
                        h += leftViewsHeights[i];
                    }

                    int hi = 0;
                    for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
                        hi += rightViewsHeights[i];
                    }

                    int top = h - hi + view.getChildAt(0).getTop();
                    listViewLeft.setSelectionFromTop(listViewLeft.getFirstVisiblePosition(), top);
                }

            }

        }
    };

    private void loadItems(){
        Integer[] leftItems = new Integer[]{R.drawable.avata, R.drawable.avata2, R.drawable.avata3, R.drawable.ic_4, R.drawable.avata};
        Integer[] rightItems = new Integer[]{R.drawable.avata3, R.drawable.avata, R.drawable.ic_8, R.drawable.ic_9, R.drawable.avata3};

        leftAdapter = new AlbumAdapter(getActivity(), R.layout.album_item, leftItems);
        rightAdapter = new AlbumAdapter(getActivity(), R.layout.album_item, rightItems);
        listViewLeft.setAdapter(leftAdapter);
        listViewRight.setAdapter(rightAdapter);

        leftViewsHeights = new int[leftItems.length];
        rightViewsHeights = new int[rightItems.length];
    }

}
