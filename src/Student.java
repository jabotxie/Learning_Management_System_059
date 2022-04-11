import java.util.ArrayList;
import java.io.Serializable;

public class Student extends User implements Serializable {
    
    ArrayList<Reply> Replies;
    ArrayList<String> votedReplies;//replies on forum votes

    public Student(String username, String password) {
        super(username, password);
        this.Replies = new ArrayList<Reply>();
        this.votedReplies = new ArrayList<String>();
    }
    public ArrayList<Reply> getAllReplies(){
        return Replies;
    }
    public ArrayList<String> getVotedForumReply(){
        return votedReplies;
    }

    @Override
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        synchronized (forum.postsSync) {
            forum.addPost(post);
        }
    }

    @Override
    public void deletePost(DiscussionForum forum, DiscussionPost post) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void editPost(DiscussionPost post, String content) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void addReply(DiscussionPost post, DiscussionPost reply) {
        synchronized (post.repliesSync) {
            post.addReply(reply);
        }
    }

    @Override
    public void createForum(Course course, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void deleteForum(Course course, DiscussionForum forum) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void editForum(Course course, DiscussionForum forum, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void createCourse(String courseTitle) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void deleteCourse(Course course) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void editCourse(Course course, String courseTitle) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void vote(DiscussionForum forum, DiscussionPost post) throws AlreadyVotedException{
        if (forum.isUserVoted(this)) throw new AlreadyVotedException();

    }
     public String toString(){
        return String.format("Student[Username = %s, Password = %s]", super.getUsername(), super.getPassword());
    }
}


}
