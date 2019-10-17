package com.project.june.customview.ui.answer;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.6.0
 * @description 答题计时器监听
 * @time 2019/8/20 14:24
 */
public interface AnswerTimeCountListener {

    void onTimeCountStart();

    void onTimeCountComplete();

    void onTimeCountCancel();

    void onTimeCountChange(int currentTime);
}
