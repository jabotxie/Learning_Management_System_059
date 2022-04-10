import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Course {

    private String courseTitle;
    ArrayList<DiscussionForum> forums;
    public final Object forumSync = new Object();

    public Course(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void addForum(DiscussionForum forum) {
        forums.add(forum);
    }

    public void editForum(DiscussionForum discussionForum, String topic) throws NoSuchTargetException {
        int i = forums.indexOf(discussionForum);
        try {
            forums.get(i).setTopic(topic);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchTargetException();
        }
    }

    public void deleteForum(DiscussionForum discussionForum) {
        forums.remove(discussionForum);
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseTitle, course.courseTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseTitle);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Course: ").append(courseTitle);
        if (forums.size() != 0) {
            sb.append('\n').append("Forums:");
            for (DiscussionForum forum : forums) {
                sb.append('\n').append(forum);
            }
        }

        return sb.toString();
    }
}
