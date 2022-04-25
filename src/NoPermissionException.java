/**
 * Project 4 -- Learning Management System
 * <p>
 * Thrown when the user does not have the permission to do the intended operation
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class NoPermissionException extends Exception {
    @Override
    public String toString() {
        return "You don't have permission to proceed the action.";
    }
}
