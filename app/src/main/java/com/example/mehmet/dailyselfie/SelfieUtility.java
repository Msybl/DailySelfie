package com.example.mehmet.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * The Utility pattern is a software pattern that is used for
 * utility classes that do not require instantiation and only have static methods
 * <p/>
 * In computer programming, a utility class is a class that defines a set of methods
 * that perform common, often re-used functions. Most utility classes define these common methods under static scope
 */
public class SelfieUtility {

    public static Bitmap decodeSampledBitmapFromPath(Uri uri, int targetHeight, int targetWidth) {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmapOptions = new BitmapFactory.Options();
        bmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri.getPath(), bmapOptions);
        int origHeight = bmapOptions.outHeight;
        int origWidth = bmapOptions.outWidth;

        // Determine how much to scale down the image
        int scale = Math.min(origHeight / targetHeight, origWidth / targetWidth);
        // Decode the image file into a Bitmap sized to fill the View
        bmapOptions.inJustDecodeBounds = false;
        bmapOptions.inSampleSize = scale;

        return BitmapFactory.decodeFile(uri.getPath(), bmapOptions);
    }
}
