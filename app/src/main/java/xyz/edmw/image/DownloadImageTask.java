package xyz.edmw.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private final OnPostExecuteListener callback;

    public DownloadImageTask(OnPostExecuteListener callback) {
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new URL(params[0]).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        callback.onPostExecute(bitmap);
    }

    public interface OnPostExecuteListener {
        void onPostExecute(@Nullable Bitmap bitmap);
    }
}