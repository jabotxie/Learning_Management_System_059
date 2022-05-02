package util;

import java.io.*;
import java.util.Objects;

/**
 * Project 5 -- Learning Management System
 * <p>
 * This class represents a user
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Nandana, Shreyash, Jason, Garv , lab sec L14
 * @version May 2nd, 2022
 */
public class User implements Serializable {

    private final String username;
    private final String password;

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
        this.password = "";
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

//    //method allows user to add posts
//    public abstract void addPost(DiscussionForum forum, DiscussionPost post);
//
//    //method allows user to delete posts
//    public abstract void deletePost(DiscussionForum forum, DiscussionPost post) throws NoPermissionException;
//
//    //method allows user to add replies
//    public abstract void addReply(DiscussionPost post, DiscussionPost reply);
//
//    //method allows user to create forum
//    public abstract void createForum(Course course, String topic) throws NoPermissionException;
//
//    //method allows user to delete forum
//    public abstract void deleteForum(Course course, DiscussionForum forum) throws NoPermissionException;
//
//    //method allows user to edit forum
//    public abstract void editForum(Course course, DiscussionForum forum, String topic) throws NoPermissionException;
//
//    //method allows user to create course
//    public abstract void createCourse(String courseTitle) throws NoPermissionException;
//
//    //method allows user to delete course
//    public abstract void deleteCourse(Course course) throws NoPermissionException;
//
//    //method allows user to edit course
//    public abstract void editCourse(Course course, String courseTitle) throws NoPermissionException;

    //toString method for user class
    public String toString() {
        return getClass() == Teacher.class ? "Teacher " + username : "Student " + username;
    }

//    public abstract void vote(DiscussionForum forum, DiscussionPost post) throws TeacherCannotVoteException, AlreadyVotedException;
}
