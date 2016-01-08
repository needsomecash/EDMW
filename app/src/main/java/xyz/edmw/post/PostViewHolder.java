package xyz.edmw.post;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.ImageViewBitmapInfo;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.MainActivity;
import xyz.edmw.R;
import xyz.edmw.image.ImageDialogFragment;

public class PostViewHolder {
    @Bind(R.id.post_author)
    TextView author;
    @Bind(R.id.post_message)
    LinearLayout message;

    private static final String tag = "PostViewHolder";
    private final Context context;
    final int displayWidth;

    public PostViewHolder(Context context, View view) {
        this.context = context;
        displayWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
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

                final String source = element.attr("src");
                if(source.contains("www.edmw.xyz")) {

                    Ion.with(imageView)
                            .animateGif(AnimateGifMode.ANIMATE)
                            .load(source)
                            .setCallback(new FutureCallback<ImageView>() {

                                @SuppressLint("NewApi")
                                @Override
                                public void onCompleted(Exception arg0,
                                                        ImageView arg1) {

                                    imageView.getViewTreeObserver().addOnPreDrawListener(
                                            new ViewTreeObserver.OnPreDrawListener() {
                                                public boolean onPreDraw() {

                                                    imageView.getViewTreeObserver()
                                                            .removeOnPreDrawListener(this);

                                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                                    imageView.setAdjustViewBounds(true);
                                                    imageView.getLayoutParams().width = imageView.getDrawable().getIntrinsicWidth()*6;
                                                    return true;
                                                }
                                            });
                                }
                            });
                    message.addView(imageView);

                } else {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setAdjustViewBounds(true);

                    Ion.with(imageView)
                            .load(source);
                    message.addView(imageView);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                            ImageDialogFragment a = new ImageDialogFragment();
                            a.newInstance(source);
                            a.show(fm, "dialog_image");
                        }
                    });
                }


                break;
            case "div":
                if (element.className().equals("bbcode_container")) {
                    View view = LayoutInflater.from(context).inflate(R.layout.view_quote, null);
                    PostViewHolder viewHolder = new PostViewHolder(context, view);

                    Element quoteElement = element.select("div.bbcode_postedby").first();
                    String quoteBy = "";
                    if(quoteElement != null) {
                        quoteBy = quoteElement.text().trim();
                        quoteBy = quoteBy.replace(" View Post", "");
                    }
                    Element messageElement = element.select("div.message").first();
                    String quoteMessage = "";
                    if(messageElement != null) {
                        quoteMessage = messageElement.html();
                    }

                    if(quoteMessage != null && !quoteMessage.isEmpty()) {
                        Post post = new Post(quoteBy, "", quoteMessage);
                        viewHolder.setPost(post);
                        this.message.addView(view);
                    }
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
