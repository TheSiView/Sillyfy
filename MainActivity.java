package com.example.sillyfy;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mediaPlayer = null;
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void music(View view) {
        switch (view.getId()) {
            case R.id.playButton:
                if (mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(this, R.raw.music_test);
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopMusic();
                    }
                });
            mediaPlayer.start();
            break;
            case R.id.pauseButton:
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
            break;
            case R.id.rewindButton:
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.start();
                stopMusic();
            }
            break;

            }
    }

    private void stopMusic() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMusic();
    }
}