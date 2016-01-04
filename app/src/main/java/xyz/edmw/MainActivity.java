package xyz.edmw;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import xyz.edmw.post.PostPagerFragment;
import xyz.edmw.thread.Thread;
import xyz.edmw.thread.ThreadFragment;
import xyz.edmw.thread.ThreadPagerFragment;

public class MainActivity extends AppCompatActivity implements ThreadFragment.OnThreadSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        String forum = "main-forum";
        int numPages = 15; // TODO
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, ThreadPagerFragment.newInstance(forum, numPages))
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            getSupportActionBar().setTitle("EDMW");
        }
    }

    @Override
    public void onThreadSelected(Thread thread) {
        getSupportActionBar().setTitle(thread.getTitle()); // TODO fix action bar truncating long thread titles

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, PostPagerFragment.newInstance(thread.getPath(), thread.getNumPages()))
                .addToBackStack(null)
                .commit();
    }
}
