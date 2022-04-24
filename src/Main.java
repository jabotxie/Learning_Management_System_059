import data.DataServer;
import windows.LoginUI;

public class Main {
    public static void main(String[] args) {
        DataServer.initData();
        LoginUI loginUI = new LoginUI();

    }
}
