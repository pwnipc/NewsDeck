package com.chalie.newsdeck.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chalie.newsdeck.Constants;
import com.chalie.newsdeck.R;
import com.chalie.newsdeck.models.Article;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsItemFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;

    private String currentPhotoPath;

    @BindView(R.id.imageViewNews)
    ImageView imageViewNews;
    @BindView(R.id.textViewNewsTitle)
    TextView textViewNewsTitle;
    @BindView(R.id.textViewUrl) TextView textViewUrl;
    @BindView(R.id.websiteTextView) TextView websiteTextView;
    @BindView(R.id.viewNews) Button viewNews;
    @BindView(R.id.saveNews) Button saveNews;


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
        setHasOptionsMenu(true);
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if ("mSource".equals(Constants.SOURCE_SAVED)) { //add stuff later
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }
    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewNews.setImageBitmap(imageBitmap);
            //encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

//    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        DatabaseReference ref = FirebaseDatabase.getInstance()
//                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child(mRestaurant.getPushId())
//                .child("imageUrl");
//        ref.setValue(imageEncoded);
//    }

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
        saveNews.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_news_item, container, false);
    }


    @Override
    public void onClick(View v) {

        if(v == viewNews){
            Toast.makeText(getContext(),"Please wait...",Toast.LENGTH_SHORT).show();
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mArticle.getUrl()));
            startActivity(webIntent);
        }else if(v == saveNews){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference("articles")
                    .child(uid);


            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mArticle.setPushId(pushId);
            pushRef.setValue(mArticle);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}