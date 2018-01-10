package com.example.prason.asynctaskdemo;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Prason on 7/14/2017.
 */

class DownloadFile extends AsyncTask<String, Integer,String>{


    String imgurl;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        double count;
        imgurl = params[0];
        try {
            URL  url = new URL(imgurl);
            URLConnection  urlConnection = url.openConnection();
            urlConnection.connect();

            int filelength = urlConnection.getContentLength();

            InputStream  inputStream = new BufferedInputStream(url.openStream(),8192);
            OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/Download/downloadfile.jpg");
            byte data[] = new byte[8192];
            long total = 0;

            while((count = inputStream.read(data))!=0){
                total = (long)(total+count);
                publishProgress((int)(total*100)/filelength);
                outputStream.write(data,0,(int)count);

            }
            inputStream.close();
            outputStream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        MainActivity.progressDialog.setProgress(values[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.progressDialog.dismiss();
        String filePath = Environment.getExternalStorageDirectory().getPath()+"/Download/downloadfile.jpg";
        MainActivity.imageView.setImageDrawable(Drawable.createFromPath(filePath));

    }
}
