package com.example.mehmet.dailyselfie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoFragment extends Fragment {

    private static final int TARGET_HEIGHT = 500;
    private static final int TARGET_WIDTH = 500;

    private final static String SELFIE_ITEM = "SelfieItem";
    private SelfieItem selfieItem;
    private ImageView ivPhoto;
    private TextView tvDate;

    //static factory method is used
    //because
    //http://www.androiddesignpatterns.com/2012/05/using-newinstance-to-instantiate.html
    public static PhotoFragment newInstance(SelfieItem item) {
        PhotoFragment f = new PhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELFIE_ITEM, item);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_photo, null);

        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        tvDate = (TextView) view.findViewById(R.id.tvDate);

        Bundle args = getArguments();
        selfieItem = (SelfieItem) args.getParcelable(SELFIE_ITEM);

        // retrieve SelfieItem's Uri and Date
        Uri selfieItemUri = selfieItem.getBitmapUri();
        String selfieItemDate = selfieItem.getDate();


        // TODO: We need to learn orientation of photo and screen dimentions
        // So that we can fit it to screen and rotate if necessary

        // Get display dimentions
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(metrics);

        Log.i("metrics", "witdh: "+metrics.widthPixels+"  height: "+metrics.heightPixels);




        // decode Bitmap from item's uri
        Bitmap selfieItemPhoto = SelfieUtility.decodeSampledBitmapFromPath(selfieItemUri,
                TARGET_HEIGHT, TARGET_WIDTH);

        ivPhoto.setImageBitmap(selfieItemPhoto);
        tvDate.setText(selfieItemDate);

        return view;
    }



}
