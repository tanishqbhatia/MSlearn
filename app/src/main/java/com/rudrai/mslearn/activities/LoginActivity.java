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

public class LoginActivity extends AppCompatActivity {

    private static Context context;
    private TextInputEditText emailTet, passwordTet;
    private TextInputLayout emailTil, passwordTil;
    private Button registerBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        findViews();
        addListeners();
    }

    private void addListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTet.getText().toString();
                String password = passwordTet.getText().toString();
                if (email.length() > 5) {
                    login(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                    emailTil.setError("Invalid email address.");
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login(String email, String password) {
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Just a sec", "Logging in. ", false, false);
        App.getServer().login(email, password).enqueue(new Callback<com.rudrai.mslearn.models.Login>() {
            @Override
            public void onResponse(Call<com.rudrai.mslearn.models.Login> call, Response<com.rudrai.mslearn.models.Login> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getError() == Boolean.FALSE) {
                        Prefs.putBoolean(Cons.REGISTERED, true);
                        Prefs.putString(Cons.UID, response.body().getUid());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error while logging in, please check the credentials.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.rudrai.mslearn.models.Login> call, Throwable t) {
                loading.dismiss();
                Log.e(LoginActivity.class.getSimpleName(), t.getMessage());
            }
        });

    }

    private void findViews() {
        emailTil = findViewById(R.id.emailTil);
        emailTet = findViewById(R.id.emailTet);
        passwordTil = findViewById(R.id.passwordTil);
        passwordTet = findViewById(R.id.passwordTet);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn);
    }
}
