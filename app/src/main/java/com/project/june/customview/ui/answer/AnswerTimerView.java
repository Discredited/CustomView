package com.project.june.customview.ui.answer;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.6.0
 * @description 答题计时器
 * @time 2019/8/20 10:34
 */
public class AnswerTimerView extends ConstraintLayout {

    private AnswerTimeCountView answerTimeCount;
    private AppCompatTextView answerSubject;
    private AppCompatTextView answerPonit;

    private int timeCurrent;
    private int timeCount;

    public AnswerTimerView(Context context) {
        this(context, null);
    }

    public AnswerTimerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.widget_answer_timer_view, this, true);
        answerTimeCount = view.findViewById(R.id.answer_time_count);
        answerSubject = view.findViewById(R.id.answer_subject);
        answerPonit = view.findViewById(R.id.answer_point);

        timeCurrent = 0;
        timeCount = 10;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        answerTimeCount.setTimer(timeCurrent, timeCount);
    }

    public void setAnswerTimeCountListener(AnswerTimeCountListener listener) {
        answerTimeCount.setAnswerTimeCountListener(listener);
    }

    public void startTimer() {
        answerTimeCount.startTimer();
    }

    public void resumeTime() {
    }

    public void pauseTimer() {

    }

    public void stopTimer() {
    }
}