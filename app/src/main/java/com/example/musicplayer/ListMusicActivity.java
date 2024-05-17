package com.example.musicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ListMusicActivity extends AppCompatActivity {

    ArrayList<Song> songArrayList;
    ListView lvSongs;
    SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);

        lvSongs = findViewById(R.id.lvSongs);

        songArrayList = new ArrayList<>();
        // Replace this with your static list of songs
        addSongs();

        songsAdapter = new SongsAdapter(this, songArrayList);

        lvSongs.setAdapter(songsAdapter);



        // Remove permission check and getSongs() call

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Pass the entire list of songs instead of just one song
                Intent openMusicPlayer = new Intent(ListMusicActivity.this, MusicPlayerActivity.class);
                openMusicPlayer.putExtra("songList", songArrayList);
                openMusicPlayer.putExtra("position", position); // Optional: Pass position for the selected song
                startActivity(openMusicPlayer);
                Toast.makeText(ListMusicActivity.this, "DONE ON CLICK", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Method to add static songs
    private void addSongs() {

        String songTitle1 = "Lover";
        String songArtist1 = "Taylor Swift";
        int song1ResId = getResources().getIdentifier("lover", "raw", getPackageName());

        Song song1 = new Song(songTitle1, songArtist1, song1ResId);
        songArrayList.add(song1);


        String songTitle2 = "Chemtrails Over The Country Club";
        String songArtist2 = "Lana Del Rey ";
        int song2ResId = getResources().getIdentifier("chemtrails", "raw", getPackageName());

        Song song2 = new Song(songTitle2, songArtist2, song2ResId);
        songArrayList.add(song2);


        String songTitle3 = "Earned It (Fifty Shades Of Grey)";
        String songArtist3 = "The Weeknd";
        int song3ResId = getResources().getIdentifier("earned_it", "raw", getPackageName());

        Song song3 = new Song(songTitle3, songArtist3, song3ResId);
        songArrayList.add(song3);


        String songTitle4 = "На Самоті";
        String songArtist4 = "DOROFEEVA";
        int song4ResId = getResources().getIdentifier("na_samoti", "raw", getPackageName());

        Song song4 = new Song(songTitle4, songArtist4, song4ResId);
        songArrayList.add(song4);


        String songTitle5 = "Unwritten";
        String songArtist5 = "Natasha Bedingfield";
        int song5ResId = getResources().getIdentifier("unwritten", "raw", getPackageName());

        Song song5 = new Song(songTitle5, songArtist5, song5ResId);
        songArrayList.add(song5);


        String songTitle6 = "Baby shark";
        String songArtist6 = "Pinkfong";
        int song6ResId = getResources().getIdentifier("baby_shark", "raw", getPackageName());

        Song song6 = new Song(songTitle6, songArtist6, song6ResId);
        songArrayList.add(song6);


        String songTitle7 = "Skin And Bones";
        String songArtist7 = "David Kushner";
        int song7ResId = getResources().getIdentifier("skin", "raw", getPackageName());

        Song song7 = new Song(songTitle7, songArtist7, song7ResId);
        songArrayList.add(song7);


        String songTitle8 = "Oxytocin";
        String songArtist8 = "Billie Eilish";
        int song8ResId = getResources().getIdentifier("oxytocin", "raw", getPackageName());

        Song song8 = new Song(songTitle8, songArtist8, song8ResId);
        songArrayList.add(song8);


        String songTitle9 = "RUNNING MILES";
        String songArtist9 = "Hippie Sabotage";
        int song9ResId = getResources().getIdentifier("running_miles", "raw", getPackageName());

        Song song9 = new Song(songTitle9, songArtist9, song9ResId);
        songArrayList.add(song9);


        String songTitle10 = "Partition";
        String songArtist10 = "Beyoncé";
        int song10ResId = getResources().getIdentifier("partition", "raw", getPackageName());

        Song song10 = new Song(songTitle10, songArtist10, song10ResId);
        songArrayList.add(song10);


    }
}