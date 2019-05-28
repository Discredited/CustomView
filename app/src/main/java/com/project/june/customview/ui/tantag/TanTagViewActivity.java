package com.project.june.customview.ui.tantag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.june.customview.R;

public class TanTagViewActivity extends AppCompatActivity {

    public static void startThis(Context context) {
        Intent intent = new Intent(context, TanTagViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_tag_view);
    }
}
