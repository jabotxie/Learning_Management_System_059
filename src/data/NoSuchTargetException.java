package data;

/**
 * Project 4 -- Learning Management System
 * <p>
 * Thrown when there is no such course, forum, post, or reply
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class NoSuchTargetException extends Exception {
    @Override
    public String toString() {
        return "No such reply under this post.";
    }

}
