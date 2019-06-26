package com.project.june.customview.ui.color;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.june.customview.R;

public class ColorActivity extends AppCompatActivity {

    private ColorPickView colorView;

    public static void startThis(Context context) {
        Intent starter = new Intent(context, ColorActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        colorView = findViewById(R.id.color_selected_view);
        findViewById(R.id.tv_selected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorView.setPicked(true);
            }
        });
        findViewById(R.id.tv_un_selected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorView.setPicked(false);
            }
        });

    }
}
