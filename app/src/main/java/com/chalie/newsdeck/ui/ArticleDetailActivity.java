package com.chalie.newsdeck.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chalie.newsdeck.R;
import com.chalie.newsdeck.adapters.ArticlePagerAdapter;
import com.chalie.newsdeck.models.Article;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity {

        @BindView(R.id.viewPager)
        ViewPager mViewPager;
        private ArticlePagerAdapter adapterViewPager;
        List<Article> mArticles;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_news_detail);
            ButterKnife.bind(this);

            mArticles = Parcels.unwrap(getIntent().getParcelableExtra("articles"));
            int startingPosition = getIntent().getIntExtra("position", 0);

            adapterViewPager = new ArticlePagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mArticles);
            mViewPager.setAdapter(adapterViewPager);
            mViewPager.setCurrentItem(startingPosition);
        }
    }