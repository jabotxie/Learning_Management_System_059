package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Project 5 -- Learning Management System
 * <p>
 *
 * This class contains methods for the Discussion Posts
 * that can be created and edited by the users i.e Teachers
 * and Students
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Nandana, Shreyash, Jason, Garv , lab sec L14
 * @version May 2nd, 2022
 */

public class DiscussionPost implements Comparable<DiscussionPost>, Serializable {

    public final Date repliesSync = new Date(System.currentTimeMillis());
    private User owner;
    List<DiscussionPost> replies = new ArrayList<>();
    List<Vote> votes;
    private String postContent;
    Date postTime;

    //Constructor for util.DiscussionPost object
    public DiscussionPost(User owner, String postContent, long postTime) {
        this.owner = owner;
        replies = new ArrayList<>();
        this.postContent = postContent;
        this.postTime = new Date(postTime);
        this.votes = new ArrayList<>();
    }

    public DiscussionPost(String username, Date date) {
        owner = new Teacher(username);
        postTime = date;
    }

    //Constructor for util.DiscussionPost object
    public DiscussionPost(User owner, String postContent, Date postTime) {
        this.owner = owner;
        replies = new ArrayList<>();
        this.postContent = postContent;
        this.postTime = postTime;
        this.votes = new ArrayList<>();
    }

    //method to retrieve owner of post
    public User getOwner() {
        return owner;
    }

    //getter method for post content
    public String getPostContent() {
        return postContent;
    }

    //method that allows user to add reply to discussion posts
    public void addReply(DiscussionPost reply) {
        replies.add(0, reply);
    }

    //method that allows users to vote
    public void addVote(String username) {
        votes.add(new Vote(new Student(username)));
    }

    //method to retrieve voting results
    public int getVotesNum() {
        return votes.size();
    }

    //method to retrieve voting results
    public List<Vote> getVotes() {
        return votes;
    }

    public long getPostTime() {
        return postTime.getTime();
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    @Override
    public int compareTo(DiscussionPost o) {
        Integer voteNum = getVotesNum();
        Integer anotherVoteNum = o.getVotesNum();
        return voteNum.compareTo(anotherVoteNum) * (-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionPost post = (DiscussionPost) o;
        return Objects.equals(post.owner.getUsername(), owner.getUsername()) && Objects.equals(postTime, post.postTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replies, postContent, postTime);
    }

    //toString method for util.DiscussionPost class
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Post Time: ").append(postTime);
        if (votes.size() != 0) {
            sb.append('\n').append("Votes: ").append(getVotesNum());
        }
        sb.append('\n').append(owner).append(": ").append(postContent);


        if (replies.size() != 0) {
            sb.append('\n').append("    Replies: ");
            for (DiscussionPost reply : replies) {
                sb.append('\n').append("    ").append(reply.getOwner()).
                        append(": ").append(reply.getPostContent());
            }
        }

        return sb.toString();

    }

}
