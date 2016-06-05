package kende.alphapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class VideoPlayer extends Activity implements SurfaceHolder.Callback {

    public static Intent newIntent(Context context, String video){
        Intent i = new Intent(context, VideoPlayer.class);
        i.putExtra("videoPath", video);
        return i;
    }
    private SurfaceHolder videoHolder;
    private SurfaceView videoView;
    private MediaPlayer mp;
    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = (SurfaceView) findViewById(R.id.fullscreen_content);
        videoHolder = videoView.getHolder();
        mp = new MediaPlayer();
        videoHolder.addCallback(this);
        videoPath = getIntent().getStringExtra("videoPath");
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + videoPath;

        Uri uri = Uri.parse(uriPath);
        try {
            mp.setDataSource(getApplicationContext(), uri);
            mp.setDisplay(holder);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
