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
    private int backgroundColor;
    private int startX = 50, startY = 50;

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
        //Paint.ANTI_ALIAS_FLAG 开启抗锯齿 开启抗锯齿来让图形和文字的边缘更加平滑
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Paint.Style.FILL Paint填充样式-填充满
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.text_size_10));
        backgroundColor = Color.parseColor("#2adecb");
    }

    //只重写 onDraw()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制View的填充颜色
        canvas.drawColor(backgroundColor);
        //canvas.drawRGB(33, 66, 99);  //与drawColor()相同
        //canvas.drawARGB(100,33, 66, 99);  //与drawColor()作用相同，增加了透明度，四个参数的范围都是0~255

        //需要先了解一下View坐标系的分布规则 即x,y轴的起始点
        //坐标系原点为View左上角的那个点  x轴从左往右为正(递增) y轴从上往下为正(递增) y轴与数学坐标系相反 这个很重要
        //坐标系原点为View左上角的那个点  x轴从左往右为正(递增) y轴从上往下为正(递增) y轴与数学坐标系相反 这个很重要
        //坐标系原点为View左上角的那个点  x轴从左往右为正(递增) y轴从上往下为正(递增) y轴与数学坐标系相反 这个很重要

        //画一个圆
        //第一个参数:圆心点的x坐标
        //第二个参数:圆心点的y坐标
        //第三个参数:半径
        //第三个参数: paint(画笔颜料)
        String drawCircle = "画一个圆";
        int circleX = startX * 2;
        int circleY = startX * 2;
        int radius = 50;
        canvas.drawText(drawCircle, startX, 30, textPaint);
        canvas.drawCircle(circleX, circleY, radius, paint);

        //画一个矩形
        //第一种方式
        //先创建一个RectF，然后执行绘制
        //不建议在onDraw()方法中执行new的对象创建 对象的内存空间分配过程会影响绘制的流畅度
        //不建议不是不允许  只是要尽量避免
        //canvas.drawRect(new RectF(200, 50, 400, 150), paint);
        //第二种方式
        String drawRect = "画一个矩形";
        int rectX = circleX * 2;
        int rectY = startY;
        int rectXEnd = rectX + 200;
        int rectYEnd = rectY + 100;
        canvas.drawText(drawRect, rectX, 30, textPaint);
        canvas.drawRect(rectX, rectY, rectXEnd, rectYEnd, paint);

        String drawOval = "画一个椭圆";
        int ovalX = rectXEnd + 50;
        int ovalY = startY;
        int ovalXEnd = ovalX + 150;
        int ovalYEnd = ovalY + 100;
        canvas.drawText(drawOval, ovalX, 30, textPaint);
        canvas.drawOval(ovalX, ovalY, ovalXEnd, ovalYEnd, paint);


        //第二行开始坐标
        int secondLineStartY = startY + 170;
        int secondLineTextStartY = secondLineStartY - 20;

        //画一个点
        String drawPoint = "画一个点";
        int pointX = startX + 50;
        int pointY = secondLineStartY + 50;
        paint.setStrokeWidth(30);
        canvas.drawText(drawPoint, startX, secondLineTextStartY, textPaint);
        canvas.drawPoint(pointX, pointY, paint);

        //画一根线
        String drawLine = "画一根线";
        int lineStartX = rectX;
        int lineStartY = secondLineStartY;
        int lineEndX = lineStartX + 200;
        int lineEndY = lineStartY + 100;
        paint.setStrokeWidth(2);  //重置Paint线条的宽度
        canvas.drawText(drawLine, lineStartX, secondLineTextStartY, textPaint);
        canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paint);

        //绘制View的填充颜色
        //绘制先后顺序的重要性
        //如果drawColor()在这里执行，那么前面的drawCircle()和drawRect()都会被覆盖
        //canvas.drawColor(Color.parseColor("#2adecb"));
    }
}
