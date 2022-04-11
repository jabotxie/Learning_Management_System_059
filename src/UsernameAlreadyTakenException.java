/**
 * Project 4 -- Learning Management System
 *
 * Thrown when the username is already taken
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 *
 * @version April 11, 2022
 */
public class UsernameAlreadyTakenException extends Exception {
    @Override
    public String toString() {
        return "The username is already taken. Please try another one.";
    }
}
