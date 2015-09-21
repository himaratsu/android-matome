/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package in.mashroom.matome;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
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

  MainActivity mActivity;
  SectionsPagerAdapter mSectionsPagerAdapter;
  ViewPager mViewPager;
  TabHost tabHost;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = this;
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    tabHost = (TabHost) findViewById(android.R.id.tabhost);
    tabHost.setup();

    // タブ間の区切り線を消す
    TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
    tabWidget.setStripEnabled(false);
    tabWidget.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

    // ActionBar下の影を消す
//    getActionBar().setElevation(0);

    // タブ下に影を出す
//    float elevation = 4 * getResources().getDisplayMetrics().density;
//    tabHost.setElevation(elevation);

    for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
      tabHost.addTab(tabHost
              .newTabSpec(String.valueOf(i))
              .setIndicator(mSectionsPagerAdapter.getPageTitle(i))
              .setContent(android.R.id.tabcontent));
    }

    tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
      @Override
      public void onTabChanged(String tabId) {
        mViewPager.setCurrentItem(Integer.valueOf(tabId));
      }
    });

    mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        tabHost.setCurrentTab(position);
      }
    });

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
