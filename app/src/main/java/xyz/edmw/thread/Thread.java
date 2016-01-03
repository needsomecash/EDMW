package xyz.edmw.thread;

public class Thread {
    private final String title;
    private final String path;
    private final String lastPost;
    private int numPages = 15; //TODO

    public Thread(String title, String path, String lastPost) {
        this.title = title;
        this.path = path;
        this.lastPost = lastPost;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getLastPost() {
        return lastPost;
    }

    public int getNumPages() {
        return numPages;
    }
}
