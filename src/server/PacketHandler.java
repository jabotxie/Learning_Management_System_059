package server;

import util.*;

import java.util.Date;

/**
 * Project 5 -- Learning Management System
 * <p>
 * A class that process the packet receiving from the client and return a response packet to the client
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 * @return login in successful: operationSuccess = true
 * util.User is msgOne
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 */

public class PacketHandler {
    //////////////////////////////////////////////////////////////////////////////////////
    //Login handlers

    static Packet login(Packet packet) {

        String username = packet.getMsg()[0];
        String password = packet.getMsg()[1];
        Class<? extends User> c = DataManager.checkToken(username, password);
        if (c == null) {
            return new Packet("Password incorrect or user doesn't exist. Please try again.", false);
        } else {
            if (SystemServer.onlineUsers.contains(username))
                return new Packet("User is already logged in another client. Please log out and try again.",
                        false);
            SystemServer.onlineUsers.add(username);
            return new Packet(new String[]{c == Teacher.class ? "T" : "S"}, true);

        }

    }


    static Packet createAccount(Packet packet) {

        String userType = packet.getMsg()[0];
        String username = packet.getMsg()[1];
        String password = packet.getMsg()[2];

        boolean operationSuccess = DataManager.createAccount(userType.equals("T") ? Teacher.class : Student.class,
                username, password);
        return new Packet(operationSuccess);

    }

    static Packet deleteAccount(Packet request) {
        String username = request.getMsg()[0];
        String password = request.getMsg()[1];
        Class<? extends User> c = DataManager.checkToken(username, password);
        User deletingUser = c == Teacher.class ? new Teacher(username, password) : new Student(username, password);
        if (c == null) {
            return new Packet("Password incorrect or user doesn't exist. Please try again.", false);
        } else {
            if (SystemServer.onlineUsers.contains(username))
                return new Packet("User is already logged in another client. Please log out and try again.",
                        false);
            DataManager.deleteAccount(deletingUser, username, password);
            return new Packet(true);

        }

    }

    static Packet logout(Packet request) {
        SystemServer.onlineUsers.remove(request.getMsg()[0]);
        return new Packet(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //Course Handlers
    static Packet createCourse(Packet request) {
        String courseTitle = request.getMsg()[0];
        boolean operationSuccess = DataManager.createCourse(courseTitle);
        return new Packet(operationSuccess);
    }

    static Packet renameCourse(Packet request) {
        String oldTitle = request.getMsg()[0];
        String updatingTitle = request.getMsg()[1];
        return new Packet(DataManager.renameCourse(oldTitle, updatingTitle));
    }

    static Packet deleteCourse(Packet request) {
        String courseTitle = request.getMsg()[0];
        boolean operationSuccess = DataManager.deleteCourse(courseTitle);
        return new Packet(operationSuccess);
    }

    static Packet enterCourse(Packet request) {
        synchronized (DataManager.coursesSync) {
            return new Packet(DataManager.courses.contains(new Course(request.getMsg()[0])));
        }
    }

    //////////////////////////////////////////////////////////////////////
    //Forum Handlers

    static Packet createForum(Packet request) {
        String course = request.getMsg()[0];
        String topic = request.getMsg()[1];
        int returnCaseNum = DataManager.createForum(course, topic);
        switch (returnCaseNum) {
            case -2:
                return new Packet("Course", false);
            case -1:
                return new Packet("Forum", false);
            default:
                return new Packet(true);
        }
    }

    static Packet editForum(Packet request) {
        String course = request.getMsg()[0];
        String oldTopic = request.getMsg()[1];
        String newTopic = request.getMsg()[2];
        int returnCaseNum = DataManager.editForum(course, oldTopic, newTopic);
        switch (returnCaseNum) {
            case -3:
                return new Packet("Overlap", false);
            case -1:
                return new Packet("Course", false);
            case -2:
                return new Packet("Forum", false);
            default:
                return new Packet(true);
        }
    }

    static Packet deleteForum(Packet request) {
        String course = request.getMsg()[0];
        String deletingTopic = request.getMsg()[1];
        int returnCaseNum = DataManager.deleteForum(course, deletingTopic);
        switch (returnCaseNum) {
            case -1:
                return new Packet("Course", false);
            case -2:
                return new Packet("Forum", false);
            default:
                return new Packet(true);
        }
    }

    static Packet enterForum(Packet request) {
        String course = request.getMsg()[0];
        String topic = request.getMsg()[1];
        int returnCaseNum = DataManager.checkForumExistence(course, topic);
        switch (returnCaseNum) {
            case -1:
                return new Packet("Course", false);
            case -2:
                return new Packet("Forum", false);
            default:
                return new Packet(true);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    //Post Handlers
    static Packet createPost(Packet request) {
        String course = request.getMsg()[0];
        String topic = request.getMsg()[1];
        String userType = request.getMsg()[2];
        String username = request.getMsg()[3];
        String post = request.getMsg()[4];
        int returnCaseNum = DataManager.createPost(course, topic, userType, username, post);
        switch (returnCaseNum) {
            case -1:
                return new Packet(new String[]{"Course", "Course doesn't exist, please try again."});
            case -2:
                return new Packet(new String[]{"Forum", "Forum doesn't exist, please try again."});
            default:
                return new Packet(true);
        }
    }

    public static Packet deletePost(Packet request) {
        String course = request.getMsg()[0];
        String topic = request.getMsg()[1];
        String username = request.getMsg()[2];
        Date postTime = new Date(Long.parseLong(request.getMsg()[3]));
        int returnCaseNum = DataManager.deletePost(course, topic, username, postTime);
        switch (returnCaseNum) {
            case -1:
                return new Packet(new String[]{"Course", "Course doesn't exist, please try again."});
            case -2:
                return new Packet(new String[]{"Forum", "Forum doesn't exist, please try again."});
            case -3:
                return new Packet(new String[]{"Post", "Post doesn't exist, please try again."});
            default:
                return new Packet(true);
        }
    }

    static Packet editPost(Packet request) {
        return DataManager.editPost(request);
    }

    static Packet replyPost(Packet request) {
        return DataManager.replyPost(request);
    }

    static Packet votePost(Packet request) {
        return DataManager.votePost(request);

    }

    ////////////////////////////////////////////////////////////////////
    //String List Handlers

    static Packet requestCourseTitle() {
        return new Packet(DataManager.getCourseTitles());
    }

    static Packet requestForumTopics(Packet request) {
        String courseTitle = request.getMsg()[0];
        int courseIndex = DataManager.courses.indexOf(new Course(courseTitle));
        if (courseIndex == -1) return new Packet(false);
        else return new Packet(DataManager.courses.get(courseIndex).getForumTopics(), true);

    }

    public static Packet requestPostList(Packet request) {
        String courseTitle = request.getMsg()[0];
        String topic = request.getMsg()[1];
        return DataManager.getPostDisplayStrings(courseTitle, topic);
    }

    public static Packet requestPostListByVote(Packet request) {
        String courseTitle = request.getMsg()[0];
        String topic = request.getMsg()[1];
        return DataManager.getPostDisplayStringsByVote(courseTitle, topic);
    }
}
