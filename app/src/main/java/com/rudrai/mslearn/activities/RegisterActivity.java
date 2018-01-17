package com.rudrai.mslearn.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.rudrai.mslearn.App;
import com.rudrai.mslearn.R;
import com.rudrai.mslearn.utils.Cons;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static Context context;
    private TextInputEditText nameTet, emailTet, passwordTet, confirmPasswordTet;
    private TextInputLayout nameTil, emailTil, passwordTil, confirmPasswordTil;
    private Button registerBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;
        findViews();
        addListeners();
    }

    private void addListeners() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTet.getText().toString();
                String email = emailTet.getText().toString();
                String password = passwordTet.getText().toString();
                String confirmPassword = confirmPasswordTet.getText().toString();
                if (password.equals(confirmPassword)) {
                    if (name.length() > 2) {
                        if (email.length() > 5) {
                            register(name, email, password);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                            emailTil.setError("Invalid email address.");
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Name must have atleast 2 characters.", Toast.LENGTH_SHORT).show();
                        nameTil.setError("Invalid name.");
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords mismatch, please try again.", Toast.LENGTH_SHORT).show();
                    passwordTil.setError("Passwords mismatch");
                    confirmPasswordTil.setError("Passwords mismatch");
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void register(String name, String email, String password) {
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Just a sec", "Registration in progress. ", false, false);
        App.getServer().register(name, email, password).enqueue(new Callback<com.rudrai.mslearn.models.Register>() {
            @Override
            public void onResponse(Call<com.rudrai.mslearn.models.Register> call, Response<com.rudrai.mslearn.models.Register> response) {
                loading.dismiss();
                if(response.isSuccessful()) {
                    if(response.body().getError() == Boolean.FALSE) {
                        Prefs.putBoolean(Cons.REGISTERED, true);
                        Prefs.putString(Cons.UID, response.body().getUid());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error while registering user, please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.rudrai.mslearn.models.Register> call, Throwable t) {
                loading.dismiss();
                Log.e(RegisterActivity.class.getSimpleName(), t.getMessage());
            }
        });

    }

    private void findViews() {
        nameTil = findViewById(R.id.nameTil);
        nameTet = findViewById(R.id.nameTet);
        emailTil = findViewById(R.id.emailTil);
        emailTet = findViewById(R.id.emailTet);
        passwordTil = findViewById(R.id.passwordTil);
        passwordTet = findViewById(R.id.passwordTet);
        confirmPasswordTil = findViewById(R.id.confirmPasswordTil);
        confirmPasswordTet = findViewById(R.id.confirmPasswordTet);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn);
    }
}
