package com.example.hindimunch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hindimunch.Fragment.HomeFragment;
import com.example.hindimunch.R;
import com.example.hindimunch.Utils.CommonConstants;
import com.example.hindimunch.Utils.CommonMethods;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.material.navigation.NavigationView;


import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    AppBarConfiguration mAppBarConfiguration;
    DrawerLayout outerDrawer_layout, inndeDrawer_Layout;
    NavigationView navigationView, nav_settings;
    ImageView imgNav, crlCstoolProfile, imgCustomLang;
    CircleImageView crlImgView;
    TextView txtUser, txtEmail;
    String personName, personGivenName, personFamilyName, personEmail, personId;
    Uri personPhoto;
    GoogleSignInAccount account;
    Fragment currentFragment;
    boolean isLangSelected;
    Locale myLocale;
    View headerLayout;
    GoogleApiClient mGoogleApiClient;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        intentParam();
        findViewById();
        init(intent);
    }

    private void intentParam() {
        this.intent = getIntent();

    }

    private void guestLogin() {

    }

    private void signupData(Intent intent) {

    }

    private void loginData(Intent intent) {

    }

    private void googleData(Intent intent) {
        account = intent.getParcelableExtra(CommonConstants.googleCredentials);
        if (account != null) {
            personName = account.getDisplayName();
            personGivenName = account.getGivenName();
            personFamilyName = account.getFamilyName();
            personEmail = account.getEmail();
            personId = account.getId();
            personPhoto = account.getPhotoUrl();
            Log.e("Google Sign In Data", " " + account.getAccount());
        }
    }

    private void findViewById() {
        outerDrawer_layout = findViewById(R.id.outerDrawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imgNav = findViewById(R.id.imgNav);
        imgCustomLang = findViewById(R.id.imgCustomLang);
        crlCstoolProfile = findViewById(R.id.crlCstoolProfile);


    }

    private void init(Intent intent) {
        if (CommonMethods.getPref(getApplicationContext(), CommonConstants.SignInMethod, "none").equals(CommonConstants.googleSignIn)) {
            googleData(intent);
        } else if (CommonMethods.getPref(getApplicationContext(), CommonConstants.SignInMethod, "none").equals(CommonConstants.normalSignIn)) {
            loginData(intent);
        } else if (CommonMethods.getPref(getApplicationContext(), CommonConstants.SignInMethod, "none").equals(CommonConstants.normalSignUp)) {
            signupData(intent);
        } else {
            guestLogin();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        navigationView.setNavigationItemSelectedListener(this);
        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outerDrawer_layout.openDrawer(GravityCompat.START);
            }
        });

        imgCustomLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocale(getApplicationContext());
            }
        });
        headerLayout = navigationView.getHeaderView(0);

        crlImgView = headerLayout.findViewById(R.id.crlImgView);
        txtEmail = headerLayout.findViewById(R.id.txtEmail);
        txtUser = headerLayout.findViewById(R.id.txtUser);

        if (account != null) {
            Drawable mDefaultBackground = getResources().getDrawable(R.drawable.ic_person_24);
            Glide.with(getApplicationContext())
                    .load(personPhoto).error(R.drawable.ic_person_24)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(crlImgView);

            Glide.with(getApplicationContext()).load(personPhoto)
                    .thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(mDefaultBackground)
                    .into(crlCstoolProfile);
            txtUser.setText(personName);
            txtEmail.setText(personEmail);
        }

    }

    @Override
    public void recreate() {
        super.recreate();

    }

    @Override
    public void onBackPressed() {
        if (outerDrawer_layout.isDrawerOpen(GravityCompat.START)) {
            outerDrawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_close_settings:
                navigationView.addHeaderView(headerLayout);
                navigationView.getMenu().clear(); //clear old inflated items.
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                navigationView.setNavigationItemSelectedListener(this);
                navigationView.animate();
                break;
            case R.id.navHome:
                HomeFragment homeFragment = new HomeFragment();
                changeFragment(homeFragment);
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_setting:
                navigationView.removeHeaderView(headerLayout);
                navigationView.getMenu().clear(); //clear old inflated items.
                navigationView.inflateMenu(R.menu.setting_navigation);
                navigationView.setNavigationItemSelectedListener(this);
                break;

            case R.id.menu_sign_out:
                logout();
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void logout() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


    }

    public void changeFragment(Fragment targetFragment) {
        currentFragment = targetFragment;
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.nav_host_fragment, currentFragment, "Fragment")
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myLocale = getResources().getConfiguration().locale;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Locale locale = getLocale(this);
        if (!locale.equals(myLocale)) {
            myLocale = locale;
            recreate();
        }
    }

    public static Locale getLocale(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = sharedPreferences.getString("language", "en");
        switch (lang) {
            case "English":
                lang = "en";
                break;
            case "hindi":
                lang = "hi";
                break;
        }
        return new Locale(lang);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("get Google Status", " " + connectionResult);
    }
}