package server;

import util.DataManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemServer {
    private static final int PORT = 4242;

    private static ArrayList<ClientThread> clientThreads = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        DataManager.initData();
        ServerSocket socket = new ServerSocket(PORT);
        while (true) {
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = socket.accept();
            System.out.println("[SERVER] Connected to client!");
            ClientThread clientThread = new ClientThread(client);
            clientThreads.add(clientThread);

            pool.execute(clientThread);
        }
    }
}
