package com.project.june.customview;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.june.customview.bean.ClassifyBean;
import com.project.june.customview.ui.answer.AnswerRushActivity;
import com.project.june.customview.ui.color.ColorActivity;
import com.project.june.customview.ui.first.FirstCustomActivity;
import com.project.june.customview.ui.level.ProgressActivity;
import com.project.june.customview.ui.magnifier.MagnifierActivity;
import com.project.june.customview.ui.progress.MochaProgressActivity;
import com.project.june.customview.ui.second.SecondCustomActivity;
import com.project.june.customview.ui.tantag.TanTagViewActivity;
import com.project.june.customview.ui.video.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClassifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();
        initData();
    }

    private void initData() {
        List<ClassifyBean> list = new ArrayList<>();
        list.add(new ClassifyBean("自定义View 初始", "自定义View的简单图形绘制方法 drawXXXX()"));
        list.add(new ClassifyBean("自定义View 初始", "自定义View的使用路径绘制复杂图形 drawPath()"));
        list.add(new ClassifyBean("自定义TanTagView", "摊摊标签View"));
        list.add(new ClassifyBean("ProgressBar", "自定义进度条"));
        list.add(new ClassifyBean("Progress", "模卡进度条"));
        list.add(new ClassifyBean("ColorView", "颜色选择"));
        list.add(new ClassifyBean("MagnifierView", "放大镜效果"));
        list.add(new ClassifyBean("AnswerView", "答题相关View"));
        list.add(new ClassifyBean("VideoPlayerView", "QAQ模卡视频播放View"));
        adapter.setItemList(list, true);
    }

    private void initRecyclerView() {
        adapter = new ClassifyAdapter();
        adapter.setItemClickListener(new OnItemClickListener<ClassifyBean>() {
            @Override
            public void onClick(View view, int position, ClassifyBean itemData) {
                if (position == 0) {
                    FirstCustomActivity.startThis(MainActivity.this);
                } else if (position == 1) {
                    SecondCustomActivity.startThis(MainActivity.this);
                } else if (position == 2) {
                    TanTagViewActivity.startThis(MainActivity.this);
                } else if (position == 3) {
                    ProgressActivity.startThis(MainActivity.this);
                } else if (position == 4) {
                    MochaProgressActivity.startThis(MainActivity.this);
                } else if (position == 5) {
                    ColorActivity.startThis(MainActivity.this);
                } else if (position == 6) {
                    MagnifierActivity.start(MainActivity.this);
                } else if (position == 7) {
                    AnswerRushActivity.start(MainActivity.this);
                } else if (position == 8) {
                    VideoActivity.start(MainActivity.this);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    public static class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ClassifyViewHolder> {

        private List<ClassifyBean> items;
        private OnItemClickListener<ClassifyBean> itemClickListener;

        public ClassifyAdapter() {
            items = new ArrayList<>();
        }

        @NonNull
        @Override
        public ClassifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ClassifyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_classify_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ClassifyViewHolder holder, int position) {
            final ClassifyBean bean = items.get(position);
            holder.bindData(position, bean);
            if (null != itemClickListener) {
                holder.itemView.setOnClickListener(v -> itemClickListener.onClick(v, holder.getAdapterPosition(), bean));
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItemList(List<ClassifyBean> list, boolean isClear) {
            if (isClear) {
                items.clear();
            }
            items.addAll(list);
            notifyDataSetChanged();
        }

        public void setItemClickListener(OnItemClickListener<ClassifyBean> listener) {
            itemClickListener = listener;
        }

        public class ClassifyViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView classifyName, classifyDescription;

            public ClassifyViewHolder(View itemView) {
                super(itemView);
                classifyName = itemView.findViewById(R.id.classify_name);
                classifyDescription = itemView.findViewById(R.id.classify_description);
            }

            public void bindData(int position, ClassifyBean bean) {
                classifyName.setText(bean.title);
                classifyDescription.setText(bean.description);
            }
        }
    }

    public interface OnItemClickListener<T> {
        void onClick(View view, int position, T itemData);
    }
}