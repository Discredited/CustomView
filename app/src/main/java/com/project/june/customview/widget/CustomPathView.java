package com.project.june.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 1.0.0
 * @description 第二个自定义View  drawPath() 使用路径绘制图形
 * <p>
 * 注释的代码都是可用代码  演示不同的绘制方式和重载方法
 * @time 2018/10/25 10:47
 */
public class CustomPathView extends View {

    private Paint paint;
    private Paint textPaint;
    private Path path;

    public CustomPathView(Context context) {
        this(context, null);
    }

    public CustomPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        //Paint.ANTI_ALIAS_FLAG 开启抗锯齿 开启抗锯齿来让图形和文字的边缘更加平滑
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Paint.Style.FILL Paint填充样式-填充满
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.text_size_12));

        // 初始化 Path 对象
        path = new Path();
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
    }

    //只重写 onDraw()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成
    }
}
