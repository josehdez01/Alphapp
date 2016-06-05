package kende.alphapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import hugo.weaving.DebugLog;

public class MainActivity extends Activity implements CameraPreview.OnQrDecodedListener {

    public final static int PERMISSIONS_REQUEST = 100;

    private CameraPreview mCameraPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mCameraPreview = (CameraPreview) findViewById(R.id.surface);

        mCameraPreview.setOnQrDecodedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onQrDecoded(String s) {
        Log.d("Readed:", s);
        if(s.equals("A")){
            Intent i = VideoPlayer.newIntent(getApplicationContext(), s.toLowerCase());
            startActivity(i);
        }else if(s.equals("E")){
            Intent i = VideoPlayer.newIntent(getApplicationContext(), s.toLowerCase());
            startActivity(i);
        }else if(s.equals("I")){
            Intent i = VideoPlayer.newIntent(getApplicationContext(), s.toLowerCase());
            startActivity(i);
        }else if(s.equals("O")){
            Intent i = VideoPlayer.newIntent(getApplicationContext(), s.toLowerCase());
            startActivity(i);
        }else if(s.equals("U")) {
            Intent i = VideoPlayer.newIntent(getApplicationContext(), s.toLowerCase());
            startActivity(i);
        }
    }

    @Override
    public void onQrNotFound() {

    }

    @DebugLog
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        boolean success = mCameraPreview.acquireCamera(getWindowManager()
                .getDefaultDisplay().getRotation());
        if (!success) {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.dlg_alert_msg))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.dlg_alert_ok_btn_caption),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MainActivity.this.finish();
                                    dialog.dismiss();
                                }
                            })
                    .create().show();
        }
    }

    @DebugLog
    @Override
    protected void onPause() {
        mCameraPreview.releaseCamera();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST &&
                grantResults.length == 1 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate();
            return;
        }
        finish();
    }
}