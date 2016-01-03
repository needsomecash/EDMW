package xyz.edmw.thread;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.edmw.rest.RestClient;

public class ThreadFragment extends ListFragment {
    private static final String tag = "ThreadFragment";
    private static final String ARG_FORUM = "arg_forum";
    private static final String ARG_PAGE = "arg_page";
    private OnThreadSelectedListener callback;
    private List<Thread> threads;

    public static ThreadFragment newInstance(String forum, int page) {
        ThreadFragment fragment = new ThreadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FORUM, forum);
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (callback != null) {
            callback.onThreadSelected(threads.get(position));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnThreadSelectedListener) context;
        } catch (ClassCastException e) {
            Log.e(tag, "Context needs to implement OnThreadSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Bundle args = getArguments();
            String forum = args.getString(ARG_FORUM);
            int page = args.getInt(ARG_PAGE);

            Call<List<Thread>> calls = RestClient.getService().getThreads(forum, page);
            calls.enqueue(new Callback<List<Thread>>() {
                @Override
                public void onResponse(Response<List<Thread>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        threads = response.body();
                        setListAdapter(new ThreadAdapter(getContext(), threads));
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    threads = null;
                }
            });
        }
    }

    public interface OnThreadSelectedListener {
        void onThreadSelected(Thread thread);
    }
}
