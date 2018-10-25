package com.project.june.customview.first;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.0.0
 * @description 第一个自定义View  Paint的设置
 * @time 2018/10/25 10:47
 */
public class CustomFirstView extends View {

    private Paint paint;
    private Paint textPaint;

    public CustomFirstView(Context context) {
        this(context, null);
    }

    public CustomFirstView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFirstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//开启抗锯齿
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.text_size_10));
    }

    //只重写 onDraw()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制View的填充颜色
        canvas.drawColor(Color.parseColor("#2adecb"));

        //画一个圆
        //第一个参数:圆心点的x坐标
        //第二个参数:圆心点的y坐标
        //第三个参数:半径
        //第三个参数: paint(画笔颜料)
        String drawCircle = "画一个圆";
        canvas.drawText(drawCircle, 50, 25, textPaint);
        canvas.drawCircle(100, 100, 50, paint);

        //画一个矩形
        //第一种方式
        //先创建一个RectF，然后执行绘制
        //不建议在onDraw()方法中执行new的对象创建 对象的内存空间分配过程会影响绘制的流畅度
        //不建议不是不允许  只是要尽量避免
        //canvas.drawRect(new RectF(200, 50, 400, 150), paint);
        //第二种方式
        String drawRect = "画一个矩形";
        canvas.drawText(drawRect, 200, 25, textPaint);
        canvas.drawRect(200, 50, 400, 150, paint);

        //绘制View的填充颜色
        //绘制先后顺序的重要性
        //如果drawColor()在这里执行，那么前面的drawCircle()和drawRect()都会被覆盖
        //canvas.drawColor(Color.parseColor("#2adecb"));
    }
}
