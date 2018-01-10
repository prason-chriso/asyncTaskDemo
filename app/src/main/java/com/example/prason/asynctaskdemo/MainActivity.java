package com.example.prason.asynctaskdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static ProgressDialog progressDialog;
    String url = "http://www.planwallpaper.com/#static/images/general-night-golden-gate-bridge-hd-wallpapers-golden-gate-bridge-wallpaper.jpg";
    Button button ;
    static ImageView imageView;

    String []permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    boolean permissionCheck = true;
    int REQUEST_CODE_PERMISSION =   1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //checking for the run time permission
        if(ActivityCompat.checkSelfPermission(this,permission[0]) != MockPackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,permission[1]) !=MockPackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,permission,REQUEST_CODE_PERMISSION);

        }


        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.download);
        imageView = (ImageView)findViewById(R.id.showImage);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("downloading content");
        progressDialog.setMax(100);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length ==2 && grantResults[0] == MockPackageManager.PERMISSION_GRANTED && grantResults[1] ==MockPackageManager.PERMISSION_GRANTED){
            permissionCheck = true;
        }
        else{
            permissionCheck = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFile().execute(url);
            }
        });
    }
}
