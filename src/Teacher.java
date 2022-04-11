/**
 * Project 4 -- Learning Management System
 * <p>
 * This class represents a teacher. It is extended from User
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class Teacher extends User {
    //constructor for teacher class
    public Teacher(String username, String password) {
        super(username, password);
    }

    @Override
    //overridden method allows teacher user to add posts
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        synchronized (forum.postsSync) {
            forum.addPost(post);
        }
    }

    @Override
    //overridden method allows teacher user to delete add posts
    public void deletePost(DiscussionForum forum, DiscussionPost post) {
        forum.deletePost(post);
    }

    @Override
    //overridden method allows teacher user to add replies
    public void addReply(DiscussionPost post, DiscussionPost reply) {
        synchronized (post.repliesSync) {
            post.addReply(reply);
        }
    }

    @Override
    //overridden method allows teacher create forums
    public void createForum(Course course, String topic) {
        synchronized (course.forumSync) {
            course.addForum(new DiscussionForum(topic));
        }
    }

    @Override
    //overridden method allows teacher user to delete forums
    public void deleteForum(Course course, DiscussionForum forum) {
        synchronized (course.forumSync) {
            course.deleteForum(forum);
        }
    }

    @Override
    //overridden method allows teacher user to edit forums
    public void editForum(Course course, DiscussionForum forum, String topic) {
        synchronized (course.forumSync) {
            course.editForum(forum, topic);
        }
    }

    @Override
    //overridden method allows teacher user to create courses
    public void createCourse(String courseTitle) {
        synchronized (DataManager.coursesSync) {
            DataManager.courses.add(new Course(courseTitle));
        }
    }

    @Override
    //overridden method allows teacher user delete courses
    public void deleteCourse(Course course) {
        synchronized (DataManager.coursesSync) {
            DataManager.courses.remove(course);
        }
    }

    @Override
    //overridden method allows teacher user edit courses
    public void editCourse(Course course, String courseTitle) {
        synchronized (DataManager.coursesSync) {
            course.setCourseTitle(courseTitle);
        }
    }

    @Override
    //overridden method prevents teacher user from voting
    public void vote(DiscussionForum forum, DiscussionPost post) throws TeacherCannotVoteException {
        throw new TeacherCannotVoteException();
    }
}
