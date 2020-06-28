package com.example.hindimunch.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hindimunch.R;
import com.example.hindimunch.Utils.CommonConstants;
import com.example.hindimunch.Utils.CommonMethods;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerificationActivity extends AppCompatActivity {
    CircleImageView crlImageView;
    EditText edtUserName, edtPassword, edtConfPass, edtOtp;

    Boolean UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        findViewById();
        init();
    }

    private void findViewById() {
        crlImageView = findViewById(R.id.crlImageView);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfPass = findViewById(R.id.edtConfPass);
        edtOtp = findViewById(R.id.edtOtp);
    }

    private void init() {

//        verify();
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (edtUserName.length() > 0) {
                    edtUserName.setError("UserName Should Not be Blank");
                } else {
                    edtUserName.setError("UserName Not Available");
                    UserName = true;
                }
            }

        });
        validationCheck();
    }

    private void validationCheck() {
        if (edtUserName.getText().toString().isEmpty()) {
            edtUserName.setError("UserName Should Not be Blank");
        } else if (!UserName) {
            edtUserName.setError("UserName not Available");
        } else if (edtPassword.getText().toString().trim().isEmpty()) {
            edtUserName.setError("Password Should Not be Blank");
        } else if (edtConfPass.getText().toString().trim().isEmpty()) {
            if (!edtPassword.getText().toString().trim().equals(edtConfPass.getText().toString().trim())) {
                edtConfPass.setError("Password not match");
            } else {
                verify();
            }
        } else if (!edtOtp.getText().toString().trim().equals("1234")) {
            edtUserName.setError("UserName Should Not be Blank");
        } else {

            verify();
        }
    }

    private void verify() {
        CommonMethods.setPref(getApplicationContext(), CommonConstants.SignInMethod, CommonConstants.normalSignUp);
        Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
        startActivity(intent);
        finish();
    }
}