package com.chalie.newsdeck.ui;

import static android.content.ContentValues.TAG;

import static com.chalie.newsdeck.Constants.BASE_URL;
import static com.chalie.newsdeck.Constants.NEWSAPI_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chalie.newsdeck.Constants;
import com.chalie.newsdeck.R;
import com.chalie.newsdeck.adapters.ArticleListAdapter;
import com.chalie.newsdeck.models.Article;
import com.chalie.newsdeck.models.Everything;
import com.chalie.newsdeck.services.NewsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.newsUpdates) RecyclerView mRecyclerView;
    private ArticleListAdapter mArticleListAdapter;
   // private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //mListView = findViewById(R.id.newsUpdates);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        NewsApi newsApi = retrofit.create(NewsApi.class);

        Call<Everything> call = newsApi.getArticles("tech",NEWSAPI_KEY);

       call.enqueue(new Callback<Everything>() {
           @Override
           public void onResponse(Call<Everything> call, Response<Everything> response) {
               if(!response.isSuccessful()){
                   Toast.makeText(MainActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                   return;
               }
               Everything articlesList = response.body();
               List<Article> articles = articlesList.getArticles();
               String[] authors = new String[articles.size()];
              // mListView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,authors));
               mArticleListAdapter = new ArticleListAdapter(MainActivity.this, articles);
               mRecyclerView.setAdapter(mArticleListAdapter);
               RecyclerView.LayoutManager layoutManager =
                       new LinearLayoutManager(MainActivity.this);
               mRecyclerView.setLayoutManager(layoutManager);
               mRecyclerView.setHasFixedSize(true);

           }

           @Override
           public void onFailure(Call<Everything> call, Throwable t) {
               Log.d(TAG, "onFailure: "+t.getMessage());
               Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_LONG).show();

           }
       });

    }
}
