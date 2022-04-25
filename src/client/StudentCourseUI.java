package client;

import util.Packet;

import static util.Packet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class StudentCourseUI implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons = new ArrayList<>();

    public StudentCourseUI() {
        try {

            logoutButton.setFocusable(false);
            logoutButton.addActionListener(this);

            Packet response = Client.getResponse(new Packet(Packet.REQUEST_COURSE_TITLES));
            String[] courseTitles = response.getMsg();

            for (String title : courseTitles) {
                courseButtons.add(new JButton(title));
            }

            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            JPanel panel = new JPanel();
            panel.setBackground(Color.PINK);
            panel.add(logoutButton);
            for (JButton course : courseButtons) {
                panel.add(course);
            }
            frame.add(panel);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == logoutButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                    "Do you want to sign out?", "Log Out Confirmation", JOptionPane.YES_NO_OPTION)) {
                frame.dispose();
                new LoginUI();
            }
        }


    }
}
