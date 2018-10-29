package com.project.june.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.june.customview.R;

public class SecondCustomActivity extends AppCompatActivity {

    public static void startThis(Context context) {
        Intent intent = new Intent(context, SecondCustomActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_custom);
    }
}
