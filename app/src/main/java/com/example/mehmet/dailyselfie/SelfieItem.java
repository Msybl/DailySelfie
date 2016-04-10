package com.example.mehmet.dailyselfie;

import android.graphics.Bitmap;


public class SelfieItem {

    Bitmap mBitmap;
    String mDate;

    public SelfieItem(Bitmap mBitmap, String mDate) {
        this.mBitmap = mBitmap;
        this.mDate = mDate;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }
}
