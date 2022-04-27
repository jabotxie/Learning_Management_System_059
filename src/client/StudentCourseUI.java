package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentCourseUI implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons = new ArrayList<>();
    String[] courseTitles;

    public StudentCourseUI(Point location) {

        Packet request = new Packet(Packet.REQUEST_COURSE_TITLES);
        Packet response = Client.getResponse(request);

        courseTitles = response.getMsg();

        JFrame frame = new JFrame("Learning Management System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocation(location);
        frame.setSize(600, 400);

        int relativeX = 0;
        int relativeY = 0;
        JPanel coursePanel = new JPanel();
        coursePanel.setBounds(relativeX, relativeY + 10,
                265, courseTitles.length * 30 - 10);
        coursePanel.setLayout(null);

        for (int i = 0; i < courseTitles.length; i++) {
            JButton courseButton = new JButton(courseTitles[i]);
            courseButton.addActionListener(this);
            courseButton.setFocusable(false);
            courseButton.setBounds(0, i * 30, 80, 20);
            courseButtons.add(courseButton);

            coursePanel.add(courseButton);
        }
        frame.add(coursePanel);
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
