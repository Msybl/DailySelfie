package com.example.mehmet.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoAdapter extends BaseAdapter {
    private static LayoutInflater mInflater = null;
    ArrayList<SelfieItem> mSelfieItem;
    Context context;

    public PhotoAdapter(Context ctx, ArrayList<SelfieItem> mSelfieItem) {
        context = ctx;
        this.mSelfieItem = mSelfieItem;
        mInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mSelfieItem.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // "view holder" design pattern is used.
        // Why is explained in below link:
        // http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder

        ViewHolder mHolder = null;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.selfie_item, null);
            mHolder = new ViewHolder();
            mHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.imageView1);
            mHolder.tvPhotoID = (TextView) convertView.findViewById(R.id.text1);

            // store the holder with the view.
            convertView.setTag(mHolder);

        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.ivPhoto.setImageBitmap(mSelfieItem.get(position).getBitmap());
        mHolder.tvPhotoID.setText(mSelfieItem.get(position).getDate());

        return convertView;

    }

    static class ViewHolder {
        TextView tvPhotoID;
        ImageView ivPhoto;
    }
}