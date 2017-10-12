package Util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Mr.Qu on 2017/10/12.
 */

public class PictureUtil {
    /*进行估算缩放*/
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;/**/
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

//      指出如何缩放
        int inSampleSize = 2;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth>srcHeight){
                inSampleSize=Math.round(srcHeight/destHeight);
            }else{
                inSampleSize=Math.round(srcWidth/destWidth);
            }
        }

        options=new BitmapFactory.Options();
        options.inSampleSize=inSampleSize;

        return BitmapFactory.decodeFile(path,options);
    }
//    缩放方法
    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);

        return getScaledBitmap(path,size.x,size.y);

    }
}
