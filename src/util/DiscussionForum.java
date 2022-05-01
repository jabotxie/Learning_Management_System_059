package util;

import java.io.Serializable;
import java.util.*;

/**
 * Project 4 -- Learning Management System
 * <p>
 * This class contains methods for the Discussion Forum
 * that can be created and edited by the users i.e Teachers
 * and Students
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Kundana, Shreyash, Jia Xie, Garv , lab sec L14
 * @version April 9, 2022
 */
public class DiscussionForum implements Serializable {

    private String topic;
    ArrayList<DiscussionPost> posts;
    public final Date postsSync = new Date(System.currentTimeMillis());

    public DiscussionForum() {
    }

    public DiscussionForum(String topic, ArrayList<DiscussionPost> posts) {
        this.topic = topic;
        this.posts = posts;
    }

    public DiscussionForum(String topic) {
        this.topic = topic;
        this.posts = new ArrayList<>();
    }

    //getter method to return topic string
    public String getTopic() {
        return topic;
    }

    //setter method to return topic string
    public void setTopic(String topic) {
        this.topic = topic;
    }

    //method to display list of discussion posts
    public void displayContentList() {
        for (int i = 0; i < posts.size(); i++) {
            DiscussionPost post = posts.get(i);
            System.out.println((i + 1) + ". " + post.getPostContent());
            if (i != posts.size() - 1) System.out.println();
        }
    }

    //getter method for discussion posts
    public ArrayList<DiscussionPost> getPosts() {
        Collections.sort(posts);
        return posts;

    }

    //getter method for number of posts
    public int getPostsNum() {
        return posts.size();
    }

    //getter method
    public void addPost(DiscussionPost post) {
        posts.add(post);
    }

    public void deletePost(DiscussionPost post) {
        posts.remove(post);
    }

    public boolean isUserVoted(User user) {
        for (DiscussionPost post : posts) {
            List<Vote> votes = post.getVotes();
            for (Vote vote : votes) {
                if (vote.getStudent().equals(user)) return true;
            }
        }
        return false;
    }

    //toString method for util.DiscussionForum class
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Topic: ").append(topic);
        if (posts.size() != 0) {
            for (int i = 0; i < posts.size(); i++) {
                sb.append("\nPost ").append(i + 1).append(". \n").append(posts.get(i));
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
