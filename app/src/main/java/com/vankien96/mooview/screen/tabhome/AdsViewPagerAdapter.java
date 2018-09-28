package com.vankien96.mooview.screen.tabhome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vankien96.mooview.R;

import java.util.List;

/**
 * Created by Admin on 12/26/17.
 */

public class AdsViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mListAds;
    private LayoutInflater mInflater;

    public AdsViewPagerAdapter(Context context, List<String> listAds) {
        mContext = context;
        mListAds = listAds;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.ads_item, container, false);
        ImageView imageAds = view.findViewById(R.id.image_ads);
        Glide.with(mContext).load(mListAds.get(position)).into(imageAds);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mListAds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
