package client;

import util.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    static String username;
    static Socket socket;
    static ObjectInputStream is;
    static ObjectOutputStream os;

    public static void main(String[] args){
        try {
            socket = new Socket("localhost", 4242);
            is = new ObjectInputStream(socket.getInputStream());
            os = new ObjectOutputStream(socket.getOutputStream());
            new LoginUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Packet getResponse(Packet request) {
        try {
            os.writeObject(request);
            return (Packet) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Packet(false);
        }

    }
}