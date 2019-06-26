package com.project.june.customview.ui.color;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.project.june.customview.R;

public class ColorPickView extends View {

    private int themeColor;
    private int innerPadding;  //内环的间隔
    private int outRadius;  //外环半径
    private int outWidth;  //外环线宽
    private int innerRadius;  //内环半径
    private boolean isPicked;  //是否选中

    private Paint innerPaint;  //绘制整个圆形
    private Paint outPaint;  //绘制外环线框

    public ColorPickView(Context context) {
        this(context, null);
    }

    public ColorPickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorPickView, defStyleAttr, 0);
        try {
            themeColor = array.getColor(R.styleable.ColorPickView_theme_color, ContextCompat.getColor(context, R.color.colorAccent));
            outWidth = array.getDimensionPixelSize(R.styleable.ColorPickView_out_width, 5);
            innerPadding = array.getDimensionPixelSize(R.styleable.ColorPickView_inner_padding, 5);
            isPicked = array.getBoolean(R.styleable.ColorPickView_is_picked, false);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(themeColor);

        outPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(outWidth);
        outPaint.setColor(themeColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);

        outRadius = size / 2;
        innerRadius = outRadius - innerPadding - outWidth;

        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isPicked) {
            //绘制外环线框
            canvas.drawCircle(outRadius, outRadius, outRadius - outWidth, outPaint);
            //绘制内环圆形
            canvas.drawCircle(outRadius, outRadius, innerRadius, innerPaint);
        } else {
            //直接绘制一个圆形
            canvas.drawCircle(outRadius, outRadius, outRadius, innerPaint);
        }
    }

    public void setThemeColor(int color) {
        innerPaint.setColor(color);
        outPaint.setColor(color);
    }

    public void setPicked(boolean isPick) {
        isPicked = isPick;
        invalidate();
    }
}
