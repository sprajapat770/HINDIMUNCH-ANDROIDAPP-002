package com.example.hindimunch.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hindimunch.R;

public class SignUpActivity extends AppCompatActivity {
    EditText edtFsName, edtPhone, edtEmail, edtLastName;
    Button btnSignUp;
    TextView txtLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById();
        init();
    }

    private void findViewById() {
        edtFsName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);
    }

    private void init() {
        validationCheck();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validationCheck() {
        if (edtFsName.getText().toString().isEmpty()) {
            edtFsName.setError("First Name Should not be blank");
        } else if (edtEmail.getText().toString().trim().isEmpty()) {
            if (edtPhone.getText().toString().trim().isEmpty()) {
                edtFsName.setError("Email or Phone Should not be blank");
            } else if (edtPhone.getText().toString().trim().matches(phonePattern)) {
                signUp();
            }
        } else if (!edtEmail.getText().toString().trim().matches(emailPattern) || edtPhone.getText().toString().trim().matches(phonePattern)) {
            edtFsName.setError("Either Email or Phone number is wrong");
        } else {
            signUp();
        }
    }

    private void signUp() {
        Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
        startActivity(intent);
    }
}
/*

emailValidate .addTextChangedListener(new TextWatcher() {
public void afterTextChanged(Editable s) {

        if (email.matches(emailPattern) && s.length() > 0)
        {
        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
        // or
        textView.setText("valid email");
        }
        else
        {
        Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
        //or
        textView.setText("invalid email");
        }
        }
public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // other stuffs
        }
public void onTextChanged(CharSequence s, int start, int before, int count) {
        // other stuffs
        }
        }); */
