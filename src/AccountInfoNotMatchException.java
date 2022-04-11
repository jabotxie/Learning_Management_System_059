/**
 * Project 4 -- Learning Management System
 *
 * throw if account info does not match with the database
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 *
 * @version April 11, 2022
 */
public class AccountInfoNotMatchException extends Exception {
    @Override
    public String toString() {
        return "Username entered doesn't exist or the password is incorrect. Please try again.";
    }
}