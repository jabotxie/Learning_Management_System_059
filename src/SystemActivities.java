import javax.swing.*;
import java.awt.event.ActionEvent;

public class SystemActivities implements Runnable {
    public static User currentUser;


    @Override
    public void run() {
        JFrame frame = new JFrame("Learning Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);

        // Adds Button to content pane of frame


        JTextField userNameTF = new JTextField("Username",10);
        JTextField passwordTF = new JTextField("password", 10);
        JButton loginButton = new JButton("Log In");

        JPanel panel = new JPanel();
        panel.add(userNameTF);
        panel.add(passwordTF);
        panel.add(loginButton);
        frame.getContentPane().add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = userNameTF.getText();
                String password = passwordTF.getText();

                System.out.println(username);
                System.out.println(password);

            }
        });
    }
}
