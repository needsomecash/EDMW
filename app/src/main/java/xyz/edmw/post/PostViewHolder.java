package xyz.edmw.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.R;
import xyz.edmw.image.DownloadImageTask;
import xyz.edmw.image.ImageDialogFragment;

public class PostViewHolder {
    @Bind(R.id.post_author)
    TextView author;
    @Bind(R.id.post_message)
    LinearLayout message;

    private static final String tag = "PostViewHolder";
    private final Context context;

    public PostViewHolder(Context context, View view) {
        this.context = context;
        ButterKnife.bind(this, view);
    }

    public void setPost(Post post) {
        message.removeAllViews();
        author.setText(Html.fromHtml(post.getAuthor() + " " + post.getTimestamp()));
        Element body = Jsoup.parseBodyFragment(post.getMessage()).body();
        for (Node node : body.childNodes()) {
            if (node instanceof TextNode) {
                setTextNode((TextNode) node);
            } else if (node instanceof Element) {
                setElement((Element) node);
            } else {
                Log.w(tag, "Unknown node.");
            }
        }
    }

    private void setTextNode(TextNode node) {
        TextView view = new TextView(context);
        String text = node.text().trim();
        if (!text.isEmpty()) {
            view.setText(Html.fromHtml(text));
            view.setAutoLinkMask(Linkify.ALL);
            message.addView(view);
        }
    }

    private void setElement(Element element) {
        switch (element.tagName()) {
            case "br":
                // do nothing
                // TODO this removes intentional newlines.
                break;
            case "img":
                final ImageView imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                new DownloadImageTask(new DownloadImageTask.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute(final Bitmap bitmap) {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ImageDialogFragment dialogFragment = ImageDialogFragment.newInstance(bitmap);
                                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                                    dialogFragment.show(fm, ImageDialogFragment.tag);
                                }
                            });
                        }
                    }
                }).execute(element.attr("src"));
                message.addView(imageView);
                break;
            default:
                TextView view = new TextView(context);
                view.setText(Html.fromHtml(element.html()));
                view.setAutoLinkMask(Linkify.ALL);
                message.addView(view);
        }
    }
}
