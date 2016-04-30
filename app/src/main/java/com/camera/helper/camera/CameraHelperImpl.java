package com.camera.helper.camera;

import android.hardware.Camera;

/**
 * 作者:Created by yaping on 2015/12/7.
 * 名称：com.touchyo
 */
public interface CameraHelperImpl {
    int getNumberOfCameras();

    Camera openCamera(int id);

    Camera openDefaultCamera();

    Camera openCameraFacing(int facing);

    boolean hasCamera(int cameraFacingFront);

    void getCameraInfo(int cameraId, CameraInfo2 cameraInfo);
}
