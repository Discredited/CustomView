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
 * @version 1.0.0
 * @description 第一个自定义View  初识 canvas.drawXXXX()方法  基本图形的绘制以及参数注释
 * <p>
 * 注释的代码都是可用代码  演示不同的绘制方式和重载方法
 * @time 2018/10/25 10:47
 */
public class CustomFirstView extends View {

    private Paint paint;
    private Paint textPaint;
    private int backgroundColor;

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

        int firstStartX = 50;  //第一列开始位置
        int secondStartX = 200;  //第二列开始位置
        int thirdStartX = 450;  //第三列开始位置

        int oneStartY = 50;  //第一行开始位置
        int twoStartY = 250;  //第二行开始位置
        int threeStartY = 400;  //第三行开始位置

        int textDistance = 20;  //文字与行开始位置的距离

        //////////// 第一行
        //画一个圆
        //第一个参数: cx     圆心点的x坐标
        //第二个参数: cy     圆心点的y坐标
        //第三个参数: radius 半径
        //第三个参数: paint  (画笔颜料)
        String drawCircle = "画一个圆";
        int circleX = firstStartX * 2;
        int circleY = firstStartX * 2;
        int radius = 50;
        int firstTextY = oneStartY - textDistance;
        canvas.drawText(drawCircle, firstStartX, firstTextY, textPaint);
        canvas.drawCircle(circleX, circleY, radius, paint);


        //画一个矩形
        String drawRect = "画一个矩形";
        int rectXEnd = secondStartX + 200;
        int rectYEnd = oneStartY + 100;
        canvas.drawText(drawRect, secondStartX, firstTextY, textPaint);
        //第一种方式
        //先创建一个RectF，然后执行绘制
        //第一个参数  left   矩形左边 边的坐标
        //第二个参数  top    矩形顶部 边的坐标
        //第三个参数  right  矩形右部 边的坐标
        //第四个参数  bottom 矩形底部 边的坐标
        //四个参数理解为(left,top)构成了矩形左上角顶点，(right,bottom)构成了右下角顶点
        //这两个顶点就构成了矩形的对角线，结合View的坐标系就确定了矩形的所有坐标点
        //drawRect()不能直接绘制平行四边形 需要几何变换配合
        //不建议在onDraw()方法中执行new的对象创建 对象的内存空间分配过程会影响绘制的流畅度
        //不建议不是不允许  只是要尽量避免
        //canvas.drawRect(new RectF(secondStartX, oneStartY, rectXEnd, rectYEnd), paint);
        //第二种方式
        //参数同上
        canvas.drawRect(secondStartX, oneStartY, rectXEnd, rectYEnd, paint);


        //画一个椭圆
        String drawOval = "画一个椭圆";
        int ovalXEnd = thirdStartX + 150;
        int ovalYEnd = oneStartY + 100;
        canvas.drawText(drawOval, thirdStartX, firstTextY, textPaint);
        //参数就是绘制矩形的参数  不能直接绘制带斜率的椭圆 需要几何变换配合
        canvas.drawOval(thirdStartX, oneStartY, ovalXEnd, ovalYEnd, paint);


        //////////// 第二行
        int secondTextY = twoStartY - textDistance;
        //画一个点
        String drawPoint = "画一个点";
        int pointX = firstStartX + 50;
        int pointY = twoStartY + 50;
        paint.setStrokeWidth(30);  //设置线条的宽度
        canvas.drawText(drawPoint, firstStartX, secondTextY, textPaint);
        //第一个参数： x  点圆心x坐标
        //第二个参数： y  点圆心y坐标
        canvas.drawPoint(pointX, pointY, paint);

        //画三个点(多个点绘制)
        String drawPoints = "画三个点";
        //构造所有点的圆心坐标，两个一对 表示点圆心的x,y
        //如果最后只剩一个数字(数组.length % 2 != 0)最后一个点无法绘制 不会抛出异常 亲测
        float[] points = {300, 280, 270, 320, 330, 320};
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);  //设置点的样式
        canvas.drawText(drawPoints, secondStartX, secondTextY, textPaint);
        //第一个参数 points 点圆心集合
        canvas.drawPoints(points, paint);
        //画多个点还有个一个重载方法
        //第二个参数 offset 表示跳过从数组0开始的多少个元素
        //第三个参数 count  表示要绘制几个数  即(count / 2)个点
        //下面实例表示 跳过(300,280),从270开始绘制四个数字，即绘制(270,320),(330,320)两个点
        //canvas.drawPoints(points, 2, 4, paint);

        //画一根线
        String drawLine = "画一根线";
        int lineEndX = thirdStartX + 200;  // thirdStartX 450
        int lineEndY = twoStartY + 100;  // twoStartY 250
        paint.setStrokeWidth(2);  //重置Paint线条的宽度
        canvas.drawText(drawLine, thirdStartX, secondTextY, textPaint);
        //两点确定一条直线  一个点对应x,y坐标
        canvas.drawLine(thirdStartX, twoStartY, lineEndX, lineEndY, paint);
        //画一堆线条
        //float[] lines = {
        //        thirdStartX, twoStartY, lineEndX, lineEndY,
        //        thirdStartX, lineEndY, lineEndX, twoStartY,
        //        thirdStartX, twoStartY + 50, thirdStartX + 200, twoStartY + 50
        //};
        //canvas.drawLines(lines, paint);
        //和drawPoints()是一个道理，只是画线的步长为4
        //canvas.drawLines(lines, 4, 8, paint);

        //////////// 第三行
        int thirdTextY = threeStartY - textDistance;

        //画一个扇形
        String drawArc = "画一个扇形";
        int arcEndX = firstStartX + 100;
        int arcEndY = threeStartY + 100;
        canvas.drawText(drawArc, firstStartX, thirdTextY, textPaint);
        //扇形和弧形也是建立在矩形的基础上的
        //前四个参数都是矩形对应的边的坐标
        //第五个参数 startAngle 扇弧形的起始角度  x轴的正向，即正右的方向，是 0 度的位置  顺时针为正角度，逆时针为负角度
        //第六个参数 sweepAngle 扇弧形划过的角度
        //第七个参数 userCenter 是否连接到圆心  扇形需要连接到圆心 弧形不连接到圆心
        canvas.drawArc(firstStartX, threeStartY, arcEndX, arcEndY, 0, 240, true, paint);

        //画一个弧形
        String drawArcAngle = "画一个弧形";
        int arcAngleStartX = secondStartX + 50;
        int arcAngleEndX = secondStartX + 150;
        int arcAngleEndY = threeStartY + 100;
        canvas.drawText(drawArcAngle, secondStartX, thirdTextY, textPaint);
        //同上
        canvas.drawArc(arcAngleStartX, threeStartY, arcAngleEndX, arcAngleEndY, 90, 150, false, paint);

        //画一根弧线
        String drawArcLine = "画一根弧线";
        int arcLineEndX = thirdStartX + 150;
        int arcLineEndY = threeStartY + 100;
        canvas.drawText(drawArcLine, thirdStartX, thirdTextY, textPaint);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        //同上  只是设置了paint的样式
        canvas.drawArc(thirdStartX, threeStartY, arcLineEndX, arcLineEndY, 90, 200, false, paint);

        //绘制View的填充颜色
        //绘制先后顺序的重要性
        //如果drawColor()在这里执行，那么前面的drawCircle()和drawRect()都会被覆盖
        //canvas.drawColor(Color.parseColor("#2adecb"));

        String string = "所有的文字和形状都是在一个View里面绘制的";
        textPaint.setColor(Color.parseColor("#ef5050"));
        canvas.drawText(string,50,600,textPaint);
    }
}
