package com.chalie.newsdeck.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chalie.newsdeck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.textViewLogin) TextView mTextViewLogin;
    @BindView(R.id.editTextName) EditText mEditTextName;
    @BindView(R.id.editTextEmail) EditText mEditTextEmail;
    @BindView(R.id.editTextPassword) EditText mEditTextPassword;
    @BindView(R.id.editTextConfirmPassword) EditText mEditTextConfirmPassword;
    @BindView(R.id.buttonCreateUser) Button mButtonCreateUser;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        mTextViewLogin.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mButtonCreateUser.setOnClickListener(this);
        createAuthStateListener();
    }

    private void createNewUser(){
        final String name = mEditTextName.getText().toString().trim();
        final String email = mEditTextEmail.getText().toString().trim();
        final String password = mEditTextPassword.getText().toString().trim();
        final String confirmPassword = mEditTextConfirmPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "createNewUser: Auth Successful");
                    }else {
                        Toast.makeText(CreateAccountActivity.this,"Auth failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == mTextViewLogin){
            Intent intent = new Intent(CreateAccountActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else if(v == mButtonCreateUser){
            createNewUser();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}