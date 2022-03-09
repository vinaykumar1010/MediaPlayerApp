package com.vinappstudio.mediaplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar mseekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mseekbar = findViewById(R.id.seekBar_ID);

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.john_wick);
        mseekbar.setMax(mediaPlayer.getDuration());

        mseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // if from user side , mediaplayer will listen to  seekbar progress
                if (b) {
                    mediaPlayer.seekTo(i);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Integer duration = mediaPlayer.getDuration();
                String mduration = String.valueOf(duration / 1000);
                Toast.makeText(getApplicationContext(), "duration" + mduration, Toast.LENGTH_LONG).show();
            }
        });

        playButton = findViewById(R.id.button_ID);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    // stop and give option to start again
                    pauseMusic();
                } else
                    startMusic();
            }
        });
    }

    public void pauseMusic() {
        // means media player object is still alive
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playButton.setText("Play");
        }
    }

    public void startMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            playButton.setText("Pause");

        }
    }

    // creating objects take a lot of memory so we  use on Destroy method to clean the memory
    // very very important
    @Override
    protected void onDestroy() {
        if (mediaPlayer != null & mediaPlayer.isPlaying()) {
            // not null means object is alive
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}