package client;

public class Client implements Runnable {

    @Override
    public void run() {
        new LoginUI();
    }
}