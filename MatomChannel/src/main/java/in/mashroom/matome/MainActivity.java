/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package in.mashroom.matome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

  ListView lv;
  MainActivity mActivity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = this;
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    lv = (ListView)findViewById(R.id.listView1);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParseObject parseObject = (ParseObject) parent.getItemAtPosition(position);
        Toast.makeText(mActivity, parseObject.getString("link"), Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse(parseObject.getString("link"));
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
      }
    });

    fetchParse();

  }

  public void fetchParse() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Entry");
    FindCallback<ParseObject> callback = new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> results, com.parse.ParseException e) {
        if (results == null) {
          Log.d("entry", "The getFirst request failed.");
        } else {
          Log.d("entry", "Retrieved the object." + results.get(0).getString("title"));

          CustomListItemAdapter adapter = new CustomListItemAdapter(mActivity, results);
          lv.setAdapter(adapter);
        }
      }
    };
    query.findInBackground(callback);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
