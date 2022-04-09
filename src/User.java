import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class User {
    public static ArrayList<DiscussionForum> forums;
    private String username;
    private String password;

    public User(String username, String password) {
        forums = new ArrayList<>();
        this.username = username;
        this.password = password;
    }

    public User(String username, String password,ArrayList<DiscussionForum> forums) {
        this.username = username;
        this.password = password;
        User.forums = forums;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (o.getClass() != Teacher.class && o.getClass() != Student.class)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public abstract void addPost(DiscussionForum forum, DiscussionPost post);

    public abstract void addReply(DiscussionPost post, DiscussionPost reply) throws NoSuchTargetException;

    public abstract void createForum(String topic) throws NoPermissionException;

    public abstract void deleteForum(DiscussionForum forum) throws NoPermissionException, NoSuchTargetException;

    public abstract void editForum(DiscussionForum forum, String topic) throws NoPermissionException, NoSuchTargetException;

    public String toString() {
        String sb = getClass().toString() + '\n' +
                "Username: " + username + '\n' +
                "Password: " + password + '\n';
        return sb;
    }
}
