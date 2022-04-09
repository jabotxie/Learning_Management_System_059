import javax.swing.*;
import java.awt.event.ActionEvent;


public class main extends JComponent implements Runnable {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new SystemActivities());


    }

    @Override
    public void run() {



    }
}
