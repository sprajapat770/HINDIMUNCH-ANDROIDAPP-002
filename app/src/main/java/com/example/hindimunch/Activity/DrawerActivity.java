package com.example.hindimunch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;


import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        intentParam();
        findViewById();
        init();
    }

    private void intentParam() {
        googleData();

    }

    private void googleData() {
        account = getIntent().getParcelableExtra(CommonConstants.googleCredentials);
        if (account != null) {
            personName = account.getDisplayName();
            personGivenName = account.getGivenName();
            personFamilyName = account.getFamilyName();
            personEmail = account.getEmail();
            personId = account.getId();
            personPhoto = account.getPhotoUrl();
        }
    }

    private void findViewById() {
        outerDrawer_layout = findViewById(R.id.outerDrawer_layout);
        inndeDrawer_Layout = findViewById(R.id.inndeDrawer_Layout);
        navigationView = findViewById(R.id.nav_view);
        nav_settings = findViewById(R.id.nav_settings);
        imgNav = findViewById(R.id.imgNav);
        imgCustomLang = findViewById(R.id.imgCustomLang);
        crlCstoolProfile = findViewById(R.id.crlCstoolProfile);

    }

    private void init() {
        navigationView.setNavigationItemSelectedListener(this);
        nav_settings.setNavigationItemSelectedListener(this);
        inndeDrawer_Layout.setScrimColor(Color.TRANSPARENT);

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
        View headerLayout = nav_settings.getHeaderView(0);

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
            case R.id.nav_settings:
                outerDrawer_layout.closeDrawer(GravityCompat.START);
                inndeDrawer_Layout.openDrawer(GravityCompat.END);
                break;

            case R.id.menu_close_settings:
                inndeDrawer_Layout.closeDrawer(GravityCompat.START);
                break;
            case R.id.navHome:
                HomeFragment homeFragment = new HomeFragment();
                changeFragment(homeFragment);
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
                inndeDrawer_Layout.openDrawer(GravityCompat.START);
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
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
}