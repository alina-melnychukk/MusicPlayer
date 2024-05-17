package com.example.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTime, tvDuration, tvTitle, tvArtist;
    SeekBar seekBarTime, seekBarVolume;
    Button btnPlay;
    MediaPlayer musicPlayer;
    ArrayList<Song> songArrayList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve the list of songs and position from the Intent
        songArrayList = (ArrayList<Song>) getIntent().getSerializableExtra("songList");
        position = getIntent().getIntExtra("position", 0);

        Toolbar toolbar = new Toolbar(getApplicationContext());
        setSupportActionBar(toolbar);

        tvTime = findViewById(R.id.tvTime);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        btnPlay = findViewById(R.id.btnPlay);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);

        Song song = songArrayList.get(position);
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());

        musicPlayer = MediaPlayer.create(this, song.getResId());
        musicPlayer.start();
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);

        String duration = millisecondsToString(musicPlayer.getDuration());
        tvDuration.setText(duration);

        btnPlay.setOnClickListener(this);

        seekBarVolume.setProgress(50);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isfromUser) {
                float volume = progress / 100f;
                musicPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarTime.setMax(musicPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isfromUser) {
                if (isfromUser) {
                    musicPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicPlayer != null) {
                    if (musicPlayer.isPlaying()) {
                        try {
                            final double current = musicPlayer.getCurrentPosition();
                            final String elapsedTime = millisecondsToString((int) current);

                            runOnUiThread(() -> {
                                tvTime.setText(elapsedTime);
                                seekBarTime.setProgress((int) current);
                            });

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }).start();
    }

    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes + ":";
        if (seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPlay) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.play2);
            } else {
                musicPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.pause1);
            }
        }
    }

    public void onBackButtonClick(View view) {
        if (view.getId() == R.id.btnBack) {
            finish();
            if (musicPlayer.isPlaying()) {
                musicPlayer.stop();
            }
        }
    }

    public void onNextButtonClick(View view) {
        // Implement logic to play the next song
        playNextSong();
    }

    public void onPreviousButtonClick(View view) {
        // Implement logic to play the previous song
        playPreviousSong();
    }

    private void playNextSong() {
        // Check if there is a next song available
        if (position < songArrayList.size() - 1) {
            position++;
            playSong(songArrayList.get(position));
        } else {
            // If there is no next song, loop back to the first song
            position = 0;
            playSong(songArrayList.get(position));
        }
    }

    private void playPreviousSong() {
        // Check if there is a previous song available
        if (position > 0) {
            position--;
            playSong(songArrayList.get(position));
        } else {
            // If there is no previous song, play the last song in the list
            position = songArrayList.size() - 1;
            playSong(songArrayList.get(position));
        }
    }

    private void playSong(Song song) {
        // Stop the current song
        if (musicPlayer.isPlaying()) {
            musicPlayer.stop();
            musicPlayer.release();
        }

        // Play the new song
        musicPlayer = MediaPlayer.create(this, song.getResId());
        musicPlayer.start();
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);

        // Update the UI with the new song details
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());
        String duration = millisecondsToString(musicPlayer.getDuration());
        tvDuration.setText(duration);
    }

    public void onRandomButtonClick(View view) {
        Button btnRandom = findViewById(R.id.btnRandom);
        String tag = btnRandom.getTag().toString();

        if (tag.equals("mix")) {
            shuffleSongs();
            btnRandom.setBackgroundResource(R.drawable.circle); // Змінюємо значок на circle
            btnRandom.setTag("circle"); // Оновлюємо тег кнопки
        } else if (tag.equals("circle")) {
            // Якщо значок circle, відтворюємо пісні в звичайному порядку
            playSong(songArrayList.get(position)); // Відтворити пісню з початковою позицією
            btnRandom.setBackgroundResource(R.drawable.mix); // Змінюємо значок на mix
            btnRandom.setTag("mix"); // Оновлюємо тег кнопки
        }
    }


    private void shuffleSongs() {
        Collections.shuffle(songArrayList); // Перемішуємо список пісень

        // Оновлюємо поточну позицію пісні після перемішування списку
        for (int i = 0; i < songArrayList.size(); i++) {
            if (songArrayList.get(i).getTitle().equals(tvTitle.getText().toString())) {
                position = i;
                break;
            }
        }

        // Після перемішування оновлюємо інтерфейс, якщо відтворення увімкнене
        if (musicPlayer.isPlaying()) {
            playSong(songArrayList.get(position));
        }
    }


}