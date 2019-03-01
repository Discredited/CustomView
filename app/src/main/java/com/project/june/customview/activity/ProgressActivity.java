package com.project.june.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.project.june.customview.R;
import com.project.june.customview.widget.LevelProgressView;

public class ProgressActivity extends AppCompatActivity {

    private LevelProgressView progressView;
    private AppCompatButton levelAdd;

    public static void startThis(Context context) {
        Intent intent = new Intent(context, ProgressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        progressView = findViewById(R.id.level_progress);
        levelAdd = findViewById(R.id.level_add);
        progressView.setMaxLevel(10);
        progressView.setProgress(0,100,0);

        levelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.addProgress(45);
            }
        });
    }
}
