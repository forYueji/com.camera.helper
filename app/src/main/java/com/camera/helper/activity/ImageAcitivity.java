package com.camera.helper.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.camera.helper.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by huangyaping on 16/4/30.
 */
public class ImageAcitivity extends FragmentActivity {

    @ViewInject(R.id.imageView_pictrue_view_show)
    private ImageView imageView_pictrue_view_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ViewUtils.inject(this);
        Uri uri = getIntent().getData();
        imageView_pictrue_view_show.setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
