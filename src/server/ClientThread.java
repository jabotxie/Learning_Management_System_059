package server;


import util.*;
import static server.PacketHandler.*;
import static util.Packet.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
    String username;
    Socket socket;
    ObjectInputStream is;
    ObjectOutputStream os;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());

    }

    @Override
    public void run() {
        try {
            while (true) {
                try {

                    Object o = is.readObject();
                    os.writeObject(getResponse(o));
                    DataManager.saveData();

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("[SERVER] Client Disconnected");
                    SystemServer.onlineUsers.remove(username);
                    break;
                }
            }
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Packet getResponse(Object request) {
        if (request.getClass() == Packet.class) {
            Packet packet = (Packet) request;
            return processPacket(packet);

        } else {
            return null;
        }
    }

    private Packet processPacket(Packet request) {
        int requestType = request.getRequestType();
        switch (requestType) {
            ////////////////////////////
            case LOGIN:
                Packet response = login(request);
                if (response.isOperationSuccess()) username = request.getMsg()[0];
                return response;
            case CREATE:
                return create(request);

            case LOGOUT:
                return logout(request);
            /////////////////////////////
            case CREATE_COURSE:
                return createCourse(request);
            case RENAME_COURSE:
                return renameCourse(request);
            case DELETE_COURSE:
                return deleteCourse(request);
            case ENTER_COURSE:
                return enterCourse(request);
            //////////////////////////////
            case CREATE_FORUM:
                return createForum(request);
            case EDIT_TOPIC:
                return editForum(request);
            case DELETE_FORUM:
                return deleteForum(request);
            case ENTER_FORUM:
                return enterForum(request);
            //////////////////////////////
            case CREATE_POST:
                return createPost(request);
            case DELETE_POST:
                return deletePost(request);
            case EDIT_POST:
                return editPost(request);
            case REPLY_POST:
                return replyPost(request);
            //////////////////////////////

            case REQUEST_COURSE_TITLES:
                return requestCourseTitle();
            case REQUEST_FORUM_TOPICS:
                return requestForumTopics(request);
            case REQUEST_POST_LIST:
                return requestPostList(request);
        }
        return new Packet(false);
    }
}
