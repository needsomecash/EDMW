package xyz.edmw.post;

public class Post {
    private final String author;
    private final String timestamp;
    private final String message;

    public Post(String author, String timestamp, String message) {
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
