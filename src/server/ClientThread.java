package server;

import com.sun.istack.internal.NotNull;
import util.*;
import static server.PacketHandler.*;
import static util.Packet.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
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

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("[SERVER] Client Disconnected");
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

    private Packet processPacket(@NotNull Packet request) {
        int requestType = request.getRequestType();
        switch (requestType) {
            case LOGIN:
                return login(request);
            case CREATE:
                return create(request);
            case CREATE_COURSE:
                return createCourse(request);
            case RENAME_COURSE:
                return renameCourse(request);
            case DELETE_COURSE:
                return deleteCourse(request);
            case ENTER_COURSE:
                return enterCourse(request);
            case REQUEST_COURSE_TITLES:
                return requestCourseTitle();
            case REQUEST_FORUM_TOPICS:
                return requestForumTopics(request);
        }
        return new Packet(false);
    }
}
