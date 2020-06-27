package com.example.hindimunch.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hindimunch.R;
import com.example.hindimunch.Utils.CommonConstants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;

public class SplashScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    GoogleSignInOptions gso;
    GoogleSignInResult result;
    GoogleSignInAccount account;
    Drawable draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
  /*      new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                signIn(true);
            }
        }, 3000);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLoginData();
    }

    private void checkLoginData() {

        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (pendingResult.isDone()) {
            result = pendingResult.get();
            draw = getResources().getDrawable(R.drawable.custom_progress_bar);
            // set the drawable as progress drawable
            showProgressDialog();
            signInResult(result);
        } else {
            //showProgressDialog();
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    signInResult(googleSignInResult);
                }
            });
        }
    }

    private void signInResult(GoogleSignInResult result) {
        Log.d("GoogleSignInResult", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            account = result.getSignInAccount();
            signIn(true);
        } else {
            signIn(false);
        }
    }

    private void signIn(final boolean isSignIn) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isSignIn) {
                    hideProgressDialog();
                    Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                    intent.putExtra(CommonConstants.googleCredentials, account);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        signIn(false);
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressDrawable(draw);
  /*          mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);*/
        }
        mProgressDialog.show();
    }
}