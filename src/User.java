import java.io.*;
import java.util.Objects;

/**
 * Project 4 -- Learning Management System
 * <p>
 * This class represents a user
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public abstract class User implements Serializable {

    private final String username;
    private final String password;

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static String getImportedFile(String fileName) throws FileNotFoundException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        try {
            String line = bf.readLine();
            while (line != null) {
                sb.append(line);
                line = bf.readLine();
                if (line != null) {
                    sb.append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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

    public abstract void deletePost(DiscussionForum forum, DiscussionPost post) throws NoPermissionException;

    public abstract void addReply(DiscussionPost post, DiscussionPost reply);

    public abstract void createForum(Course course, String topic) throws NoPermissionException;

    public abstract void deleteForum(Course course, DiscussionForum forum) throws NoPermissionException;

    public abstract void editForum(Course course, DiscussionForum forum, String topic) throws NoPermissionException;

    public abstract void createCourse(String courseTitle) throws NoPermissionException;

    public abstract void deleteCourse(Course course) throws NoPermissionException;

    public abstract void editCourse(Course course, String courseTitle) throws NoPermissionException;

    public String toString() {
        return getClass() == Teacher.class ? "Teacher " : "Student " + username;
    }

    public abstract void vote(DiscussionForum forum, DiscussionPost post) throws TeacherCannotVote, AlreadyVotedException;
}
