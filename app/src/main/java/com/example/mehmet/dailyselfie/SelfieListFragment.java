package com.example.mehmet.dailyselfie;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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


public class SelfieListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private ListView mListView;
    private PhotoAdapter mAdapter;
    private ArrayList<SelfieItem> mSelfieItem = new ArrayList<SelfieItem>();
    private Context mContext;
    private Uri mUri;

    public static SelfieListFragment newInstance() {
        SelfieListFragment f = new SelfieListFragment();
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
    }

    public void onFragmentResult() {
        Bitmap imageBitmap =
                decodeSampledBitmapFromPath(mUri, 100, 100);

        SelfieItem mSelfie = new SelfieItem(imageBitmap, getCurrentDate());
        mSelfieItem.add(mSelfie);

        mAdapter.notifyDataSetChanged();
    }

    // Take a photo with the camera app
    public void dispatchTakePictureIntent() {
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

    private Bitmap decodeSampledBitmapFromPath(Uri uri, int targetHeight, int targetWidth) {

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
