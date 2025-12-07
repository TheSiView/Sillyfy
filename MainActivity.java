package com.example.sillyfy;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

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
        songLength = findViewById(R.id.songLength);
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
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.music_test);
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopMusic();
                    }
                });
                mediaPlayer.start();
                handler.postDelayed(updateSeekBar, 100);
                break;
            case R.id.pauseButton:
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    handler.removeCallbacks(updateSeekBar);
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


    private SeekBar songLength;
    private final Handler handler = new Handler();
    private void setupSeekBar() {
        songLength.setMax(mediaPlayer.getDuration());
        songLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateSeekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume updates after seeking
                handler.postDelayed(updateSeekBar, 100);
            }
        });
        handler.postDelayed(updateSeekBar, 100);
    }
    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                songLength.setProgress(currentPosition);
            }
            handler.postDelayed(this, 100);
        }
    };
}



