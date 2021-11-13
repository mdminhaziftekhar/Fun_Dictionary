package com.example.miwoklanguage;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WordFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 4;
    private Context context;

    public WordFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1){
            return new FamilyFragment();
        } else if (position == 2){
            return new ColorsFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.category_numbers);
        } else if (position == 1){
            return context.getString(R.string.category_family_short);
        } else if (position == 2){
            return context.getString(R.string.category_colors);
        } else {
            return context.getString(R.string.category_phrases);
        }
    }
}
