package com.example.miwoklanguage;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<wordClass> {
    public WordAdapter(@NonNull Context context, ArrayList<wordClass> words){
        super(context, 0, words);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null){
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.newlistitem, parent, false);
        }

        wordClass currentLanguage= getItem(position);
        assert currentLanguage != null;

        TextView text1 = currentItemView.findViewById(R.id.textView2);
        text1.setText(currentLanguage.getDefaultTranslation());

        TextView text2 = currentItemView.findViewById(R.id.textView3);
        text2.setText(currentLanguage.getMiwokTranslation());

        ImageView img = currentItemView.findViewById(R.id.imageView3);
        img.setImageResource(currentLanguage.getmImageResourceId());

        ImageView img2 = currentItemView.findViewById(R.id.play);
        img2.setImageResource(R.drawable.play);

        return currentItemView;
    }



}
