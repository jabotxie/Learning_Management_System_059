import javax.swing.*;
import java.util.ArrayList;

public class Client extends JComponent implements Runnable {

    /**
     *
     * @return
     * 1: login successfully
     * -1: login again
     * -2: quit
     */
    private int login() {
        return 1;
    }

    private int course() {
        return 1;
    }

    private int forum(Course course) {
        return 1;
    }

    private int post(DiscussionForum forum) {
        return 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Client());
    }

    @Override
    public void run() {
        int loginReturn;
        do {
            loginReturn = login();
            if (loginReturn == 1) {//login successfully
                ArrayList<Course> courses = DataServer.getCourses();
                int courseReturn;
                do {
                    courseReturn = course();
                    if (courseReturn > 0) {
                        Course course = courses.get(courseReturn - 1);
                        int forumReturn;
                        do {
                            forumReturn = forum(course);
                            if (forumReturn > 0) {
                                DiscussionForum forum = course.forums.get(forumReturn - 1);
                            }
                        } while (forumReturn != -1);
                    }
                } while (courseReturn != -1);
            }
        } while (loginReturn != -2);
    }
}
