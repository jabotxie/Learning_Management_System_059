/**
 * Project 4 -- Learning Management System
 * <p>
 * Thrown when a teacher tries to vote
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class TeacherCannotVote extends Exception {
    @Override
    public String toString() {
        return "Only students can vote. You, as a teacher, can sort the posts based on students' votes";
    }
}
