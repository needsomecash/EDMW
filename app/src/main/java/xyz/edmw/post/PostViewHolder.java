package xyz.edmw.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ortiz.touch.TouchImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        Element body = Jsoup.parse(post.getMessage()).body();
        for (Element element : body.getAllElements()) {
            final View view;
            switch (element.tagName()) {
                case "img":
                    view = new TouchImageView(context);
                    new DownloadImageTask((TouchImageView) view).execute(element.attr("src"));
                    break;
                default:
                    view = new TextView(context);
                    ((TextView) view).setText(Html.fromHtml(element.html()));
            }
            message.addView(view);
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
