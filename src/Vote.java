import java.io.Serializable;

/**
 * Project 4 -- Learning Management System
 * <p>
 * This class represents a vote.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class Vote implements Serializable {
    private final Student student;

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
