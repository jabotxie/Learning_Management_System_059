import java.util.ArrayList;

public class Student extends User {

    public Student(String username, String password) {
        super(username, password);
    }

    public Student(String username, String password, ArrayList<DiscussionForum> forums) {
        super(username, password, forums);
    }

    @Override
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        forum.addPost(post);
    }

    @Override
    public void addReply(DiscussionPost post, DiscussionPost reply) {
        post.addReply(reply);
    }

    @Override
    public void createForum(String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void deleteForum(DiscussionForum forum) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void editForum(DiscussionForum forum, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }
}
