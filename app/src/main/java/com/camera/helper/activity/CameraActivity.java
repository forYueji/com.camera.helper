package com.camera.helper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.camera.helper.R;
import com.camera.helper.camera.CameraTool;
import com.camera.helper.customview.CameraGrid;
import com.camera.helper.dialog.DialogHelper;
import com.camera.helper.utils.PictureCallbackDataHelper;
import com.camera.helper.utils.PictureCallbackHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import junit.framework.Test;

import de.greenrobot.event.EventBus;

/**
 * Created by huangyaping on 16/4/8.
 */
public class CameraActivity extends BaseFragmentActivity {
    private static final String TAG = "CameraActivity";

    @ViewInject(R.id.surfaceView)
    private SurfaceView surfaceView;

    @ViewInject(R.id.buttonLayout)
    private RelativeLayout buttonLayout;

    @ViewInject(R.id.panel_take_photo)
    private RelativeLayout panel_take_photo;

    @ViewInject(R.id.camera_top)
    private RelativeLayout camera_top;

    @ViewInject(R.id.flashBtn)
    private ImageView flashBtn;

    @ViewInject(R.id.change)
    private ImageView change;

    @ViewInject(R.id.masking)
    private CameraGrid masking;

    @ViewInject(R.id.focus_index)
    private View focus_index;

    @ViewInject(R.id.takepicture)
    private ImageView takepicture;

    @ViewInject(R.id.back)
    private ImageView back;

    @ViewInject(R.id.next)
    private ImageView next;

    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        ViewUtils.inject(this);
        initSurfaceView();
        initOncliclList();

        dialogHelper = new DialogHelper(this);
        EventBus.getDefault().register(this);
    }

    public void initSurfaceView(){
        CameraTool.getInstance().openCamera(surfaceView, this);
    }

    public void initOncliclList (){
        takepicture.setOnClickListener(onClickListener);
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.takepicture:
                    CameraTool.getInstance().onTakePicture(callbackHelper);
                    break;
            }
        }
    };

    public void onEvent (String path){
        dialogHelper.dismissProgressDialog();
        if (!TextUtils.isEmpty(path)){
            Intent newIntent = new Intent(this, ImageAcitivity.class);
            newIntent.setData(Uri.parse(path));
            startActivity(newIntent);
        }
    }

    public PictureCallbackHelper callbackHelper = new PictureCallbackHelper() {
        @Override
        public void pictureCallbackData(byte[] data) {
            dialogHelper.showProgressDialog("处理中...");
            new PictureCallbackDataHelper(CameraActivity.this,data).execute();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
