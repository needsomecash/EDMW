package xyz.edmw.thread;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.R;

public class ThreadViewHolder {
    @Bind(R.id.thread_title)
    TextView title;
    @Bind(R.id.thread_last_post)
    TextView lastPost;

    public ThreadViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setThread(Thread thread) {
        title.setText(Html.fromHtml(thread.getTitle()));
        lastPost.setText(Html.fromHtml(thread.getLastPost()));
    }
}
