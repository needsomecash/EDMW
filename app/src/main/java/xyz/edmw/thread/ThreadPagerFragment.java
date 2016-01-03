package xyz.edmw.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.R;

public class ThreadPagerFragment extends Fragment {
    private static final String ARG_FORUM = "arg_forum";
    private static final String ARG_PAGES = "arg_num_pages";

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    public static ThreadPagerFragment newInstance(String forum, int numPages) {
        ThreadPagerFragment fragment = new ThreadPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FORUM, forum);
        args.putInt(ARG_PAGES, numPages);
        fragment.setArguments(args);
        return fragment;
    }

    public ThreadPagerFragment() {

    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        String forum = args.getString(ARG_FORUM);
        int numPages = args.getInt(ARG_PAGES);

        viewPager.setAdapter(new ThreadPagerAdapter(getChildFragmentManager(), forum, numPages));
    }
}
