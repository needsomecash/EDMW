package xyz.edmw.rest;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import xyz.edmw.post.Post;
import xyz.edmw.thread.Thread;

public interface ApiService {
    @GET("/forum/{forum}/page{page}")
    Call<List<Thread>> getThreads(@Path("forum") String forum, @Path("page") int page);

    @GET("/{path}/page{page}")
    Call<List<Post>> getPosts(@Path("path") String path, @Path("page") int page);
}