package com.project.june.customview.ui.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
