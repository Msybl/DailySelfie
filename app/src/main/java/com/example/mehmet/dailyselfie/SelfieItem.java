package com.example.mehmet.dailyselfie;

import android.graphics.Bitmap;
import android.net.Uri;
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

    Uri mBitmapUri;
    String mDate;

    public SelfieItem(Uri mBitmapUri, String mDate) {
        this.mBitmapUri = mBitmapUri;
        this.mDate = mDate;
    }

    protected SelfieItem(Parcel in) {
        mBitmapUri = (Uri) in.readValue(Bitmap.class.getClassLoader());
        mDate = in.readString();
    }

    public Uri getBitmapUri() {
        return mBitmapUri;
    }

    public void setBitmapUri(Uri mBitmapUri) {
        this.mBitmapUri = mBitmapUri;
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
        dest.writeValue(mBitmapUri);
        dest.writeString(mDate);
    }
}
