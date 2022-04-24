import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI implements ActionListener {

    JFrame frame;

    JButton loginButton;
    JButton createButton;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameText;
    JTextField passwordText;




     public LoginUI() {
        frame = new JFrame("Learning Management System");

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
