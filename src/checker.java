import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class checker {
    public static void main(String[] args) throws IOException {
        DiscussionForum forum = new DiscussionForum("chegg");
        ArrayList<DiscussionPost> posts = new ArrayList<>();
        forum.addPost(new DiscussionPost("checker",System.currentTimeMillis()));
        forum.addPost(new DiscussionPost("checker2",System.currentTimeMillis()));
        forum.addPost(new DiscussionPost("checker3",System.currentTimeMillis()));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("forum.txt"));
        objectOutputStream.writeObject(forum);



    }
}
