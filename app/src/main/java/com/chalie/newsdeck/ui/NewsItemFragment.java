package com.chalie.newsdeck.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chalie.newsdeck.R;
import com.chalie.newsdeck.models.Article;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsItemFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.imageViewNews)
    ImageView imageViewNews;
    @BindView(R.id.textViewNewsTitle)
    TextView textViewNewsTitle;
    @BindView(R.id.textViewUrl) TextView textViewUrl;
    @BindView(R.id.websiteTextView) TextView websiteTextView;
    @BindView(R.id.viewNews) Button viewNews;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Article mArticle;
    private String mParam2;

    public NewsItemFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewsItemFragment newInstance(Article article) {
        NewsItemFragment newsItemFragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putParcelable("article", Parcels.wrap(article));
        newsItemFragment.setArguments(args);
        return newsItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = Parcels.unwrap(getArguments().getParcelable("article"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_item, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mArticle.getUrlToImage()).into(imageViewNews);

        textViewNewsTitle.setText(mArticle.getTitle());
        textViewUrl.setText(mArticle.getUrl());
        websiteTextView.setText(mArticle.getContent());
        viewNews.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_news_item, container, false);
    }

    @Override
    public void onClick(View v) {

        if(v == viewNews){
            Toast.makeText(getContext(),"Please wait...",Toast.LENGTH_SHORT).show();
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mArticle.getUrl()));
            startActivity(webIntent);
        }
    }
}