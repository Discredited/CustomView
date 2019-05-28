package com.project.june.customview.ui.level;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.june.customview.R;

import java.util.Locale;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.0.0
 * @description 自定义等级View
 * @time 2019/2/27 19:22
 */
public class LevelProgressView extends View {


    private Paint levelTextPaint;//等级文本绘制画笔
    private Paint experienceTextPaint;//经验值文本画笔
    private Paint experienceBackgroundPaint;//经验值背景框画笔
    private Paint progressPaint;//进度条画笔
    private Paint maxProgressPaint;//最大进度条画笔

    //总进度矩形
    private RectF unReachedRectF = new RectF(0, 0, 0, 0);
    //已到达进度矩形
    private RectF reachedRectF = new RectF(0, 0, 0, 0);
    //背景框的矩形
    private RectF experienceRectF = new RectF(0, 0, 0, 0);
    //三角形的Path
    private Path trianglePath = new Path();

    private int levelTextSize;
    private float levelTextHeight;
    private int experienceTextSize;
    private float experienceTextHeight;

    private float progressHeight;  //progressBar高度
    private float experienceBackgroundHeight;  //经验值显示框高度

    private int experienceTextOffset; //经验值和进度框的偏移量
    private int levelOffsetProgress; //等级和进度框的偏移量

    private float progressTopPosition; //进度条的顶部开始位置
    private int suggestionHeight; // 最小高度

    private int currentProgress;  //当前进度
    private int maxProgress;  //最大进度

    private int currentLevel;
    private int maxLevel; //最大等级
    private String currentLevelString;
    private String nextLevelString;

    private float progressRadius;
    private float drawCurrentLevelStringTop;

    public LevelProgressView(Context context) {
        this(context, null);
    }

