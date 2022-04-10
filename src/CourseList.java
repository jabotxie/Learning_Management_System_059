import java.io.Serializable;
import java.util.ArrayList;

public class CourseList implements Serializable {
    private ArrayList<Course> courses;
    public CourseList(ArrayList<Course> courses) {
        this.courses = courses;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        String sb = "CourseList{" + "courses=" + courses +
                '}';
        return sb;
    }
}
