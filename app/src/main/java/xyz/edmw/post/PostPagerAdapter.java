package xyz.edmw.post;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PostPagerAdapter extends FragmentStatePagerAdapter {
    private final String path;
    private final int numPages;

    public PostPagerAdapter(FragmentManager fm, String path, int numPages) {
        super(fm);
        this.path = path;
        this.numPages = numPages;
    }

    @Override
    public Fragment getItem(int position) {
        return PostFragment.newInstance(path, position + 1);
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
