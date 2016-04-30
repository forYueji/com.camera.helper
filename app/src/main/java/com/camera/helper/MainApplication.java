package com.camera.helper;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by huangyaping on 16/4/8.
 */
public class MainApplication extends Application {
    private DisplayMetrics displayMetrics;

    private static MainApplication mInstance;

    public MainApplication (){
        mInstance = this;
    }

    public static MainApplication getApp() {
        if (mInstance != null && mInstance instanceof MainApplication) {
            return (MainApplication) mInstance;
        } else {
            mInstance = new MainApplication();
            mInstance.onCreate();
            return (MainApplication) mInstance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }
}
