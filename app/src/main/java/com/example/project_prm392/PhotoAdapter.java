package com.example.project_prm392;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.Photo;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private List<Photo> photoList;

    public PhotoAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Photo photo = photoList.get(position);


        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, container, false);


        ImageView imageView = view.findViewById(R.id.img_photo);
        imageView.setImageResource(photo.getResourceId());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
