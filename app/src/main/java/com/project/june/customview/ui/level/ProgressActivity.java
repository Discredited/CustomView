package com.project.june.customview.ui.level;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;

import com.project.june.customview.R;

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
