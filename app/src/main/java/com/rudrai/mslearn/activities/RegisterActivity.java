package com.rudrai.mslearn.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rudrai.mslearn.R;
import com.rudrai.mslearn.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.transform.Result;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText nameTet, emailTet, passwordTet, confirmPasswordTet;
    private TextInputLayout nameTil, emailTil, passwordTil, confirmPasswordTil;
    private Button registerBtn;
    private static Context context;

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
                if(password.equals(confirmPassword)) {
                    if(name.length() > 2) {
                        if(email.length() > 5) {
                            new Register(name, email, password).execute();
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
    }

    private static class Register extends AsyncTask<Void, Void, String>{
        private ProgressDialog loading;
        private String name, email, password;
        Register(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(context, "Please wait...", "Loading", false, false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler() {
                public boolean canHandleRequest(DownloadManager.Request data) {
                    return false;
                }
                public Result load(DownloadManager.Request request, int networkPolicy) throws IOException {
                    return null;
                }
            };
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("phone", "8888888888");
            param.put("token", "1234567890");

            String result = rh.sendPostRequest("\n" +
                    "GIVEN_API", param);
            Log.i("result", result);
            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("stauts", s);
            String status = null;
            try {
                JSONObject mainObject = new JSONObject(s);
                status = mainObject.getString("status");

                Log.i("output", status);
                if(status.equals("true")){
                    Toast.makeText(context," successfully",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context,"Please try again",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            loading.dismiss();

        }
    }
}
