package com.example.mehmet.dailyselfie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SelfieListFragment extends ListFragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    OnPhotoClickedListener mCallback;
    private ListView mListView;
    private PhotoAdapter mAdapter;
    private ArrayList<SelfieItem> mSelfieItem = new ArrayList<SelfieItem>();
    private Context mContext;
    private Uri mUri;

    //static factory method is used
    //because
    //http://www.androiddesignpatterns.com/2012/05/using-newinstance-to-instantiate.html
    public static SelfieListFragment newInstance() {
        SelfieListFragment f = new SelfieListFragment();
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        Activity activity;

        if (mContext instanceof Activity) {
            activity = (Activity) context;
            try {
                mCallback = (OnPhotoClickedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnPhotoClickedListener");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_selfie_list, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = getListView();
        mAdapter = new PhotoAdapter(mContext, mSelfieItem);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("alper", "onItemClick");
                mCallback.onPhotoClicked(mSelfieItem.get(position));
            }
        });
    }

    // Take a photo with the camera app
    public void dispatchTakePictureIntent() {
        Log.d("alper", "dispatchTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mUri = Uri.fromFile(photoFile);

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            }

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    private String getCurrentDate() {
        String currentDate = "";
        Date d = new Date(); // Initializes this Date instance to the current time.

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return currentDate = formatter.format(d);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = getCurrentDate();
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(mContext.getExternalFilesDir(null), imageFileName);

        return storageDir;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            SelfieItem mSelfie = new SelfieItem(mUri, getCurrentDate());
            mSelfieItem.add(mSelfie);

            mAdapter.notifyDataSetChanged();
        }
    }

    // Container Activity must implement this interface
    public interface OnPhotoClickedListener {
        void onPhotoClicked(SelfieItem item);
    }

}
