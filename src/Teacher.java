import java.util.ArrayList;

public class Teacher extends User {


    public Teacher(String username, String password) {
        super(username, password);
    }

    public Teacher(String username, String password, ArrayList<DiscussionForum> forums) {
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
    public void createForum(String topic) {
        synchronized (o) {
            User.forums.add(new DiscussionForum(topic));
        }
    }

    @Override
    public void deleteForum(DiscussionForum forum) throws NoSuchTargetException {
        synchronized (o) {
            if (!User.forums.remove(forum)) throw new NoSuchTargetException();
        }
    }

    @Override
    public void editForum(DiscussionForum forum, String topic) throws NoSuchTargetException {
        synchronized (o) {
            int i = User.forums.indexOf(forum);
            if (i == -1) throw new NoSuchTargetException();
        }
    }
}
