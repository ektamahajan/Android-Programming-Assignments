package com.example.newuser.lab8_asynctask_thread;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/**
 * Created by newuser on 3/22/16.
 */

public class MyDownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap>{
    private final WeakReference<ImageView> imageViewWeakReference;

    public MyDownloadImageAsyncTask(ImageView imv){
        imageViewWeakReference = new WeakReference<ImageView>(imv);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = null;
        for (String url : urls)
        {
            bitmap = MyUtility.downloadImageusingHTTPGetRequest(url);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewWeakReference != null && bitmap != null)
        {
          final ImageView imageView = imageViewWeakReference.get();
          if (imageView !=null)
          {
              imageView.setImageBitmap(bitmap);
          }

        }
      //  super.onPostExecute(bitmap);
    }
}
