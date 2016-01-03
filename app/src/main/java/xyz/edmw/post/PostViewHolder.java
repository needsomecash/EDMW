package xyz.edmw.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ortiz.touch.TouchImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.net.URL;

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
    private final View view;

    public PostViewHolder(Context context, View view) {
        this.context = context;
        this.view = view;
        ButterKnife.bind(this, view);
    }

    public void setPost(Post post) {
        message.removeAllViews();
        author.setText(Html.fromHtml(post.getAuthor() + " " + post.getTimestamp()));
        Element body = Jsoup.parseBodyFragment(post.getMessage()).body();
        for (Node node : body.childNodes()) {
            if (node instanceof TextNode) {
                TextView view = new TextView(context);
                String text = ((TextNode) node).text().trim();
                if (!text.isEmpty()) {
                    view.setText(Html.fromHtml(text));
                    view.setAutoLinkMask(Linkify.ALL);
                    message.addView(view);
                }
            } else if (node instanceof Element) {
                Element element = (Element) node;
                switch (element.tagName()) {
                    case "br":
                        // do nothing
                        // TODO this removes intentional newlines.
                        break;
                    case "img":
                        TouchImageView imageView = new TouchImageView(context);
                        new DownloadImageTask(imageView).execute(element.attr("src"));
                        message.addView(imageView);
                        break;
                    default:
                        TextView view = new TextView(context);
                        view.setText(Html.fromHtml(element.html()));
                        view.setAutoLinkMask(Linkify.ALL);
                        message.addView(view);
                }
            } else {
                Log.w(tag, "Unknown node.");
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final TouchImageView view;

        public DownloadImageTask(TouchImageView view) {
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(new URL(params[0]).openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            view.setImageBitmap(bitmap);
        }
    }
}
