package com.example.mehmet.dailyselfie;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoFragment extends Fragment {

    private final static String SELFIE_ITEM = "SelfieItem";
    private SelfieItem item;
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
        SelfieItem item = (SelfieItem) args.getParcelable(SELFIE_ITEM);

        ivPhoto.setImageBitmap(item.getBitmap());
        tvDate.setText(item.getDate());

        return view;
    }

}
