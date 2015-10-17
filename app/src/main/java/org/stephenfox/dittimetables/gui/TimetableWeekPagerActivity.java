package org.stephenfox.dittimetables.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import org.stephenfox.dittimetables.R;
import org.stephenfox.dittimetables.network.AsyncDownloader;
import org.stephenfox.dittimetables.network.JsonParser;
import org.stephenfox.dittimetables.network.WeekDownloader;

/**
 * This class manages a set of fragments see
 * {@link org.stephenfox.dittimetables.gui.TimetableWeekPageFragment}
 * for details on each fragment.
 */
public class TimetableWeekPagerActivity extends FragmentActivity {

  private ViewPager pager;
  private PagerAdapter pageAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_slide);

    Intent d = getIntent();
    String url = d.getStringExtra("url");

    WeekDownloader weekDownloader = new WeekDownloader();
    weekDownloader.downloadWeekForCourse(url, new AsyncDownloader.HttpAsyncCallback() {
      @Override
      public void finished(String data) {
        JsonParser jsonParser = new JsonParser();
        jsonParser.parseSessionsForWeek(data);
      }
    });


    pager = (ViewPager)findViewById(R.id.slide);
    pageAdapter = new SliderAdapter(getSupportFragmentManager());
    pager.setAdapter(pageAdapter);

  }


  private class SliderAdapter extends FragmentStatePagerAdapter {

    public SliderAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return new TimetableWeekPageFragment();
    }

    @Override
    public int getCount() {
      return 5;
    }
  }
}