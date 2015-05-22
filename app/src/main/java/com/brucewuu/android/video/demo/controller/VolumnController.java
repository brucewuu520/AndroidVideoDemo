package com.brucewuu.android.video.demo.controller;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.brucewuu.android.video.demo.R;

import tr.xip.markview.MarkView;

/**
 * Created by brucewuu on 15/5/22.
 */
public class VolumnController {

    private Context mContext;
    private Toast mToast;
    private MarkView markView;


    public VolumnController(Context mContext) {
        this.mContext = mContext;
    }

    public void show(int progress) {
        if (mToast == null) {
            mToast = new Toast(mContext);
            View view = View.inflate(mContext, R.layout.view_controll_volumn, null);
            markView = (MarkView) view.findViewById(R.id.mark_volumn_controll);
            mToast.setView(view);
            mToast.setGravity(Gravity.CENTER, 0 , 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        markView.setMark(progress);
        mToast.show();
    }
}
