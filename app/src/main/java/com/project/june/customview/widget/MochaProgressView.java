package com.project.june.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.3.0
 * @description 模卡进度View
 * @time 2019/3/27 10:08
 */
public class MochaProgressView extends View {

    private float progressPercent;
    private float radius;

    private float rectStart;
    private float rectEnd;
    private float rectTop;
    private float rectBottom;

    private Paint progressPaint;
    private Paint progressTextPaint;
    private Paint tipTextPaint;

    private String progressString;
    private String tipString;

    public MochaProgressView(Context context) {
        this(context, null);
    }

    public MochaProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MochaProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radius = 200;
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(20);
        progressPaint.setColor(Color.parseColor("#2ADECB"));

        progressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressTextPaint.setTextSize(30);
        progressTextPaint.setColor(Color.WHITE);
        tipTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tipTextPaint.setTextSize(24);
        tipTextPaint.setColor(Color.WHITE);

        progressString = "0%";
        tipString = "模卡生成中...";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rectStart == 0) {
            rectStart = (getWidth() - radius) / 2;
        }
        if (rectEnd == 0) {
            rectEnd = (getWidth() + radius) / 2;
        }

        if (rectTop == 0) {
            rectTop = (getHeight() - radius) / 2;
        }

        if (rectBottom == 0) {
            rectBottom = (getHeight() + radius) / 2;
        }

        progressString = String.format(Locale.CHINA, "%.0f", progressPercent * 100) + "%";

        int sweepAngle = (int) (360 * progressPercent);
        float progressTextX = (getWidth() - progressTextPaint.measureText(progressString)) / 2;
        float progressTextY = (getHeight() + progressTextPaint.descent() - progressTextPaint.ascent()) / 2;
        float tipTextX = (getWidth() - tipTextPaint.measureText(tipString)) / 2;
        float tipTextY = rectBottom + 20 + tipTextPaint.descent() - tipTextPaint.ascent();


        //扇形和弧形也是建立在矩形的基础上的
        //前四个参数都是矩形对应的边的坐标
        //第五个参数 startAngle 扇弧形的起始角度  x轴的正向，即正右的方向，是 0 度的位置  顺时针为正角度，逆时针为负角度
        //第六个参数 sweepAngle 扇弧形划过的角度
        //第七个参数 userCenter 是否连接到圆心  扇形需要连接到圆心 弧形不连接到圆心
        if (sweepAngle > 0) {
            canvas.drawArc(rectStart, rectTop, rectEnd, rectBottom, 0, sweepAngle, false, progressPaint);
        }
        canvas.drawText(progressString, progressTextX, progressTextY, progressTextPaint);
        canvas.drawText(tipString, tipTextX, tipTextY, tipTextPaint);
    }

    public float getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(float percent) {
        if (percent > 1f) {
            percent = 1f;
        }
        this.progressPercent = percent;
        invalidate();
    }
}