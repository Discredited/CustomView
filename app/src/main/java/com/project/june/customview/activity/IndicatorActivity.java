package com.project.june.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.project.june.customview.R;

public class IndicatorActivity extends AppCompatActivity {

    public static void startThis(Context context) {
        Intent starter = new Intent(context, IndicatorActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        RoundedImageView imageView = findViewById(R.id.cover_image);


        Glide.with(this)
                .load("http://base-1257102033.piccd.myqcloud.com/demand/adorablecover/20190215/1550224337_35e43227315541ba980b35f777c381ad.jpg")
                .apply(new RequestOptions().skipMemoryCache(true))
                .transition(DrawableTransitionOptions.withCrossFade(2000))
                .thumbnail(Glide.with(this).load(R.drawable.img_placeholder_square))
                .into(imageView);
    }
}
