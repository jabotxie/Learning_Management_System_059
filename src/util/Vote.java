package util;

import java.io.Serializable;

/**
 * Project 5 -- Learning Management System
 * <p>
 * <p>
 * This class represents a vote.
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 */
public class Vote implements Serializable {

    Student student;

    public Vote(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Voter: " + student.getUsername();
    }
}
