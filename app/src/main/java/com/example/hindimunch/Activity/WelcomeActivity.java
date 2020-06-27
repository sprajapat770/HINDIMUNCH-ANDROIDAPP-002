package com.example.hindimunch.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hindimunch.R;
import com.example.hindimunch.Utils.CommonConstants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class WelcomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button btnLogin, btnSignUp, btnGuest;
    SignInButton btnGoogleSign;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private int googleSignRequestCode;
    GoogleSignInOptions gso;
    GoogleSignInResult result;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        intentParam();
        findViewById();
        init();
    }

    private void intentParam() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void findViewById() {
        btnGoogleSign = findViewById(R.id.btnGoogleSign);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnGuest = findViewById(R.id.btnGuest);
    }

    private void init() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnGoogleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
            }
        });
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void googleLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, googleSignRequestCode);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Connection result ", "onConnectionFailed:" + connectionResult);
        Toast.makeText(getApplicationContext(), "Failed " + connectionResult.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == googleSignRequestCode) {
            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInResult(result);
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

    private void signIn(boolean isSignIn) {
        showProgressDialog();
        if (isSignIn) {
            hideProgressDialog();
            Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
            intent.putExtra(CommonConstants.googleCredentials, account);
            startActivity(intent);
        }


    }

    /*

        @Override
        protected void onStart() {
            super.onStart();

            OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (pendingResult.isDone()) {
                GoogleSignInResult result = pendingResult.get();
                signInResult(result);
            }
            else {
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

    */
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
}