package com.example.mehmet.dailyselfie;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/*
    How can I make my custom objects be Parcelable?
    http://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-be-parcelable
    http://www.parcelabler.com/

 */
public class SelfieItem implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SelfieItem> CREATOR = new Parcelable.Creator<SelfieItem>() {
        @Override
        public SelfieItem createFromParcel(Parcel in) {
            return new SelfieItem(in);
        }

        @Override
        public SelfieItem[] newArray(int size) {
            return new SelfieItem[size];
        }
    };
    Bitmap mBitmap;
    String mDate;

    public SelfieItem(Bitmap mBitmap, String mDate) {
        this.mBitmap = mBitmap;
        this.mDate = mDate;
    }

    protected SelfieItem(Parcel in) {
        mBitmap = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        mDate = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mBitmap);
        dest.writeString(mDate);
    }
}
