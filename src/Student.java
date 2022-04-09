import Exceptions.NoPermissionException;

public class Student extends User {

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
