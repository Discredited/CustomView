package com.project.june.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.project.june.customview.R;
import com.project.june.customview.utils.glide.AnyCornerRoundCrop;
import com.project.june.customview.utils.glide.TransformationUtils;

public class GlideActivity extends AppCompatActivity {

    public static void startThis(Context context) {
        Intent starter = new Intent(context, GlideActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        AppCompatImageView imageView = findViewById(R.id.cover_image);


        Glide.with(this)
                .load("http://base-1257102033.piccd.myqcloud.com/demand/adorablecover/20190215/1550224337_35e43227315541ba980b35f777c381ad.jpg")
                .transition(DrawableTransitionOptions.withCrossFade(2000))
                .thumbnail(Glide.with(this).load(R.drawable.img_placeholder_square))
                .apply(new RequestOptions().skipMemoryCache(true).transform(new AnyCornerRoundCrop(TransformationUtils.LEFT, 20)))
                .into(imageView);
    }
}
