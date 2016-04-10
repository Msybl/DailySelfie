package com.example.mehmet.dailyselfie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ArrayList<SelfieItem> mSelfieItem = new ArrayList<SelfieItem>();
    private ListView mListView;
    private PhotoAdapter listAdapter;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.listView);
        listAdapter = new PhotoAdapter(MainActivity.this, mSelfieItem);
        mListView.setAdapter(listAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate actions into action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                // User chose the "camera" action
                dispatchTakePictureIntent();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Take a photo with the camera app
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
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

    //Get the thumbnail
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap imageBitmap =
                    decodeSampledBitmapFromPath(mUri, 100, 100);

            SelfieItem mSelfie = new SelfieItem(imageBitmap, getCurrentDate());
            mSelfieItem.add(mSelfie);

            listAdapter.notifyDataSetChanged();
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
        File storageDir = new File(getExternalFilesDir(null), imageFileName);

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

}
