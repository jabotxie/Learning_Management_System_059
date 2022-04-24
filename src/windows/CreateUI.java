package windows;

import data.AccountInfoNotMatchException;
import data.DataServer;
import data.Student;
import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUI implements ActionListener {
    JFrame frame = new JFrame("Learning Management System");

    JButton backToLoginButton = new JButton("Back to login");
    JButton createButton = new JButton("Create");
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField usernameText = new JTextField(10);
    JTextField passwordText = new JTextField(10);


    public CreateUI() {
        backToLoginButton.setFocusable(false);
        backToLoginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


        JPanel panel = new JPanel();
        panel.add(usernameLabel);
        panel.add(usernameText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(createButton);
        panel.add(backToLoginButton);
        frame.add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToLoginButton) {
            frame.dispose();
            new LoginUI();
        }
        if (e.getSource() == createButton) {

            String password = passwordText.getText();
            String username = usernameText.getText();
            Class<? extends User> userClass = Student.class;
            User currentUser = DataServer.createAccount(userClass, username, password);
            if (currentUser == null) {
                JOptionPane.showMessageDialog(null, "The username has already been taken. " +
                        "Please enter another username", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                new CourseUI(currentUser);
            }
        }
    }
}
