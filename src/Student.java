/**
 * Project 4 -- Learning Management System
 * <p>
 * A student class that represents a student. It is extended from User
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class Student extends User {
    //constructor for Student Class
    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    //method to add posts to the discussion forum
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        synchronized (forum.postsSync) {
            forum.addPost(post);
        }
    }

    @Override
    //prevents student user from deleting posts or forums
    public void deletePost(DiscussionForum forum, DiscussionPost post) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to add replies to discussion posts
    public void addReply(DiscussionPost post, DiscussionPost reply) {
        synchronized (post.repliesSync) {
            post.addReply(reply);
        }
    }

    @Override
    //overridden method to prevent student users from creating forums
    public void createForum(Course course, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to prevent student from deleting posts or forums
    public void deleteForum(Course course, DiscussionForum forum) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to prevent student from editing the forum
    public void editForum(Course course, DiscussionForum forum, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to prevent student from creating a course
    public void createCourse(String courseTitle) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to prevent student from deleting a course
    public void deleteCourse(Course course) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to prevent student from editing a course
    public void editCourse(Course course, String courseTitle) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    //overridden method to prevent student from voting more than once
    public void vote(DiscussionForum forum, DiscussionPost post) throws AlreadyVotedException {
        if (forum.isUserVoted(this)) throw new AlreadyVotedException();
        post.addVote(new Vote(this));
    }


}
