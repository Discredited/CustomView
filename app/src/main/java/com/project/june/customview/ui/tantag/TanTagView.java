package com.project.june.customview.ui.tantag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.june.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 1.0.0
 * @description 标签View
 * @time 2018/10/29 13:50
 */
public class TanTagView extends View {

    private Paint textPaint;
    private Paint bgPaint;

    private int tagTextSize;  //tag文本字体大小
    private int tagPaddingVertical; //tag文本垂直内间距
    private int tagPaddingHorizontal; //tag文本水平内间距
    private int tagMargin;  //tag之间的距离
    private int tagRadius;  //tag圆角

    private List<String> tagList = new ArrayList<>();

    public TanTagView(Context context) {
        this(context, null);
    }

    public TanTagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TanTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        tagTextSize = getResources().getDimensionPixelSize(R.dimen.text_size_12) * 2;
        tagPaddingVertical = getResources().getDimensionPixelSize(R.dimen.tag_padding_vertical);
        tagPaddingHorizontal = getResources().getDimensionPixelSize(R.dimen.tag_padding_horizontal);
        tagMargin = getResources().getDimensionPixelSize(R.dimen.tag_margin);
        tagRadius = getResources().getDimensionPixelSize(R.dimen.tag_radius);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(tagTextSize);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        tagList.add("好物");
        tagList.add("二手");
        tagList.add("技能");
        tagList.add("商演");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bgStart = getPaddingStart();
        float bgEnd;
        float bgTop = getPaddingTop();
        //关于fotMetrics的属性介绍
        //fotMetrics.top      = top线y坐标     - baseLine线y坐标（负值）  top到baseline的距离
        //fontMetrics.ascent  = ascent线y坐标  - baseLine线y坐标（负值）  ascent到baseline的距离
        //fontMetrics.descent = descent线y坐标 - baseLine线y坐标（正值）  descent与baseline的距离
        //fontMetrics.bottom  = bottom线y坐标  - baseLine线y坐标（正值）  bottom与baseline的距离
        float textHeight = textPaint.descent() - textPaint.ascent();
        float bgBottom = bgTop + textHeight + tagPaddingVertical * 2;
        float textBottom = bgTop + textHeight + tagPaddingVertical - textPaint.descent();


        for (int i = 0; i < tagList.size(); i++) {
            String tagText = tagList.get(i);
            float textLength = textPaint.measureText(tagText);
            bgStart = i > 0 ? (bgStart + textLength + tagPaddingHorizontal * 2 + tagMargin) : 0;
            bgEnd = bgStart + textLength + tagPaddingHorizontal * 2;
            bgPaint.setColor(getPaintColor(tagText, true));
            canvas.drawRoundRect(bgStart, bgTop, bgEnd, bgBottom, tagRadius, tagRadius, bgPaint);
            float textStart = bgStart + tagPaddingHorizontal;
            textPaint.setColor(getPaintColor(tagText, false));
            canvas.drawText(tagText, textStart, textBottom, textPaint);
        }
    }

    private int getPaintColor(String text, boolean isBackground) {
        int alpha = isBackground ? 39 : 255;
        int color = Color.argb(alpha, 151, 0, 1);
        switch (text) {
            case "好物":
                color = Color.argb(alpha, 151, 0, 1);
                break;
            case "技能":
                color = Color.argb(alpha, 65, 112, 1);
                break;
            case "二手":
                color = Color.argb(alpha, 160, 233, 1);
                break;
            case "商演":
                color = Color.argb(alpha, 211, 168, 1);
                break;
        }
        return color;
    }

    public void setTagList(List<String> list) {
        tagList.clear();
        tagList.addAll(list);
        requestLayout();
    }
}
