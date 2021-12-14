package com.chalie.newsdeck.adapters;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chalie.newsdeck.models.Article;
import com.chalie.newsdeck.ui.NewsItemFragment;

import java.util.List;

@SuppressWarnings("ALL")
public class ArticlePagerAdapter  extends FragmentPagerAdapter {

    private List<Article> mArticles;

    public ArticlePagerAdapter(@NonNull FragmentManager fm, int behavior, List<Article> articles) {
        super(fm, behavior);
        mArticles = articles;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsItemFragment.newInstance(mArticles.get(position));
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArticles.get(position).getTitle();
    }
}
