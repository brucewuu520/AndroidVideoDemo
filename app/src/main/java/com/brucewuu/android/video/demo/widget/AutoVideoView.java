package com.brucewuu.android.video.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 自动全屏的VideoView
 * Created by brucewuu on 2015-5-21.
 */
public class AutoVideoView extends VideoView {

    private int videoWidth;
    private int videoHeight;


    public AutoVideoView(Context context) {
        this(context, null);
    }

    public AutoVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        if (videoWidth > 0 && videoHeight > 0) {
            if (videoWidth * height > width * videoHeight) {
                height = width * videoHeight / videoWidth;
            } else {
                width = height * videoWidth / videoHeight;
            }
            setMeasuredDimension(width, height);
        }
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }
}
