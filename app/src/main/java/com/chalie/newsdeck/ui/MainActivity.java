package com.chalie.newsdeck.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chalie.newsdeck.services.NewsApiClient;
import com.chalie.newsdeck.R;
import com.chalie.newsdeck.models.Article;
import com.chalie.newsdeck.models.Everything;
import com.chalie.newsdeck.services.NewsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.newsUpdates);
        NewsApi client = NewsApiClient.getClient();
        Call<Everything> call = client.getArticles("tech");

        Log.d(TAG, "onCreate: "+client);

        call.enqueue(new Callback<Everything>() {
            @Override
            public void onResponse(Call<Everything> call, Response<Everything> response) {
                if(response.isSuccessful()){
                    List<Article> articlesList = response.body().getArticles();
                    String[] titles = new String[articlesList.size()];
                    String[] content = new String[articlesList.size()];

                    Toast.makeText(MainActivity.this,articlesList.get(1).getTitle(),Toast.LENGTH_LONG ).show();

                    for(int i = 0; i < content.length; i++){
                        content[i] = articlesList.get(i).getContent();
                    }

                    for (int i = 0; i < titles.length; i++){
                        titles[i] = articlesList.get(i).getTitle();
                    }

                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, titles);
                    mListView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Everything> call, Throwable t) {
                Toast.makeText(MainActivity.this, "oops :(",Toast.LENGTH_LONG).show();

            }
        });
    }
}
