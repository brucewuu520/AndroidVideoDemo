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
 * Created by ���� on 2015-5-21.
 */
public class VideoActivity extends Activity {

    // �Զ���VideoView
    private AutoVideoView mVideoView;
    // ͷ��View
    private View mTopView;
    // �ײ�View
    private View mBottomView;
    // ��Ƶ�����϶���
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
