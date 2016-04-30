package com.camera.helper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by huangyaping on 16/4/8.
 */
public class UiHelper {
    private static UiHelper uiHelper;

    public static UiHelper getInstance(){
        if (uiHelper == null){
            synchronized (UiHelper.class){
                if (uiHelper == null){
                    uiHelper = new UiHelper();
                }
            }
        }
        return uiHelper;
    }

    public void startActivity (Context activity , Class<?> obj , Bundle bundle){
        Intent intent = new Intent(activity,obj);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void startaActivity (Context activity , Class<?> obj){
        Intent intent = new Intent(activity,obj);
        activity.startActivity(intent);
    }
}
