import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project 4 -- Learning Management System
 * <p>
 * This class helps to store the data to local file
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class CourseList implements Serializable {
    private final ArrayList<Course> courses;

    public CourseList(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "CourseList{" + "courses=" + courses +
                '}';
    }
}
