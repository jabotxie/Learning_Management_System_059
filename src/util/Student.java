package util;

/**
 * Project 5 -- Learning Management System
 * <p>
 * A student class that represents a student. It is extended from util.User. The class handles all functionality students can perform such as replying, voting, and viewing
 * their course dashboard.
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Nandana, Shreyash, Jason, Garv , lab sec L14
 * @version May 2nd, 2022
 */
public class Student extends User {

    //Here the util.Student constructor is declared with the username and password parameters. The username and password variables are brought into the class
    //through inheritance and the utilization of super.

    public Student(String username, String password) {
        super(username, password);
    }

    public Student(String username) {
        super(username, "");
    }


}
