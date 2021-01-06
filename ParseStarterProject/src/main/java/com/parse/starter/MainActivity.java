/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {


    Boolean signUpModeActive = true;
    TextView login;
    EditText username;
    EditText password;
    ImageView logo;
    ConstraintLayout backgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        logo = findViewById(R.id.logo);
        backgroundLayout = findViewById(R.id.backgroundLayout);

        logo.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        password.setOnKeyListener(this);



    /*
    ParseUser user = new ParseUser();

    user.setUsername("krista");
    user.setPassword("myPass");

    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if(e == null){
                // OK
                Log.i("Sign Up OK!", "We did it");
            } else {
                e.printStackTrace();
            }
        }
    });
    */

//      ParseUser.logInInBackground("krista", "myPass", new LogInCallback() {
//          @Override
//          public void done(ParseUser user, ParseException e) {
//              if (user != null){
//                  Log.i("Success", "We Logged In");
//              } else {
//                  e.printStackTrace();
//              }
//          }
//      });

//        if (ParseUser.getCurrentUser().getUsername() != null) {
//            Log.i("Signed In", ParseUser.getCurrentUser().getUsername());
//        } else {
//            Log.i("no luck", "not signed in");
//        }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void signUpClicked(View view) {

        if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
        } else {

            if (signUpModeActive) {
                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // OK
                            Log.i("Sign Up Success!", username.getText().toString() + " has been signed up.");
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                // Login
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("Login Success", username.getText().toString() + " logged in");
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            Button signUpButton = findViewById(R.id.signUpButton);
            if (signUpModeActive) {
                signUpModeActive = false;
                signUpButton.setText("Login");
                login.setText("or, Sign Up");
            } else {
                signUpModeActive = true;
                signUpButton.setText("Sign Up");
                login.setText("or, Login");
            }
        } else if (view.getId() == R.id.logo || view.getId() == R.id.backgroundLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            signUpClicked(v);
        }

        return false;
    }
}