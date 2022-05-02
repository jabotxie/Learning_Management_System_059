package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Project 5 -- Learning Management System
 * <p>
 *
 * A class that represents a course.
 * A course is identified by its title
 * There are forums in a course
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 */
public class Course implements Serializable {

    private String courseTitle;
    ArrayList<DiscussionForum> forums = new ArrayList<>();
    public final Date forumSync = new Date(System.currentTimeMillis());

    public Course(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    //method allows adding post to forum
    public void addForum(String topic) {
        forums.add(new DiscussionForum(topic));
    }

    //method allows editing post in forum
    public void editForum(DiscussionForum discussionForum, String topic) {
        discussionForum.setTopic(topic);
    }

    //method allows deleting a post in the forum
    public void deleteForum(DiscussionForum discussionForum) {
        forums.remove(discussionForum);
    }

    //getter method for course title
    public String getCourseTitle() {
        return courseTitle;
    }

    //setter method for course title
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String[] getForumTopics() {
        String[] topics = new String[forums.size()];
        for (int i = 0; i < forums.size(); i++) {
            DiscussionForum forum = forums.get(i);
            topics[i] = forum.getTopic();
        }
        return topics;
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

    //toString method for course class
    public String toString() {
        StringBuilder sb = new StringBuilder("util.Course: ").append(courseTitle);
        if (forums != null && forums.size() != 0) {
            sb.append('\n').append("Forums:");
            for (DiscussionForum forum : forums) {
                sb.append('\n').append(forum);
            }
        }

        return sb.toString();
    }
}
