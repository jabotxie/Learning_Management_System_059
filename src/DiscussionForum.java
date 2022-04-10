import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Project 4 -- Learning Management System
 *
 * This class contains methods for the Discussion Forum
 * that can be created and edited by the users i.e Teachers
 * and Students
 *
 * @author Nandana, Shreyash, Jason, Garv , lab sec L14
 *
 * @version April 9, 2022
 *
 */
public class DiscussionForum implements Serializable {

    private String topic;
    private ArrayList<DiscussionPost> posts;
    public final Object postsSync = new Object();

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Topic: ").append(topic);
        if (posts.size() != 0) {
            for (int i = 0; i < posts.size(); i++) {
                sb.append("\nPost ").append(i +1).append(". \n").append(posts.get(i));
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionForum forum = (DiscussionForum) o;
        return Objects.equals(topic, forum.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic);
    }
}
