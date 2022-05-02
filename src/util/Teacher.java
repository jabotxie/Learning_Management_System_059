package util;

/**
 * Project 5 -- Learning Management System
 * <p>
 * This class represents a teacher. It is extended from util.User
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Nandana, Shreyash, Jason, Garv , lab sec L14
 * @version May 2nd, 2022
 */
public class Teacher extends User {
    //constructor for teacher class
    public Teacher(String username, String password) {
        super(username, password);
    }

    public Teacher(String username) {
        super(username, "");
    }
}
