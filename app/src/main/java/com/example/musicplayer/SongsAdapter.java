package com.example.musicplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends ArrayAdapter<Song> {

    private Context mContext;
    private ArrayList<Song> mSongs;

    public SongsAdapter(@NonNull Context context, @NonNull ArrayList<Song> songs) {
        super(context, 0, songs);
        mContext = context;
        mSongs = songs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvArtist = convertView.findViewById(R.id.tvArtist);
        ImageButton btnOptions = convertView.findViewById(R.id.btnOptions);

        Song song = getItem(position);
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());

        // Set click listener for the entire list item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the entire list of songs instead of just one song
                Intent openMusicPlayer = new Intent(mContext, MusicPlayerActivity.class);
                openMusicPlayer.putExtra("songList", mSongs);
                openMusicPlayer.putExtra("position", position); // Optional: Pass position for the selected song
                mContext.startActivity(openMusicPlayer);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionsDialog(position);
            }
        });

        return convertView;
    }



    private void showOptionsDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Options");
        builder.setItems(new CharSequence[]{"Delete"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    deleteSong(position);
                    break;
            }
        });
        builder.show();
    }

    private void deleteSong(int position) {
        mSongs.remove(position);
        notifyDataSetChanged();
    }
}
