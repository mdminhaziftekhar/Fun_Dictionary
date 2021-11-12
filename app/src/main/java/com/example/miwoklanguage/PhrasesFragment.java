package com.example.miwoklanguage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PhrasesFragment extends Fragment {
    private WordAdapter phrasesWordsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_list, container, false);

        List<Word> phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        phrasesWordsAdapter = new WordAdapter(getActivity(), phrases, R.color.category_phrases);
        ListView listView = (ListView) view.findViewById(R.id.list_words);
        listView.setAdapter(phrasesWordsAdapter);

        return view;

    }
    @Override
    public void onStop() {
        super.onStop();
        phrasesWordsAdapter.releaseMediaPlayer();
    }
}