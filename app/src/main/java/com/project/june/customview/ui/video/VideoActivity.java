package com.project.june.customview.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.project.june.customview.R;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 1.0.0
 * @description com.project.june.customview.ui.video
 * @time 2019/10/17 15:03
 */
public class VideoActivity extends AppCompatActivity {

    public static final String[] URL_LIST = {
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc561a9e681f82beb2fa330387e1adc4082d050f131aa8b526cdaefde2b671254c13a2b8820c18191e6e7675eb504dc71a53&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc56bdbbc322373ebdac24fe4c744dcca9157a406c9c9bb1d2f1884fe4b4108802ecd9f5070f15d5d0a5c97b7ec92f52010a&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc56b9f9b66c454fecbc6990de57317130b77080143ac6b4547e3ad0d75c3e19b35888b8c5df510128c791cdc39c56bd5d15&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc56201bf14f2db8f5365b9d1d2f17d7afdd34edcf1c1f3f7267a17244ba27b004c0cda4e76bdbb708e6578786a52a8ac85d&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc569ea7cf2a2f4b958da4f68ef64fd1e7344c99e3da7f8adbf2704e8805095b988d22adc6a2bf400bc989e9f0aad3699fc9&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc569d47dbdb469ac42fe76cc315b1f33e155e58743e2ce971fc27758cc81458682ab9a0f3090d0f2d90f42e9b8a75e62801&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc568e97230b4fde189962ffc7cf7efe115fe2dfcf6b7de7143998bcd6fe5059efc4dd70f369b338c50cc4b3e2c7c3445d91&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc56fee42a2593a3e4f651ad1fdb1ea9e1d4a183675ee4c40627685e80d624af0b8e025ccfda50f5ab598e281c9f50bb291b&line=0",
            "https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc5610e35a7727b67a678528ff14ebc1653f0dfa9b5a1dcd89606ec501223810fad42973f0b451efbeb1f52f2423eb61edab&line=0",
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, VideoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoPlayerView videoPlayerView = findViewById(R.id.vpPlayer);
        videoPlayerView.setUp(URL_LIST[1], "唐牙牙");
        Glide.with(this).load("https://p3.pstatp.com/large/1954a0001568787956f3e.jpg").into(videoPlayerView.thumbImageView);

        findViewById(R.id.btToList).setOnClickListener(v -> VideoListActivity.start(VideoActivity.this));
    }
}
