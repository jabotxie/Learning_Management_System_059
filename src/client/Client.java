package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {



    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 4242);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            new LoginUI(socket, is, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}