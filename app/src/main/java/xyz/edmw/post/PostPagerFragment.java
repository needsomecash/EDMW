package xyz.edmw.post;

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

public class PostPagerFragment extends Fragment {
    private static final String ARG_PATH = "arg_path";
    private static final String ARG_PAGES = "arg_num_pages";

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    public static PostPagerFragment newInstance(String path, int numPages) {
        PostPagerFragment fragment = new PostPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        args.putInt(ARG_PAGES, numPages);
        fragment.setArguments(args);
        return fragment;
    }

    public PostPagerFragment() {

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
        String path = args.getString(ARG_PATH);
        int numPages = args.getInt(ARG_PAGES);

        viewPager.setAdapter(new PostPagerAdapter(getChildFragmentManager(), path, numPages));
    }
}
