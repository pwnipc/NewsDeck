package com.chalie.newsdeck.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chalie.newsdeck.R;
import com.chalie.newsdeck.models.Article;
import com.chalie.newsdeck.ui.ArticleDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {
    private List<Article> articles ;
    private Context mContext;

    public ArticleListAdapter(Context mContext, List<Article> articles){
        this.mContext = mContext;
        this.articles = articles;
    }


    @NonNull
    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListAdapter.ArticleViewHolder holder, int position) {
        holder.bindNews(articles.get(position));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageViewNews) ImageView mNewsImageView;
        @BindView(R.id.textViewNewsTitle) TextView mTitleTextView;
        @BindView(R.id.textViewNewsDescription) TextView mDescriptionTextView;
//        @BindView(R.id.textViewUrl) TextView mUrlTextView;

        private Context mContext;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindNews(Article article) {
            mTitleTextView.setText(article.getTitle());
            mDescriptionTextView.setText(article.getDescription());
            Picasso.get().load(article.getUrlToImage()).into(mNewsImageView);
            //  mUrlTextView.setText(article.getUrl());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ArticleDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("articles", Parcels.wrap(articles));
            mContext.startActivity(intent);


        }
    }


}


