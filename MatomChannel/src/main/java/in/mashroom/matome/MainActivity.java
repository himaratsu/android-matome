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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActionBarActivity {

  MainActivity mActivity;
  SectionsPagerAdapter mSectionsPagerAdapter;
  ViewPager mViewPager;
  TabHost tabHost;
  View indicator;
  TabWidget tabWidget;

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
    tabWidget = (TabWidget) findViewById(android.R.id.tabs);
    tabWidget.setStripEnabled(false);
    tabWidget.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

    LayoutInflater inflater = LayoutInflater.from(this);
    for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
      TextView tv = (TextView) inflater.inflate(R.layout.tab_widget, tabWidget, false);
      tv.setText(mSectionsPagerAdapter.getPageTitle(i));

      tabHost.addTab(tabHost
                .newTabSpec(String.valueOf(i))
                .setIndicator(tv)
                .setContent(android.R.id.tabcontent));
    }

    indicator = findViewById(R.id.indicator);
    mViewPager.setOnPageChangeListener(new PageChangeListener());

    // ActionBar下の影を消す
    getSupportActionBar().setElevation(0);
    getSupportActionBar().setDisplayShowTitleEnabled(true);

    // タブ下に影を出す
    float elevation = 4 * getResources().getDisplayMetrics().density;
    tabHost.setElevation(elevation);

    tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
      @Override
      public void onTabChanged(String tabId) {
        mViewPager.setCurrentItem(Integer.valueOf(tabId));
      }
    });


    // Ad
    AdView mAdView = (AdView) findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);
  }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        private int scrollingState = ViewPager.SCROLL_STATE_IDLE;

        @Override
        public void onPageSelected(int position) {
            // スクロール中はonPageScrolled()で描画するのでここではしない
            if (scrollingState == ViewPager.SCROLL_STATE_IDLE) {
                updateIndicatorPosition(position, 0);
            }
            tabWidget.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollingState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            updateIndicatorPosition(position, positionOffset);
        }

        private void updateIndicatorPosition(int position, float positionOffset) {
            View tabView = tabWidget.getChildTabViewAt(position);
            int indicatorWidth = tabView.getWidth();
            int indicatorLeft = (int) ((position + positionOffset) * indicatorWidth);

            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) indicator.getLayoutParams();
            layoutParams.width = indicatorWidth;
            layoutParams.setMargins(indicatorLeft, 0, 0, 0);
            indicator.setLayoutParams(layoutParams);
        }
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
