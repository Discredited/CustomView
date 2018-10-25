package com.project.june.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 1.0.0
 * @description 第一个
 * @time 2018/10/25 11:12
 */
public class FirstCustomActivity extends AppCompatActivity {

    public static void startThis(Context context) {
        Intent intent = new Intent(context, FirstCustomActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_custom);
    }
}
