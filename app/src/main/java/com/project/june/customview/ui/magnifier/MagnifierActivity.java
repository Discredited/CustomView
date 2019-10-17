package com.project.june.customview.ui.magnifier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.june.customview.R;

public class MagnifierActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MagnifierActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifier);

        final MagnifierView mv = new MagnifierView.Builder(this)
                .intiLT(100, 200)
                .viewWH(320, 320)
                .scale(2f)
                .alpha(16)
                .color("#ff00ff")
                .build();

        mv.startViewToRoot();
    }
}
