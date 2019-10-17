package com.project.june.customview.ui.answer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.6.0
 * @description 答题
 * @time 2019/8/20 10:32
 */
public class AnswerRushActivity extends AppCompatActivity implements View.OnClickListener {

    private AnswerTimerView answerTimer;

    public static void start(Context context) {
        Intent starter = new Intent(context, AnswerRushActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_rush);

        answerTimer = findViewById(R.id.answer_timer);

        findViewById(R.id.timer_start).setOnClickListener(this);
        findViewById(R.id.timer_end).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timer_start:
                answerTimer.startTimer();
                break;
            case R.id.timer_end:
                answerTimer.pauseTimer();
                break;
        }
    }
}
