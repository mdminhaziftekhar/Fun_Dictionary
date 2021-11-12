package com.example.miwoklanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_phrases);

        //Code for back button in actionbar
        assert getSupportActionBar() != null; // null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set audio focus request
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<wordClass> words = new ArrayList<wordClass>();
        words.add(new wordClass("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new wordClass("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new wordClass("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new wordClass("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new wordClass("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new wordClass("Are you coming?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new wordClass("Yes, I’m coming.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new wordClass("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new wordClass("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new wordClass("Come here.", "әnni'nem", R.raw.phrase_come_here));
        phraseAdapter itemsAdapter = new phraseAdapter(this, words);
        ListView gridView = (ListView) findViewById(R.id.phrasesList);
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