package com.example.miwoklanguage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NumbersFragment extends Fragment {
    private WordAdapter numbersWordsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_list, container, false);

        final List<Word> numbers = new ArrayList<>();
        numbers.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        numbersWordsAdapter = new WordAdapter(getActivity(), numbers, R.color.category_numbers);
        ListView listView = (ListView) view.findViewById(R.id.list_words);
        listView.setAdapter(numbersWordsAdapter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        numbersWordsAdapter.releaseMediaPlayer();
    }
}