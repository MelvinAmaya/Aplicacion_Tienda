package com.example.aplicacion_tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import javax.net.ssl.SNIHostName;

public class MainActivity extends AppCompatActivity {
    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video=(VideoView) findViewById(R.id.splascreen);

        video.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.tutiendida));
        video.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),pagina1.class);
                startActivity(i);
                finish();
            }
        },5000);
    }
}