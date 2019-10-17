package com.project.june.customview.ui.progress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.project.june.customview.R;

public class MochaProgressActivity extends AppCompatActivity {

    private MochaProgressView progressView;

    public static void startThis(Context context) {
        Intent starter = new Intent(context, MochaProgressActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocha_progress);

        progressView = findViewById(R.id.progress_view);

        findViewById(R.id.reset_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.setProgressPercent(0f);
            }
        });
        findViewById(R.id.add_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.setProgressPercent(progressView.getProgressPercent() + 0.12f);
            }
        });
    }
}
