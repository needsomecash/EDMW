package xyz.edmw;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import xyz.edmw.post.PostPagerFragment;
import xyz.edmw.thread.Thread;
import xyz.edmw.thread.ThreadFragment;
import xyz.edmw.thread.ThreadPagerFragment;

public class MainActivity extends AppCompatActivity implements ThreadFragment.OnThreadSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String forum = "main-forum";
        int numPages = 15; // TODO
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, ThreadPagerFragment.newInstance(forum, numPages))
                .commit();
    }

    @Override
    public void onThreadSelected(Thread thread) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, PostPagerFragment.newInstance(thread.getPath(), thread.getNumPages()))
                .addToBackStack(null)
                .commit();
    }
}
