import java.util.ArrayList;

public class Student extends User {

    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        synchronized (forum.postsSync) {
            forum.addPost(post);
        }
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
    public void vote(DiscussionPost post) throws TeacherCannotVote {

    }


}
