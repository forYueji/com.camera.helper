package com.camera.helper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.camera.helper.R;
import com.camera.helper.camera.CameraManager;
import com.lidroid.xutils.ViewUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initView();
    }


    public void initView (){
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraManager.getInstance().openCamera(MainActivity.this);
            }
        });
    }
}
