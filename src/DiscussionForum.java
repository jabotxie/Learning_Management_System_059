import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
}
