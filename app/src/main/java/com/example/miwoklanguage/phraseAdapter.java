package com.example.miwoklanguage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class phraseAdapter extends ArrayAdapter<wordClass> {
    public phraseAdapter(@NonNull Context context, ArrayList<wordClass> words){
        super(context, 0, words);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.phraselistitem, parent, false);
        }

        wordClass currentLanguage = getItem(position);
        assert currentLanguage != null;

        TextView text1 = currentItemView.findViewById(R.id.textView2);
        text1.setText(currentLanguage.getDefaultTranslation());

        TextView text2 = currentItemView.findViewById(R.id.textView3);
        text2.setText(currentLanguage.getMiwokTranslation());

        return currentItemView;
    }
}
