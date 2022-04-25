package server;

import com.sun.istack.internal.NotNull;
import util.*;

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
                    e.printStackTrace();
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

    private Packet processPacket(@NotNull Packet packet) {
        int requestType = packet.getRequestType();
        switch (requestType) {
            case Packet.LOGIN:
                return login(packet);
            case Packet.CREATE:
                return create(packet);
            case Packet.REQUEST_COURSE_TITLES:
                return requestCourseTitle();

        }
        return new Packet(false);
    }

    /**
     * @param packet the packet sent by client
     * @return login in successful: operationSuccess = true
     * util.User is msgOne
     */
    private Packet login(Packet packet) {
        try {

            String username = packet.getMsg()[0];
            String password = packet.getMsg()[1];
            Class<? extends User> c = DataManager.checkToken(username, password);
            if (c == null) {
                return new Packet(false);
            } else {
                return new Packet(new String[]{c == Teacher.class ? "T" : "S"}, true);
            }
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return new Packet(false);
        }
    }


    private Packet create(Packet packet) {
        try {
            String userType = packet.getMsg()[0];
            String username = packet.getMsg()[1];
            String password = packet.getMsg()[2];

            boolean operationSuccess = DataManager.createAccount(userType.equals("T") ? Teacher.class : Student.class,
                    username, password);
            return new Packet(operationSuccess);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return new Packet(false);
        }
    }

    private Packet requestCourseTitle() {
        return new Packet(DataManager.getCourseTitles());
    }
}
