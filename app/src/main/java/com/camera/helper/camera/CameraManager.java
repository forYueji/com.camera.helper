package com.camera.helper.camera;

import android.app.Activity;

import com.camera.helper.activity.CameraActivity;
import com.camera.helper.utils.UiHelper;

import java.util.Stack;

/**
 * Created by huangyaping on 16/4/8.
 */
public class CameraManager {

    /** 相机辅助类 */
    public static CameraManager cameraManager;
    private Stack<Activity> stack = new Stack<Activity>();

    public static CameraManager getInstance(){
        if (cameraManager == null){
            synchronized (CameraManager.class){
                if (cameraManager == null){
                    cameraManager = new CameraManager();
                }
            }
        }
        return cameraManager;
    }

    /** 打开相机 */
    public void openCamera (Activity activity){
        UiHelper.getInstance().startaActivity(activity, CameraActivity.class);
    }

    public void addActivity (Activity activity){
        stack.add(activity);
    }

    public void removeActivity (Activity activity){
        stack.remove(activity);
    }
}