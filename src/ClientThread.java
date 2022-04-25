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
        is = new ObjectInputStream(socket.getInputStream());
        os = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {

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
}
