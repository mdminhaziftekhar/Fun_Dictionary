package com.example.miwoklanguage;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private final int mBackgroundColor;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };


    public WordAdapter(Activity context, List<Word> words, int backgroundColor) {
        super(context, 0, words);
        this.mBackgroundColor = backgroundColor;
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final Word currentWord = getItem(position);

        ImageView miwokImageView = (ImageView) listItemView.findViewById(R.id.miwok_picture);
        if (currentWord.hasImage()) {
            miwokImageView.setImageResource(currentWord.getImageResourceId());
            miwokImageView.setVisibility(View.VISIBLE);
        } else {
            miwokImageView.setVisibility(View.GONE);
        }

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_word);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultLangTextView = (TextView) listItemView.findViewById(R.id.default_lang_word);
        defaultLangTextView.setText(currentWord.getDefaultTranslation());

        LinearLayout textLayout = (LinearLayout) listItemView.findViewById(R.id.text_layout);
        textLayout.setBackgroundResource(mBackgroundColor);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getContext(), currentWord.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return listItemView;
    }

    void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}
