package client;

import util.Packet;
import util.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CourseUI implements ActionListener {
    Socket socket;
    ObjectInputStream is;
    ObjectOutputStream os;

    User currentUser;
    JFrame frame = new JFrame("Learning Management System");
    JButton editButton = new JButton("edit");
    JButton createButton = new JButton("create");
    JButton deleteButton = new JButton("delete");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons = new ArrayList<>();

    public CourseUI(Socket socket, ObjectInputStream is, ObjectOutputStream os, User currentUser) {
        try {
            this.socket = socket;
            this.is = is;
            this.os = os;

            this.currentUser = currentUser;
            editButton.setFocusable(false);
            editButton.addActionListener(this);
            createButton.setFocusable(false);
            createButton.addActionListener(this);
            deleteButton.setFocusable(false);
            deleteButton.addActionListener(this);
            logoutButton.setFocusable(false);
            logoutButton.addActionListener(this);

            os.writeObject(new Packet(Packet.REQUEST_COURSE_TITLES));
            Packet response = (Packet) is.readObject();
            String[] courseTitles = response.getMsg();

            for (String title : courseTitles) {
                courseButtons.add(new JButton(title));
            }

            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            JPanel panel = new JPanel();
            panel.setBackground(Color.PINK);
            panel.add(editButton);
            panel.add(createButton);
            panel.add(deleteButton);
            panel.add(logoutButton);
            for (JButton course : courseButtons) {
                panel.add(course);
            }
            frame.add(panel);
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (socket != null) {
            if (e.getSource() == logoutButton) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                        "Do you want to sign out?", "Log Out Confirmation", JOptionPane.YES_NO_OPTION)) {
                    frame.dispose();
                    new LoginUI(socket, is, os);
                }
            }
        }
    }
}
