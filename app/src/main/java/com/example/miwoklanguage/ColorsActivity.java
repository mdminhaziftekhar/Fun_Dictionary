package com.example.miwoklanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    //for using later as OnCompletionListener
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    //AudioManager for audiofocus
    private AudioManager audioManager;

    //Audio change listener
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                //pause playback
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //Resume Playback
                mMediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //Stop Playback
                releaseMediaPlayer();
            }
        }
    };

    //Releases the media if app get stopped
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    //releases the media if app gets distroyed
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        //for showing back button in action bar
        assert getSupportActionBar() != null; // null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button

        //set audio focus request
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<wordClass> words = new ArrayList<wordClass>();
        words.add(new wordClass("Red", "Wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new wordClass("Green", "Chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new wordClass("Brown", "Takaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new wordClass("Gray", "Topoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new wordClass("Black", "Kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new wordClass("White", "Kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new wordClass("Dusty Yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new wordClass("Mustard Yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //WordAdapter is the new custom adapter
        WordAdapter itemsAdapter = new WordAdapter(this, words);
        ListView gridView = (ListView) findViewById(R.id.colorList);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wordClass word = words.get(i);
                releaseMediaPlayer();

                //Request audio focus for playback
                int result = audioManager.requestAudioFocus(afChangeListener,
                        //Stream type
                        AudioManager.STREAM_MUSIC,
                        //Duration
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                        );
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //we have audio focus now
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
                    mMediaPlayer.start();

                    // what to do after completing the media playback //
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
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
        }

        //Abandon Audio focus
        audioManager.abandonAudioFocus(afChangeListener);
    }
}