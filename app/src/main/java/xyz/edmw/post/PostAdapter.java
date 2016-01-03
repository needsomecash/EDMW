package xyz.edmw.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import xyz.edmw.R;

public class PostAdapter extends BaseAdapter {
    private final Context context;
    private final List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_post, null);
            holder = new PostViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PostViewHolder) convertView.getTag();
        }
        holder.setPost(posts.get(position));
        return convertView;
    }
}
