package com.project.june.customview.ui.answer;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 2.6.0
 * @description 单选答题View
 * @time 2019/8/20 10:36
 */
public class AnswerSingleView extends LinearLayoutCompat implements View.OnClickListener {

    private AppCompatTextView answerQuestion, answerChooseA, answerChooseB, answerChooseC, answerChooseD;

    private String choiceAnswer;

    public AnswerSingleView(Context context) {
        this(context, null);
    }

    public AnswerSingleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerSingleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.widget_answer_single_view, this, true);
        answerQuestion = view.findViewById(R.id.answer_question_text);
        answerChooseA = view.findViewById(R.id.answer_choose_a);
        answerChooseB = view.findViewById(R.id.answer_choose_b);
        answerChooseC = view.findViewById(R.id.answer_choose_c);
        answerChooseD = view.findViewById(R.id.answer_choose_d);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setOrientation(VERTICAL);

        answerChooseA.setOnClickListener(this);
        answerChooseB.setOnClickListener(this);
        answerChooseC.setOnClickListener(this);
        answerChooseD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_choose_a:
                choiceAnswer = "A";
                break;
            case R.id.answer_choose_b:
                choiceAnswer = "B";
                break;
            case R.id.answer_choose_c:
                choiceAnswer = "C";
                break;
            case R.id.answer_choose_d:
                choiceAnswer = "D";
                break;
        }

        answerChooseA.setSelected(v.getId() == answerChooseA.getId());
        answerChooseB.setSelected(v.getId() == answerChooseB.getId());
        answerChooseC.setSelected(v.getId() == answerChooseC.getId());
        answerChooseD.setSelected(v.getId() == answerChooseD.getId());
    }
}
