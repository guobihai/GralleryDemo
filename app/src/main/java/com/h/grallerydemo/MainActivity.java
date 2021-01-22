package com.h.grallerydemo;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);




        final List<String> stringList = new ArrayList<>();
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587802397685&di=fe5d44189dddec4e7a9fdd3cfe9e2981&imgtype=0&src=http%3A%2F%2Fimg.aiimg.com%2Fuploads%2Fuserup%2F1208%2F02105103D22.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587802397685&di=70b3b5aa141b394947358cbdcae5db08&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2Fday_101220%2F1012201253b7d409dd0575e1fc.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587802397685&di=fe5d44189dddec4e7a9fdd3cfe9e2981&imgtype=0&src=http%3A%2F%2Fimg.aiimg.com%2Fuploads%2Fuserup%2F1208%2F02105103D22.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587802397685&di=70b3b5aa141b394947358cbdcae5db08&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2Fday_101220%2F1012201253b7d409dd0575e1fc.jpg");


        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return stringList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //container.removeViewAt(position);
                //移除滑动过后的view
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                View view = LayoutInflater.from(viewPager.getContext()).inflate(R.layout.item_layout, viewPager, false);

                ImageView imageView = (ImageView) view.findViewById(R.id.img_grallery);
                Glide.with(viewPager.getContext()).load(stringList.get(position)).into(imageView);
                container.addView(view);
                return view;
            }
        };

        viewPager.setAdapter(pagerAdapter);

    }
}
