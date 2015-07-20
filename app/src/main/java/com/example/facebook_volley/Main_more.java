package com.example.facebook_volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.facebook_volley.adapter.MoreAdapter;
import com.example.facebook_volley.data.MoreItem;

import java.util.ArrayList;

/**
 * Created by PHI LONG on 13/07/2015.
 */
public class Main_more extends Fragment{

    private View header1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.more_activity, container, false);

        ListView list = (ListView) v.findViewById(R.id.list);

        header1 = inflater.inflate(R.layout.moreheader,null);
        list.addHeaderView(header1);

        header1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Main_profile.class);
                startActivity(in);
            }
        });

        MoreItem dangxuat = new MoreItem(R.drawable.dangxuat,"Đăng xuất");
        MoreItem dulieu = new MoreItem(R.drawable.dulieu,"Dữ liệu di động");
        MoreItem thongtin = new MoreItem(R.drawable.thongtin,"Thông tin");
        MoreItem dieukhoan = new MoreItem(R.drawable.dieukhoan,"Điều khoản & chính sách");
        MoreItem riengtu = new MoreItem(R.drawable.riengtu,"Đường tắt quyền riêng tư");
        MoreItem nhatky = new MoreItem(R.drawable.nhatky,"Nhật ký hoạt động");
        MoreItem trinhtaoma = new MoreItem(R.drawable.trinhtaoma,"Trình tạo mã");
        MoreItem trungtam = new MoreItem(R.drawable.trungtam,"Trung tâm trợ giúp");
        MoreItem caidat = new MoreItem(R.drawable.caidat,"Cài đặt tài khoản");
        MoreItem ungdung = new MoreItem(R.drawable.caidatungdung,"Cài đặt ứng dụng");

        MoreItem skype = new MoreItem(R.drawable.skype,"Skype");
        MoreItem ytb = new MoreItem(R.drawable.youtube,"Youtube");
        MoreItem pinterest = new MoreItem(R.drawable.pinterest,"Pinterest");
        MoreItem wordpress = new MoreItem(R.drawable.wordpress,"Wordpress");
        MoreItem instagram = new MoreItem(R.drawable.instagram,"Instagram");

        MoreItem twitter = new MoreItem(R.drawable.twitter,"Twitter");
        MoreItem vimeo = new MoreItem(R.drawable.vimeo,"Vimeo");
        MoreItem google = new MoreItem(R.drawable.google,"Google");
        MoreItem github = new MoreItem(R.drawable.github,"Github");

        MoreItem congdong = new MoreItem(R.drawable.dribbble,"Cộng đồng Android Việt Nam");
        MoreItem giaitri = new MoreItem(R.drawable.evernote,"Hội thích thể thao 24h");


        ArrayList<Object> people = new ArrayList<>();

        people.add("Yêu thích");
        people.add(ytb);
        people.add(instagram);
        people.add(vimeo);
        people.add(google);

        people.add("Trang");
        people.add(twitter);
        people.add(vimeo);
        people.add(google);
        people.add(github);

        people.add("Nhóm");
        people.add(giaitri);
        people.add(congdong);


        people.add("Ứng dụng");
        people.add(skype);
        people.add(ytb);
        people.add(instagram);
        people.add(pinterest);
        people.add(wordpress);

        people.add("Cài đặt");
        people.add(ungdung);
        people.add(caidat);
        people.add(trungtam);
        people.add(trinhtaoma);
        people.add(nhatky);
        people.add(riengtu);
        people.add(dieukhoan);
        people.add(thongtin);
        people.add(dulieu);
        people.add(dangxuat);

        list.setAdapter(new MoreAdapter(getActivity(), people));

        return v;
    }

}
