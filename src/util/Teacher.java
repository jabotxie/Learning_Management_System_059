package util;

/**
 * Project 4 -- Learning Management System
 * <p>
 * This class represents a teacher. It is extended from util.User
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
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
