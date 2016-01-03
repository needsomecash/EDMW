package xyz.edmw.post;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.R;

public class PostViewHolder {
    @Bind(R.id.post_author)
    TextView author;
    @Bind(R.id.post_message)
    TextView message;

    public PostViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setPost(Post post) {
        author.setText(Html.fromHtml(post.getAuthor() + " " + post.getTimestamp()));
        message.setText(Html.fromHtml(post.getMessage()));
    }
}
