package com.project.june.customview.ui.answer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.6.0
 * @description 答题时间计数
 * @time 2019/8/20 10:52
 */
public class AnswerTimeCountView extends View {

    private int timeCount;  //倒计时总时长
    private int timeCurrent; //倒计时当前时间
    private int progressCurrent;
    private int progressCount;

    private int timeTextColor;
    private int timeProgressColor;

    private int timeProgressStrokeWidth;

    private Paint timeTextPaint;
    private Paint timeProgressPaint;

    private int rectLeft;
    private int rectTop;
    private int rectRight;
    private int rectBottom;
    private ObjectAnimator progressAnimator;

    private AnswerTimeCountListener answerTimeCountListener;

    public AnswerTimeCountView(Context context) {
        this(context, null);
    }

    public AnswerTimeCountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerTimeCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        timeCount = 10;
        timeCurrent = 0;

        progressCurrent = 360;
        progressCount = 360;

        timeProgressStrokeWidth = 10;

        timeTextColor = Color.parseColor("#19247D");
        timeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timeTextPaint.setColor(timeTextColor);
        timeTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        timeTextPaint.setTextSize(56f);

        timeProgressColor = Color.parseColor("#FA3F79");
        timeProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timeProgressPaint.setColor(timeProgressColor);
        timeProgressPaint.setStyle(Paint.Style.STROKE);
        timeProgressPaint.setStrokeWidth(timeProgressStrokeWidth);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public int getProgressCurrent() {
        return progressCurrent;
    }

    public void setProgressCurrent(int progressCurrent) {
        this.progressCurrent = progressCurrent;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String drawText = String.valueOf(timeCount - timeCurrent);
        float drawTextStart = (getWidth() - timeTextPaint.measureText(drawText)) / 2;
        float drawTextTop = (getHeight() + timeTextPaint.descent() - timeTextPaint.ascent()) / 2 - 10;

        if (rectLeft == 0) {
            rectLeft = timeProgressStrokeWidth / 2;
        }
        if (rectTop == 0) {
            rectTop = timeProgressStrokeWidth / 2;
        }
        if (rectRight == 0) {
            rectRight = getWidth() - timeProgressStrokeWidth / 2;
        }
        if (rectBottom == 0) {
            rectBottom = getHeight() - timeProgressStrokeWidth / 2;
        }

        //起始位为-90度  起始位置随着时间(实际是progressCurrent)的改变变化
        int startAngle = -90 + (progressCount - progressCurrent);
        canvas.drawArc(rectLeft, rectTop, rectRight, rectBottom, startAngle, progressCurrent, false, timeProgressPaint);
        canvas.drawText(drawText, drawTextStart, drawTextTop, timeTextPaint);
    }

    public void setTimer(int current, int count) {
        timeCurrent = current;
        timeCount = count;
    }

    public void setAnswerTimeCountListener(AnswerTimeCountListener answerTimeCountListener) {
        this.answerTimeCountListener = answerTimeCountListener;
    }

    public void startTimer() {
        clearAnimation();
        if (null == progressAnimator) {
            progressAnimator = ObjectAnimator.ofInt(this, "progressCurrent", progressCount, 0);
            progressAnimator.setDuration(timeCount * 1000);
            progressAnimator.removeAllListeners();
            progressAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.e("sherry", "onAnimationCancel:动画取消");
                    if (null != answerTimeCountListener) {
                        answerTimeCountListener.onTimeCountCancel();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e("sherry", "onAnimationCancel:动画结束");
                    if (null != answerTimeCountListener) {
                        answerTimeCountListener.onTimeCountComplete();
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    Log.e("sherry", "onAnimationCancel:动画开始");
                    if (null != answerTimeCountListener) {
                        answerTimeCountListener.onTimeCountStart();
                    }
                }
            });
            progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    timeCurrent = (int) (animation.getCurrentPlayTime() / 1000);
                    if (null != answerTimeCountListener) {
                        answerTimeCountListener.onTimeCountChange(timeCurrent);
                    }
                }
            });
        } else {
            progressAnimator.cancel();
        }
        progressAnimator.start();
    }
}
