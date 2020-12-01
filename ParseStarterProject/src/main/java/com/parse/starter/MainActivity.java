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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseObject tweet = new ParseObject("tweet");
    tweet.put("username", "napoleon");
    tweet.put("tweet", "i like carrots");
    tweet.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null) {
          Log.i("Success", "We saved a tweet");
        } else {
          e.printStackTrace();
        }
      }
    });

    ParseQuery<ParseObject> query = new ParseQuery<>("tweet");
    query.getInBackground("Uo5enYYG5U", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e == null && object != null) {
          object.put("tweet", "i like basil");
          object.saveInBackground();

          Log.i("username", object.getString("username"));
          Log.i("tweet", object.getString("tweet"));
        }
      }
    });

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}