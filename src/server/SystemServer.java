package server;

import util.DataManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Project 5 -- Learning Management System
 * <p>
 *
 * A class thar initialized the server and creating the thread to communicate with different clients.
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 */
public class SystemServer {
    private static final int PORT = 4040;

    static ArrayList<String> onlineUsers = new ArrayList<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        DataManager.initData();
        ServerSocket socket = new ServerSocket(PORT);
        System.out.println(InetAddress.getLocalHost());
        try {
            while (true) {
                System.out.println("[SERVER] Waiting for client connection...");
                Socket client = socket.accept();
                System.out.println("[SERVER] Connected to client!");
                ClientThread clientThread = new ClientThread(client);

                pool.execute(clientThread);
            }
        } catch (Throwable throwable) {
            DataManager.saveData();
        } finally {
            DataManager.saveData();
        }
    }
}
