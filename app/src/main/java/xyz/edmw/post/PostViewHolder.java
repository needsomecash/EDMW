package xyz.edmw.post;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.R;

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
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                message.addView(imageView);

                String source = element.attr("src");
                Ion.with(imageView)
                        .load(source);
                break;
            case "div":
                if (element.className().equals("bbcode_container")) {
                    View view = LayoutInflater.from(context).inflate(R.layout.view_quote, null);
                    PostViewHolder viewHolder = new PostViewHolder(context, view);

                    String postedBy = element.select("div.bbcode_postedby").first().text().trim();
                    String message = element.select("div.message").first().html();
                    Post post = new Post(postedBy, "", message);
                    viewHolder.setPost(post);

                    this.message.addView(view);
                    break;
                } else {
                    // fall through
                }
            default:
                TextView view = new TextView(context);
                view.setText(Html.fromHtml(element.html()));
                message.addView(view);
        }
    }
}
