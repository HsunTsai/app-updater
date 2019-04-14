package com.hsun.appupdater;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

class DownloadFileTask extends AsyncTask<String, String, String> {

    private Listener listener;

    public interface Listener {
        public void onStartDownload();

        public void onProgress(int progress);

        public void onFinishDownload();

        public void onError();
    }

    public DownloadFileTask setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (null != listener) listener.onStartDownload();
    }

    @Override
    protected String doInBackground(String... params) {
        int count;
        try {
            Log.i("Url", params[0]);
            Log.i("download_path", params[1]);
            URL url = new URL(params[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream(params[1]);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

            if (null != listener) listener.onFinishDownload();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != listener) listener.onError();
        }

        return null;
    }

    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        if (null != listener) listener.onProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String file_url) {
//        // dismiss the dialog after the file was downloaded
//        dismissDialog(progress_bar_type);
//
//        // Displaying downloaded image into image view
//        // Reading image path from sdcard
//        String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
//        // setting downloaded into image view
//        my_image.setImageDrawable(Drawable.createFromPath(imagePath));
    }
}