import Exceptions.NoPermissionException;
import Exceptions.NoSuchTargetException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class User {
    public static ArrayList<DiscussionForum> forums;

    public User() {
        forums = new ArrayList<>();
    }

    public User(ArrayList<DiscussionForum> forums) {
        this.forums = forums;
    }


    public static ArrayList<String> getImportedFile(String fileName) throws FileNotFoundException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        ArrayList<String> lines = new ArrayList<>();
        try {
            String line = bf.readLine();
            while (line != null) {
                lines.add(line);
                line = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public abstract void addPost(DiscussionForum forum, DiscussionPost post);

    public abstract void addReply(DiscussionPost post, DiscussionPost reply);

    public abstract void createForum(String topic) throws NoPermissionException;

    public abstract void deleteForum(DiscussionForum forum) throws NoPermissionException, NoSuchTargetException;

    public abstract void editForum(DiscussionForum forum, String topic) throws NoPermissionException, NoSuchTargetException;

}
