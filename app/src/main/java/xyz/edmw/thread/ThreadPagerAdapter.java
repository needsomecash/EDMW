package xyz.edmw.thread;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ThreadPagerAdapter extends FragmentStatePagerAdapter {
    private final String forum;
    private final int numPages;

    public ThreadPagerAdapter(FragmentManager fm, String forum, int numPages) {
        super(fm);
        this.forum = forum;
        this.numPages = numPages;
    }

    @Override
    public Fragment getItem(int position) {
        return ThreadFragment.newInstance(forum, position + 1);
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
