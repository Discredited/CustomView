package com.project.june.customview.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.june.customview.R;
import com.project.june.customview.bean.ClassifyBean;

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
        list.add(new ClassifyBean("自定义View初始", "自定义View的基础知识"));
        adapter.setItemList(list, true);
    }

    private void initRecyclerView() {
        adapter = new ClassifyAdapter();
        adapter.setItemClickListener(new OnItemClickListener<ClassifyBean>() {
            @Override
            public void onClick(View view, int position, ClassifyBean itemData) {
                if (position == 0) {
                    FirstCustomActivity.startThis(MainActivity.this);
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
        public void onBindViewHolder(@NonNull ClassifyViewHolder holder, final int position) {
            final ClassifyBean bean = items.get(position);
            holder.bindData(position, bean);
            if (null != itemClickListener) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onClick(v, position, bean);
                    }
                });
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