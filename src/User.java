import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class User {

    public final Object o = new Object();
    private String username;
    private String password;

    public User(String username, String password) {

        this.username = username;
        this.password = password;
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

    public abstract void addReply(DiscussionPost post, DiscussionPost reply);

    public abstract void createForum(Course course, String topic) throws NoPermissionException;

    public abstract void deleteForum(Course course, DiscussionForum forum) throws NoPermissionException;

    public abstract void editForum(Course course, DiscussionForum forum, String topic) throws NoPermissionException;

    public abstract void createCourse(String courseTitle) throws NoPermissionException;

    public abstract void deleteCourse(Course course) throws NoPermissionException;

    public abstract void editCourse(Course course, String courseTitle) throws NoPermissionException;

    public String toString() {
        return getClass().toString() + '\n' +
                "Username: " + username + '\n' +
                "Password: " + password + '\n';
    }
}
