package com.brucewuu.android.video.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brucewuu.android.video.demo.R;
import com.brucewuu.android.video.demo.widget.AutoVideoView;
import com.brucewuu.android.video.demo.widget.view.playpause.PlayPauseView;

/**
 * Created by 吴昭 on 2015-5-21.
 */
public class VideoActivity extends Activity {

    // 自定义VideoView
    private AutoVideoView mVideoView;
    // 头部View
    private View mTopView;
    // 底部View
    private View mBottomView;
    // 视频播放拖动条
    private SeekBar mSeekBar;
    private PlayPauseView mBtnPlay;
    private TextView mPlayTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video);
        mBtnPlay = (PlayPauseView) findViewById(R.id.play_pause_view);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnPlay.toggle();
            }
        });
    }
}
