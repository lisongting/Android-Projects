package cn.ssdut.lst.uieffects;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */

public class CustomViewPagerActivity extends Activity {
    private CustomViewPager customViewPager;
    private int[] images = new int[]{R.drawable.guide_image1,R.drawable.guide_image2,R.drawable.guide_image3};
    private List<ImageView> mImages = new ArrayList<>();
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.custom_guide_page);
        customViewPager = (CustomViewPager) findViewById(R.id.id_viewpager3);

        customViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(CustomViewPagerActivity.this);
                imageView.setImageResource(images[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mImages.add(imageView);

                //初始化customViewPager中的Map
                customViewPager.setViewForPosition(imageView,position);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //移除
                container.removeView(mImages.get(position));
                customViewPager.removeFromPosition(position);
            }

            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
    }

}
