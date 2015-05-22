package com.brucewuu.android.video.demo.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ScreenUtils {

    public static final int dpToPx(float dp, Resources resources){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    /**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
    public static float pxToDp(Context context, float px) {
        return px / screenDensity(context);
    }

    /**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
    public static int pxToDpCeilInt(Context context, float px) {
        return (int)(pxToDp(context, px) + 0.5f);
    }
    
    /**
     * 获取屏幕宽
     * @param context
     * @return
     */
    public static int screenWidth(Context context) {
    	return context.getResources().getDisplayMetrics().widthPixels;
    }
    
    /**
     * 获取屏幕高
     * @param context
     * @return
     */
    public static int screenHeight(Context context) {
    	return context.getResources().getDisplayMetrics().heightPixels;
    }
    
    /**
     * 获取屏幕像素密度
     * @param context
     * @return
     */
    public static float screenDensity(Context context) {
    	return context.getResources().getDisplayMetrics().density;
    }
}
