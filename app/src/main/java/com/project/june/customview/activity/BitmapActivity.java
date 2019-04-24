package com.project.june.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.project.june.customview.R;

import java.io.File;
import java.util.Locale;

public class BitmapActivity extends AppCompatActivity {

    private AppCompatImageView imageView, imageView2;
    private AppCompatTextView textView;

    public static void startThis(Context context) {
        Intent intent = new Intent(context, BitmapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        imageView = findViewById(R.id.image_view);
        imageView2 = findViewById(R.id.image_view_two);
        textView = findViewById(R.id.text_view);

        BitmapFactory.Options options = new BitmapFactory.Options();
        // bitmap图片复用，这个属性必须设置；
        //options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
        int byteCount = bitmap.getByteCount();

        imageView.setImageBitmap(bitmap);

        textView.append("bitmap地址: " + bitmap);
        textView.append("\n");
        textView.append("bitmap:占用的内存 " + getSourceSize(byteCount));
        textView.append("\n");
        textView.append("宽:" + bitmap.getWidth());
        textView.append("\n");
        textView.append("高:" + bitmap.getHeight());
        textView.append("\n");
        textView.append("inTargetDensity:" + options.inTargetDensity);
        textView.append("\n");
        textView.append("inDensity:" + options.inDensity);

        Glide.with(this).downloadOnly().load(R.drawable.avatar).into(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                long length = resource.length();
                textView.append("\n");
                textView.append("\n");
                textView.append("File:文件大小" + getSourceSize(length));
            }
        });

        textView.append("\n");
        textView.append("\n");

        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inDensity = 320;  //资源文件夹密度
        scaleOptions.inTargetDensity = 240;  //手机密度
        scaleOptions.inSampleSize = 2;
        Bitmap scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar, scaleOptions);
        imageView2.setImageBitmap(scaleBitmap);

        textView.append("scaleBitmap地址: " + scaleBitmap);
        textView.append("\n");
        textView.append("scaleBitmap:占用的内存 " + getSourceSize(scaleBitmap.getByteCount()));
        textView.append("\n");
        textView.append("宽:" + scaleBitmap.getWidth());
        textView.append("\n");
        textView.append("高:" + scaleBitmap.getHeight());
        textView.append("\n");
        textView.append("inTargetDensity:" + scaleOptions.inTargetDensity);
        textView.append("\n");
        textView.append("inDensity:" + scaleOptions.inDensity);
    }

    public String getSourceSize(long byteLength) {
        if (byteLength == 0) {
            return "0";
        }
        if (byteLength < 1024) {
            return String.valueOf(byteLength + "B");
        }
        long m = byteLength / 1024 / 1024;
        if (m <= 0) {
            return String.format(Locale.CHINA, "%.2fK", (byteLength * 1f / 1024));
        }
        return String.format(Locale.CHINA, "%.2fM", byteLength * 1f / 1024 / 1024);
    }
}
