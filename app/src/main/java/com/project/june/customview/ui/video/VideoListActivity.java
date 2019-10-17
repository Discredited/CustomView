package com.project.june.customview.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.june.customview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.project.june.customview.ui.video.VideoActivity.URL_LIST;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 1.0.0
 * @description com.project.june.customview.ui.video
 * @time 2019/10/17 15:57
 */
public class VideoListActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, VideoListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        VideoAdapter adapter = new VideoAdapter();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        adapter.setItemList(Arrays.asList(URL_LIST));
    }

    static class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

        private List<String> items = new ArrayList<>();

        @NonNull
        @Override
        public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
            holder.bindData(items.get(position), position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItemList(List<String> list) {
            items.clear();
            items.addAll(list);
            notifyDataSetChanged();
        }

        class VideoViewHolder extends RecyclerView.ViewHolder {

            VideoPlayerView videoPlayerView;

            public VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                videoPlayerView = itemView.findViewById(R.id.vpPlayer);
            }

            public void bindData(String url, int position) {
                videoPlayerView.setUp(url, "第" + position + "个视频");
            }
        }
    }
}
