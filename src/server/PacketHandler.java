package server;

import util.*;

public class PacketHandler {
    //////////////////////////////////////////////////////////////////////////////////////
    //Login handlers

    /**
     * @param packet the packet sent by client
     * @return login in successful: operationSuccess = true
     * util.User is msgOne
     */
    static Packet login(Packet packet) {
        try {

            String username = packet.getMsg()[0];
            String password = packet.getMsg()[1];
            Class<? extends User> c = DataManager.checkToken(username, password);
            if (c == null) {
                return new Packet(false);
            } else {
                return new Packet(new String[]{c == Teacher.class ? "T" : "S"}, true);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return new Packet(false);
        }
    }


    static Packet create(Packet packet) {

        String userType = packet.getMsg()[0];
        String username = packet.getMsg()[1];
        String password = packet.getMsg()[2];

        boolean operationSuccess = DataManager.createAccount(userType.equals("T") ? Teacher.class : Student.class,
                username, password);
        return new Packet(operationSuccess);

    }

    static Packet delete(Packet request) {
        //TODO: delete user
        return request;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //Course Handlers
    static Packet createCourse(Packet request) {
        String courseTitle = request.getMsg()[0];
        boolean operationSuccess = DataManager.createCourse(courseTitle);
        return new Packet(operationSuccess);
    }

    static Packet editCourse(Packet request) {
        //TODO:
        return request;
    }

    static Packet deleteCourse(Packet request) {
        //TODO:
        return request;
    }

    static Packet enterCourse(Packet request) {
        //TODO:
        return request;
    }

    //////////////////////////////////////////////////////////////////////
    //Forum Handlers

    static Packet createForum(Packet request) {
        //TODO:
        return request;
    }

    static Packet editForum(Packet request) {
        //TODO:
        return request;
    }

    static Packet deleteForum(Packet request) {
        //TODO:
        return request;
    }

    static Packet enterForum(Packet request) {
        //TODO:
        return request;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Post Handlers
    static Packet createPost(Packet request) {
        //TODO:
        return request;
    }

    static Packet deletePost(Packet request) {
        //TODO:
        return request;
    }

    static Packet editPost(Packet request) {
        //TODO:
        return request;
    }

    static Packet replyPost(Packet request) {
        //TODO:
        return request;
    }

    static Packet votePost(Packet request) {
        //TODO:
        return request;
    }

    static Packet sortPost(Packet request) {
        //TODO:
        return request;
    }
    ////////////////////////////////////////////////////////////////////
    //String List Handlers

    static Packet requestCourseTitle() {
        return new Packet(DataManager.getCourseTitles());
    }

    static Packet requestForumTopics(Packet request) {
        //TODO:
        return request;
    }

    static Packet requestPostList(Packet request) {
        //TODO:
        return request;
    }
}