    public LevelProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        levelTextSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_30);
        experienceTextSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_10);

        experienceTextOffset = context.getResources().getDimensionPixelSize(R.dimen.experience_progress_offset);
        levelOffsetProgress = context.getResources().getDimensionPixelSize(R.dimen.progress_offset);

        initPainter();

        currentProgress = 0;
        maxProgress = 100;

        //等级文字的顶部位置  经验框高度 + 经验框偏移量
        //等级文字的顶部开始位置
        float levelTextTopPosition = getPaddingTop() + experienceBackgroundHeight + experienceTextOffset;
        //绘制等级文字的top y位置
        drawCurrentLevelStringTop = levelTextTopPosition + levelTextHeight - (levelTextPaint.getFontMetrics().bottom - levelTextPaint.getFontMetrics().descent) - (levelTextPaint.getFontMetrics().ascent - levelTextPaint.getFontMetrics().top);
        //绘制进度框的top y位置
        progressTopPosition = levelTextTopPosition + (levelTextHeight - progressHeight) / 2;

        //建议最小高度 经验值RectF高度 + offset + levelText + padding
        suggestionHeight = (int) (levelTextTopPosition + levelTextHeight) + getPaddingTop() + getPaddingBottom();

        setLevelString();
    }

    private void initPainter() {
        levelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        experienceTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        experienceBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maxProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        levelTextPaint.setColor(Color.parseColor("#666666"));
        levelTextPaint.setTextSize(levelTextSize);
        levelTextHeight = (levelTextPaint.descent() - levelTextPaint.ascent());

        experienceTextPaint.setColor(Color.parseColor("#00B7EE"));
        experienceTextPaint.setTextSize(experienceTextSize);
        experienceTextHeight = experienceTextPaint.descent() - experienceTextPaint.ascent();

        experienceBackgroundPaint.setColor(Color.parseColor("#2200B7EE"));
        experienceBackgroundPaint.setStyle(Paint.Style.FILL);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_level_progress);
        progressHeight = bitmap.getHeight() > levelTextHeight ? levelTextHeight : bitmap.getHeight();
        progressPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR));

        maxProgressPaint.setColor(Color.parseColor("#E6E6E6"));

        progressRadius = progressHeight / 2;
        experienceBackgroundHeight = experienceTextOffset + (experienceTextPaint.descent() - experienceTextPaint.ascent());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    //计算矩形
    private void calculateDrawRectF() {
        float currentLevelTextWidth = levelTextPaint.measureText(currentLevelString);
        float nextLevelTextWidth = levelTextPaint.measureText(nextLevelString);

        float progressRectFStart = getPaddingStart() + currentLevelTextWidth + levelOffsetProgress;
        float progressRectFEnd = getWidth() - getPaddingEnd() - nextLevelTextWidth - levelOffsetProgress;
        float progressRectTop = getPaddingTop() + progressTopPosition;
        float progressRectBottom = progressRectTop + progressHeight;

        float percent = currentProgress * 1f / maxProgress;
        float reachProgressEnd = progressRectFStart + (progressRectFEnd - progressRectFStart) * (percent > 1f ? 1f : percent);

        //最大进度
        //左边距+当前等级文字长度+等级文字偏移量
        unReachedRectF.left = progressRectFStart;
        //View宽度 - 右边距 - 下一级文字长度 —等级文字偏移量
        unReachedRectF.right = progressRectFEnd;
        //顶部内间距 + 经验值背景高度 + 经验值文字与背景间距 + 等级文字偏移量
        unReachedRectF.top = progressRectTop;
        //等级文字高度
        unReachedRectF.bottom = progressRectBottom;

        //已到达进度
        reachedRectF.left = progressRectFStart;
        reachedRectF.right = reachProgressEnd;
        reachedRectF.top = progressRectTop;
        reachedRectF.bottom = progressRectBottom;

        //经验值背景框
        String drawExperience = String.format(Locale.CHINA, "%d/%d", getCurrentProgress(), getMaxProgress());
        float experienceWidth = experienceTextPaint.measureText(drawExperience);
        experienceRectF.left = reachProgressEnd - experienceWidth / 2 - experienceTextOffset;
        experienceRectF.right = reachProgressEnd + experienceWidth / 2 + experienceTextOffset;
        experienceRectF.top = getPaddingTop();
        experienceRectF.bottom = getPaddingTop() + experienceBackgroundHeight;

        //绘制背景框下面的三角形
        float triangleWidth = levelOffsetProgress / 2f;
        float triangleStartX = experienceRectF.left + (experienceRectF.right - experienceRectF.left) / 2f - triangleWidth;
        trianglePath.reset();
        trianglePath.moveTo(triangleStartX, experienceRectF.bottom);
        trianglePath.lineTo(triangleStartX + levelOffsetProgress, experienceRectF.bottom);
        trianglePath.lineTo(experienceRectF.left + (experienceRectF.right - experienceRectF.left) / 2f, experienceRectF.bottom + triangleWidth);
        trianglePath.lineTo(triangleStartX, experienceRectF.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculateDrawRectF();

        //绘制等级文本
        //当前等级
        float drawCurrentLevelStringStart = getPaddingStart();
        canvas.drawText(currentLevelString, drawCurrentLevelStringStart, drawCurrentLevelStringTop, levelTextPaint);
        //下一级
        float drawNextLevelStringStart = getWidth() - getPaddingEnd() - levelTextPaint.measureText(nextLevelString);
        canvas.drawText(nextLevelString, drawNextLevelStringStart, drawCurrentLevelStringTop, levelTextPaint);

        //未到达进度
        canvas.drawRoundRect(unReachedRectF, progressRadius, progressRadius, maxProgressPaint);


        //已到达进度
        canvas.drawRoundRect(reachedRectF, progressRadius, progressRadius, progressPaint);

        //经验显示
        canvas.drawRoundRect(experienceRectF, 5, 5, experienceBackgroundPaint);
        canvas.drawPath(trianglePath, experienceBackgroundPaint);
        //绘制经验文本
        String drawExperience = String.format(Locale.CHINA, "%d/%d", getCurrentProgress(), getMaxProgress());
        float drawExperienceTop = experienceRectF.top + experienceTextHeight;
        canvas.drawText(drawExperience, experienceRectF.left + experienceTextOffset, drawExperienceTop, experienceTextPaint);
    }


    @Override
    protected int getSuggestedMinimumWidth() {
        return super.getSuggestedMinimumWidth();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return suggestionHeight;
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        setProgress(currentProgress);
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setProgress(int progress, int max, int level) {
        currentProgress = progress;
        if (currentProgress > maxProgress) {
            currentProgress = maxProgress;
        }
        maxProgress = max;
        currentLevel = level;
        invalidate();
    }

    public void setMaxLevel(int level) {
        maxLevel = level;
    }

    public void setProgress(int progress) {
        setProgress(progress, getMaxProgress(), getCurrentLevel());
    }


    public void addProgress(int incrementProgress) {
        //已满级并且获得了最大经验
        if (isLevelMax() && currentProgress >= maxProgress) {
            return;
        }

        //增加的目标进度
        int incrementTargetProgress = currentProgress + incrementProgress;

        int overtakeProgress;  //超出经验

        if (incrementTargetProgress >= maxProgress) {
            overtakeProgress = isLevelMax() ? -1 : (incrementTargetProgress - maxProgress);
            incrementTargetProgress = maxProgress;
        } else {
            overtakeProgress = -1;
        }

        doAnimator(currentProgress, incrementTargetProgress, overtakeProgress);
    }

    private void setLevelString() {
        currentLevelString = String.format(Locale.CHINA, "Lv%d", currentLevel);
        nextLevelString = String.format(Locale.CHINA, "Lv%d", currentLevel + 1);
    }

    private boolean isLevelMax() {
        return maxLevel - 1 == currentLevel;
    }

    private void doAnimator(int progress, int target, final int overtakeProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "currentProgress", progress, target);
        if (overtakeProgress >= 0) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    currentProgress = 0;
                    maxProgress = maxProgress + currentLevel * 10;

                    currentLevel += 1;
                    setLevelString();
                    doAnimator(0, overtakeProgress, -1);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        animator.start();
    }
}
