package com.example.miwoklanguage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class NumbersActivity extends AppCompatActivity {
    //media player instance to playback
    private MediaPlayer mMediaPlayer;

    //To use the oncompletion listener later,
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    //Audio manager instance to manage of handle audio interruptions
    private AudioManager audioManager;

    //AudioChangeListener
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                          focusChange ==  AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //pause playback
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //Resume Playback
                        mMediaPlayer.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //Stop playback
                        releaseMediaPlayer();
                    }
                }
            };

    //define what happens after the app stops
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    //define what happens after the app stops
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    //this method need to be overridden to show back button in action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //Code for back button in actionbar
        assert getSupportActionBar() != null; // null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set the request for audio focus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<wordClass> words = new ArrayList<wordClass>();
        words.add(new wordClass("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new wordClass("Two", "Otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new wordClass("Three", "Tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new wordClass("Four", "Oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new wordClass("Five", "Massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new wordClass("Six", "Temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new wordClass("Seven", "Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new wordClass("Eight", "Kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new wordClass("Nine", "Wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new wordClass("Ten", "Na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(this, words);
        ListView gridView = (ListView) findViewById(R.id.list);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                wordClass word = words.get(position);
                releaseMediaPlayer();

                //Request audio focus for playback
                int result = audioManager.requestAudioFocus(afChangeListener,
                        //use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permission focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //we have audioFocus now
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });



    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.stop();
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Abandon audio focus
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}