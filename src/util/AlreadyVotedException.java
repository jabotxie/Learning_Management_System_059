package util;

/**
 * Project 4 -- Learning Management System
 * <p>
 * Throw when a student tries to vote more than once
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class AlreadyVotedException extends Exception {
    @Override
    public String toString() {
        return "Each student can only vote once.\nYou have already voted in this forum.";
    }
}
