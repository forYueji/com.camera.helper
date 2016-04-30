package com.camera.helper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.AsyncTask;

import com.camera.helper.camera.CameraTool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by huangyaping on 16/4/30.
 */
public class PictureCallbackDataHelper extends AsyncTask<Void,Void,String> {
    private int PHOTO_SIZE = 2000;
    private Context context;
    private byte[] data;


    public PictureCallbackDataHelper (Context context , byte [] data){
        this.context = context;
        this.data = data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        try{
            return saveToSDCard(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        EventBus.getDefault().post(result);
    }

    public String saveToSDCard(byte[] data) throws IOException {
        Bitmap croppedImage;

        //获得图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        PHOTO_SIZE = options.outHeight > options.outWidth ? options.outWidth : options.outHeight;
        int height = options.outHeight > options.outWidth ? options.outHeight : options.outWidth;
        options.inJustDecodeBounds = false;
        Rect r;
        if (CameraTool.getInstance().getCurrentCameraId() == 1) {
            r = new Rect(height - PHOTO_SIZE, 0, height, PHOTO_SIZE);
        } else {
            r = new Rect(0, 0, PHOTO_SIZE, PHOTO_SIZE);
        }
        try {
            croppedImage = decodeRegionCrop(data, r);
        } catch (Exception e) {
            return null;
        }
        String imagePath = saveToFile(FileUtils.getInst(context).getSystemPhotoPath(), true,
                croppedImage);
        croppedImage.recycle();
        return imagePath;
    }

    private Bitmap decodeRegionCrop(byte[] data, Rect rect) {

        InputStream is = null;
        System.gc();
        Bitmap croppedImage = null;
        try {
            is = new ByteArrayInputStream(data);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);

            try {
                croppedImage = decoder.decodeRegion(rect, new BitmapFactory.Options());
            } catch (IllegalArgumentException e) {
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeStream(is);
        }
        Matrix m = new Matrix();
        m.setRotate(90, PHOTO_SIZE / 2, PHOTO_SIZE / 2);
        if (CameraTool.getInstance().getCurrentCameraId() == 1) {
            m.postScale(1, -1);
        }
        Bitmap rotatedImage = Bitmap.createBitmap(croppedImage, 0, 0, PHOTO_SIZE, PHOTO_SIZE, m, true);
        if (rotatedImage != croppedImage)
            croppedImage.recycle();
        return rotatedImage;
    }


    //保存图片文件
    public String saveToFile(String fileFolderStr, boolean isDir, Bitmap croppedImage) throws FileNotFoundException, IOException {
        File jpgFile;
        if (isDir) {
            File fileFolder = new File(fileFolderStr);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
            String filename = format.format(date) + ".jpg";
            if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                FileUtils.getInst(context).mkdir(fileFolder);
            }
            jpgFile = new File(fileFolder, filename);
        } else {
            jpgFile = new File(fileFolderStr);
            if (!jpgFile.getParentFile().exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                FileUtils.getInst(context).mkdir(jpgFile.getParentFile());
            }
        }
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流

        croppedImage.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        IOUtil.closeStream(outputStream);
        return jpgFile.getPath();
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    public PictureCallbackDataHelper(byte[] data,Context context,int rotation) {
//        this.data = data;
//        this.context = context;
//        this.rotation = rotation;
//    }
//
//    @Override
//    protected String doInBackground(Void... params) {
//        try {
//            return saveToSDCard(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public String saveToSDCard(byte[] data) throws IOException {
//        Log.e("------", "-----------------<<< + " + data);
//        Bitmap croppedImage;
//
//        //获得图片大小
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(data, 0, data.length, options);
//
//        PHOTO_SIZE = options.outHeight > options.outWidth ? options.outWidth : options.outHeight;
//        int height = options.outHeight > options.outWidth ? options.outHeight : options.outWidth;
//        options.inJustDecodeBounds = false;
//        Rect r;
//        if (CameraTool.getInstance().getCurrentCameraId() == 1) {
//            r = new Rect(height - PHOTO_SIZE, 0, height, PHOTO_SIZE);
//        } else {
//            r = new Rect(0, 0, PHOTO_SIZE, PHOTO_SIZE);
//        }
//        try {
//            croppedImage = decodeRegionCrop(data, r);
//        } catch (Exception e) {
//            return null;
//        }
//        String imagePath = saveToFile(FileUtils.getInst(context).getSystemPhotoPath(), true, croppedImage);
//        croppedImage.recycle();
//        return imagePath;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        Log.e("-----" , "------------>>>>" + result);
//        if (!TextUtils.isEmpty(result)){
//            showPicActivity(result);
//        }
//        super.onPostExecute(result);
//    }
//
//
//    public void showPicActivity(String result){
//        EventBus.getDefault().post(result);
//    }
//
//    private Bitmap decodeRegionCrop(byte[] data, Rect rect) {
//        InputStream is = null;
//        System.gc();
//        Bitmap croppedImage = null;
//        try {
//            is = new ByteArrayInputStream(data);
//            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
//
//            try {
//                croppedImage = decoder.decodeRegion(rect, new BitmapFactory.Options());
//            } catch (IllegalArgumentException e) {
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        } finally {
//            IOUtil.closeStream(is);
//        }
//        Matrix m = new Matrix();
//        m.setRotate(90, PHOTO_SIZE / 2, PHOTO_SIZE / 2);
//        if (CameraTool.getInstance().getCurrentCameraId() == 1) {
//            m.postScale(1, -1);
//        }
//        Bitmap rotatedImage = Bitmap.createBitmap(croppedImage, 0, 0, PHOTO_SIZE, PHOTO_SIZE, m, true);
//        if (rotatedImage != croppedImage)
//            croppedImage.recycle();
//        return rotatedImage;
//    }
//
//    public String saveToFile(String fileFolderStr, boolean isDir, Bitmap croppedImage) throws FileNotFoundException, IOException {
//        File jpgFile;
//        if (isDir) {
//            File fileFolder = new File(fileFolderStr);
//            Date date = new Date();
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
//            String filename = format.format(date) + ".jpg";
//            if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
//                FileUtils.getInst(context).mkdir(fileFolder);
//            }
//            jpgFile = new File(fileFolder, filename);
//        } else {
//            jpgFile = new File(fileFolderStr);
//            if (!jpgFile.getParentFile().exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
//                FileUtils.getInst(context).mkdir(jpgFile.getParentFile());
//            }
//        }
//        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
//
//        croppedImage.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
//        IOUtil.closeStream(outputStream);
//        return jpgFile.getPath();
//    }
}
