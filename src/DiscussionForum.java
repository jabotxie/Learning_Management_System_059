import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class DiscussionForum implements Serializable {

    private String topic;
    private ArrayList<DiscussionPost> posts;

    public DiscussionForum(String topic, ArrayList<DiscussionPost> posts) {
        this.topic = topic;
        this.posts = posts;
    }

    public DiscussionForum(String topic) {
        this.topic = topic;
        this.posts = new ArrayList<>();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArrayList<DiscussionPost> getPosts(boolean isSorted) {
        if (isSorted) Collections.sort(posts);
        return posts;

    }

    public void addPost(DiscussionPost post) {
        posts.add(post);
    }

    public void deletePost(DiscussionPost post) throws NoSuchTargetException {
        if (!posts.remove(post)) throw new NoSuchTargetException();
    }
}
