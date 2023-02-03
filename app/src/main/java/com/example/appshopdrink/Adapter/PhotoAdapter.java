package com.example.appshopdrink.Adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.appshopdrink.R;
import com.example.appshopdrink.TrangchuFragment;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private Context mContext;
    private List<Photo> mListPhoto;

    public PhotoAdapter(Context mContext, List<Photo> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    public PhotoAdapter(TrangchuFragment trangchuFragment, List<Photo> mListPhoto) {
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item,container,false);
        ImageView imgphoto = view.findViewById(R.id.img_photo);
        Photo photo = mListPhoto.get(position);

        if(photo!=null){

            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            Glide.with(mContext).load(photo.getResoureid()).transition(DrawableTransitionOptions.with(factory)).into(imgphoto);
            int a = position+1;

            imgphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){

                        Toast.makeText(mContext.getApplicationContext(), "page 1 ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        container.addView(view);
        return view;
    }




    @Override
    public int getCount() {
        if(mListPhoto!=null){
            return mListPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}