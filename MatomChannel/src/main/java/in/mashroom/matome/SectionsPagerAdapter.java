package in.mashroom.matome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Locale;

/**
 * Created by himara2 on 15/09/21.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext = null;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LatestEntryFragment();
            case 1:
                return new DayHotEntryFlagment();
            case 2:
                return new WeekHotEntryFlagment();
            case 3:
                return new AllHotEntryFlagment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1);
            case 1:
                return mContext.getString(R.string.title_section2);
            case 2:
                return mContext.getString(R.string.title_section3);
            case 3:
                return mContext.getString(R.string.title_section4);
        }
        return null;
    }
}
