package com.example.mehmet.dailyselfie;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Mehmet on 15.5.2016.
 */
public class AsyncSelfieTask extends AsyncTask<Uri, Void, Bitmap> {

    WeakReference<ImageView> imageViewWeakReference;
    int mHeight = 0;
    int mWitdh = 0;

    public AsyncSelfieTask(ImageView imageView, int height, int witdh) {
        mHeight = height;
        mWitdh = witdh;
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Uri... params) {
        return SelfieUtility.decodeSampledBitmapFromPath(params[0], mHeight, mWitdh);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(imageViewWeakReference != null & bitmap != null){
            ImageView imageView = imageViewWeakReference.get();
            if(imageView != null)
                imageView.setImageBitmap(bitmap);
        }
    }
}
