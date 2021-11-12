package com.example.miwoklanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
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

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

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
        setContentView(R.layout.activity_family);

        //for showing back button in action bar
        assert getSupportActionBar() != null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set audio focus request
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<wordClass> words = new ArrayList<wordClass>();
        words.add(new wordClass("Father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new wordClass("Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new wordClass("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new wordClass("Daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new wordClass("Older Brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new wordClass("Younger Brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new wordClass("Older Sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new wordClass("Younger Sister", "Kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new wordClass("Grandmother", "Ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new wordClass("Grandfather", "Paapa", R.drawable.family_grandfather, R.raw.family_grandfather));
        WordAdapter itemsAdapter = new WordAdapter(this, words);
        ListView gridView = (ListView) findViewById(R.id.familyList);
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