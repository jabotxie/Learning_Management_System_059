package client;

import util.Packet;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Project 5 -- Learning Management System
 * <p>
 * A class that initialized the client using sockets and helps to communicate with the server.
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 */

public class Client {

    public static String username;
    static Socket socket;
    static ObjectInputStream is;
    static ObjectOutputStream os;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 4040);
            is = new ObjectInputStream(socket.getInputStream());
            os = new ObjectOutputStream(socket.getOutputStream());
            new AccountLogin();
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "The server is offline. " +
                    "Please wait and try again.", "Server Offline", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Packet getResponse(Packet request) {
        try {
            os.writeObject(request);
            return (Packet) is.readObject();
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "The server is offline. " +
                    "Please wait and try again.", "Server Offline", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}