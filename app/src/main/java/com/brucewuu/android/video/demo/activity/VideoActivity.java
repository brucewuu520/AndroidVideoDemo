package com.brucewuu.android.video.demo.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brucewuu.android.video.demo.R;
import com.brucewuu.android.video.demo.controller.VolumnController;
import com.brucewuu.android.video.demo.util.ScreenUtils;
import com.brucewuu.android.video.demo.widget.AutoVideoView;
import com.brucewuu.android.video.demo.widget.view.playpause.PlayPauseView;

/**
 * Created by brucewuu on 2015-5-21.
 */
public class VideoActivity extends Activity implements View.OnClickListener {

    // 自定义VideoView
    private AutoVideoView mVideoView;
    // 头部View
    private View mTopView;
    // 底部View
    private View mBottomView;
    // 视频标题
    private TextView mTitle;
    // 视频播放拖动条
    private SeekBar mSeekBar;
    // 播放暂停按钮
    private PlayPauseView mBtnPlay;
    // 当前播放时间
    private TextView mPlayTime;
    // 视频总时长
    private TextView mVideoTime;

    // 屏幕宽高
    private float videoWidth;
    private float videoHeight;
    // 视频已播放位置
    private int playedPosition;

    // 自动隐藏顶部和底部View的时间
    private static final int HIDE_BAR_TIME = 5000;
    // 音频管理器
    private AudioManager mAudioManager;
    // 控制音量显示器
    private VolumnController mVolumnController;

    private GestureDetectorCompat mDetectorCompat;

    private static final String videoUrl = "http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4";

    private final CountDownTimer mControllBarTimer = new CountDownTimer(HIDE_BAR_TIME, HIDE_BAR_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            showOrHideBarView();
        }
    };

    private final CountDownTimer mUpdatePlayStateTimer = new CountDownTimer(System.currentTimeMillis(), 1000L) {
        @Override
        public void onTick(long millisUntilFinished) {
            int currentPosition = mVideoView.getCurrentPosition();
            if (currentPosition > 0) {
                mPlayTime.setText(String.valueOf(currentPosition));
                int progress = currentPosition * 100 / mVideoView.getDuration();
                mSeekBar.setProgress(progress);
                if (currentPosition > mVideoView.getDuration() - 100) {
                    mPlayTime.setText("00:00");
                    mSeekBar.setProgress(0);
                }
                mSeekBar.setSecondaryProgress(mVideoView.getBufferPercentage());
            } else {
                mPlayTime.setText("00:00");
                mSeekBar.setProgress(0);
            }
        }

        @Override
        public void onFinish() {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video);
        initViews();

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mVolumnController = new VolumnController(this);
        mDetectorCompat = new GestureDetectorCompat(this, new MySimpleOnGestureListener());
        videoWidth = ScreenUtils.screenWidth(this);
        videoHeight = ScreenUtils.screenHeight(this);

        playVideo();
    }

    private void initViews() {
        mVideoView = (AutoVideoView) findViewById(R.id.video_view);
        mTopView = findViewById(R.id.video_top_view);
        mBottomView = findViewById(R.id.video_bottom_view);
        findViewById(R.id.btn_left_back).setOnClickListener(this);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mTitle = (TextView) findViewById(R.id.tv_video_title);
        mPlayTime = (TextView) findViewById(R.id.play_time);
        mVideoTime = (TextView) findViewById(R.id.total_time);
        mBtnPlay = (PlayPauseView) findViewById(R.id.play_pause_view);
        mBtnPlay.setOnClickListener(this);
    }

    private void playVideo() {
        mVideoView.setVideoPath(videoUrl);
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.setVideoWidth(mp.getVideoWidth());
                mVideoView.setVideoHeight(mp.getVideoHeight());
                mVideoView.start();
                mBtnPlay.toggle();
                if (playedPosition != 0)
                    mVideoView.seekTo(playedPosition);
                mControllBarTimer.cancel();
                mControllBarTimer.start();
                mVideoTime.setText(String.valueOf(mVideoView.getDuration()));
                mUpdatePlayStateTimer.cancel();
                mUpdatePlayStateTimer.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtnPlay.toggle();
                mUpdatePlayStateTimer.cancel();
            }
        });
    }

    private void showOrHideBarView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left_back:
                finish();
                break;
            case R.id.play_pause_view:
                if (mBtnPlay.toggle()) {
                    if (mVideoView.isPlaying()) {
                        mVideoView.pause();
                    }
                } else {
                    mVideoView.start();
                }
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { // 横屏
            videoWidth = ScreenUtils.screenHeight(this);
            videoHeight = ScreenUtils.screenWidth(this);
        } else { // 竖屏
            videoWidth = ScreenUtils.screenWidth(this);
            videoHeight = ScreenUtils.screenHeight(this);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mControllBarTimer.cancel();
        mUpdatePlayStateTimer.cancel();
    }

    private void backward(float delataX) {
        int current = mVideoView.getCurrentPosition();
        int backwardTime = (int) (delataX / videoWidth * mVideoView.getDuration());
        int currentTime = current - backwardTime;
        mVideoView.seekTo(currentTime);
        mSeekBar.setProgress(currentTime * 100 / mVideoView.getDuration());
        mPlayTime.setText(String.valueOf(currentTime));
    }

    private void forward(float delataX) {
        int current = mVideoView.getCurrentPosition();
        int forwardTime = (int) (delataX / videoWidth * mVideoView.getDuration());
        int currentTime = current + forwardTime;
        mVideoView.seekTo(currentTime);
        mSeekBar.setProgress(currentTime * 100 / mVideoView.getDuration());
        mPlayTime.setText(String.valueOf(currentTime));
    }

    private void onVolumeSlide(float delatY) {
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int down = (int) (delatY / videoHeight * max * 3);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + down;

        int volume = 0;
        if (current <= 0)
            volume = 0;
        else if (current >= max)
            volume = max;
        else
            volume = current;
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

        mVolumnController.show(volume);
    }

    private final class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float x1 = e1.getX(), y1 = e1.getY();
            float x2 = e2.getX(), y2 = e2.getY();
            if (x1 > videoWidth * 4 / 5) { // 右边滑动，调节音量
                onVolumeSlide(y2 - y1);
            } else if (x1 < videoWidth * 2 / 5 ) { // 左边滑动，调节亮度

            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {

            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("GestureDetector:", "---onSingleTapUp--");
            return super.onSingleTapUp(e);
        }
    }
}
