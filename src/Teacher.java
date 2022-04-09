import Exceptions.NoPermissionException;
import Exceptions.NoSuchTargetException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Teacher extends User {

    public Teacher(ArrayList<DiscussionForum> forums) {
        super(forums);
    }

    public Teacher() {
        super();
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
        User.forums.add(new DiscussionForum(topic));
    }

    @Override
    public void deleteForum(DiscussionForum forum) throws NoSuchTargetException {
        if (!User.forums.remove(forum)) throw new NoSuchTargetException();
    }

    @Override
    public void editForum(DiscussionForum forum, String topic) throws NoSuchTargetException {
        int i = User.forums.indexOf(forum);
        if (i == -1) throw new NoSuchTargetException();
    }
}
