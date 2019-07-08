package com.haobi.news_1.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * Created by 15739 on 2019/7/8.
 */

public class ImageUtil {
    //判断文件是否下载（1文件是否存在2文件内容是否完整）
    public static boolean checkImageIsDownload(String imageName){
        File image = getFileByName(imageName);
        if (image.exists()){
            Bitmap bitmap = getImageBitMapByFile(image);
            if (bitmap != null){
                return true;
            }
        }
        return false;
    }

    public static File getFileByName(String imageName){
        File SD = Environment.getExternalStorageDirectory();
        File cacheFile = new File(SD, Constant.CACHE);
        File image = new File(cacheFile, imageName+".jpg");
        return image;
    }

    public static Bitmap getImageBitMapByFile(File image){
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        return bitmap;
    }
}
