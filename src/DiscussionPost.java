import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Project 4 -- Learning Management System
 * <p>
 * This class contains methods for the Discussion Posts
 * that can be created and edited by the users i.e Teachers
 * and Students
 *
 * @author Nandana, Shreyash, Jason, Garv , lab sec L14
 * @version April 9, 2022
 */

public class DiscussionPost implements Comparable<DiscussionPost>, Serializable {

    public final Date repliesSync = new Date(System.currentTimeMillis());
    private User owner;
    ArrayList<DiscussionPost> replies;
    ArrayList<Vote> votes;
    private String postContent;
    Date postTime;

    public DiscussionPost(User owner, String postContent, long postTime) {
        this.owner = owner;
        replies = new ArrayList<>();
        this.postContent = postContent;
        this.postTime = new Date(postTime);
        this.votes = new ArrayList<>();
    }

    public DiscussionPost(User owner, String postContent, Date postTime) {
        this.owner = owner;
        replies = new ArrayList<>();
        this.postContent = postContent;
        this.postTime = postTime;
        this.votes = new ArrayList<>();
    }

    public DiscussionPost() {

    }


    public User getOwner() {
        return owner;
    }

    public String getPostContent() {
        return postContent;
    }

    public void addReply(DiscussionPost reply) {
        replies.add(reply);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public int getVotesNum() {
        return votes.size();
    }

    public ArrayList<Vote> getVotes() {
        return votes;
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
        return Objects.equals(replies, post.replies) && Objects.equals(postContent, post.postContent) && Objects.equals(postTime, post.postTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replies, postContent, postTime);
    }

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
