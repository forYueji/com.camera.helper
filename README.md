# com.camera.helper

自定义 Camera 实现相机拍照

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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
