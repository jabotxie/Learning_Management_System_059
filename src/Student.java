/**
 * Project 4 -- Learning Management System
 * <p>
 * A student class that represents a student. It is extended from User. The class handles all functionality students can perform such as replying, voting, and viewing
 their course dashboard.

 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Kundana Nittala, Jia Xie
 * @version April 11, 2022
 */
public class Student extends User {
    
    //Here the Student constructor is declared with the username and password parameters. The username and password variables are brought into the class
    //through inheritance and the utilization of super.

    public Student(String username, String password) {
        super(username, password);
    }
    
    //Here the addPost method is utilized for parameters forum and post. Adding a post to the forum is synchronized.

    @Override
    public void addPost(DiscussionForum forum, DiscussionPost post) {
        synchronized (forum.postsSync) {
            forum.addPost(post);
        }
    }

     //Here the deletePost method is utilized for parameters forum and post. Throws the NoPermissionException.
    
    @Override
    public void deletePost(DiscussionForum forum, DiscussionPost post) throws NoPermissionException {
        throw new NoPermissionException();
    }
    

    @Override
    public void addReply(DiscussionPost post, DiscussionPost reply) {
        synchronized (post.repliesSync) {
            post.addReply(reply);
        }
    }

    
    @Override
    public void createForum(Course course, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void deleteForum(Course course, DiscussionForum forum) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void editForum(Course course, DiscussionForum forum, String topic) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void createCourse(String courseTitle) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void deleteCourse(Course course) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void editCourse(Course course, String courseTitle) throws NoPermissionException {
        throw new NoPermissionException();
    }

    @Override
    public void vote(DiscussionForum forum, DiscussionPost post) throws AlreadyVotedException {
        if (forum.isUserVoted(this)) throw new AlreadyVotedException();
        post.addVote(new Vote(this));
    }


}
