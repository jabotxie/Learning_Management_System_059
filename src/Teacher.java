/**
 * Project 4 -- Learning Management System
 *
 * This class represents a teacher. It is extended from User
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 *
 * @version April 11, 2022
 */
public class Teacher extends User {

    public Teacher(String username, String password) {
        super(username, password);
    }

    @Override
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        synchronized (forum.postsSync) {
            forum.addPost(post);
        }
    }

    @Override
    public void deletePost(DiscussionForum forum, DiscussionPost post) {
        forum.deletePost(post);
    }

    @Override
    public void editPost(DiscussionPost post, String content) {
        post.setPostContent(content);
    }

    @Override
    public void addReply(DiscussionPost post, DiscussionPost reply) {
        synchronized (post.repliesSync) {
            post.addReply(reply);
        }
    }

    @Override
    public void createForum(Course course, String topic) {
        synchronized (course.forumSync) {
            course.addForum(new DiscussionForum(topic));
        }
    }

    @Override
    public void deleteForum(Course course, DiscussionForum forum) {
        synchronized (course.forumSync) {
            course.deleteForum(forum);
        }
    }

    @Override
    public void editForum(Course course, DiscussionForum forum, String topic) {
        synchronized (course.forumSync) {
            course.editForum(forum, topic);
        }
    }

    @Override
    public void createCourse(String courseTitle) {
        synchronized (DataManager.coursesSync) {
            DataManager.courses.add(new Course(courseTitle));
        }
    }

    @Override
    public void deleteCourse(Course course) {
        synchronized (DataManager.coursesSync) {
            DataManager.courses.remove(course);
        }
    }

    @Override
    public void editCourse(Course course, String courseTitle) {
        synchronized (DataManager.coursesSync) {
            course.setCourseTitle(courseTitle);
        }
    }

    @Override
    public void vote(DiscussionForum forum, DiscussionPost post) throws TeacherCannotVote {
        throw new TeacherCannotVote();
    }
}
