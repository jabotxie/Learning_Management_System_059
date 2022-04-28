package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static client.Client.username;
import static util.Packet.ENTER_COURSE;
import static util.Packet.LOGOUT;

public class StudentCourseUI implements ActionListener {

    JFrame frame;
    JButton logoutButton = new JButton("Log out");
    JButton refreshButton = new JButton("Refresh");
    ArrayList<JButton> courseButtons = new ArrayList<>();
    String[] courseTitles;

    public StudentCourseUI(Point location) {

        Packet request = new Packet(Packet.REQUEST_COURSE_TITLES);
        Packet response = Client.getResponse(request);

        courseTitles = response.getMsg();

        frame = new JFrame("Learning Management System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocation(location);
        frame.setSize(600, 400);

        int relativeX = 0;
        int relativeY = 0;
        JPanel coursePanel = new JPanel();
        coursePanel.setBounds(relativeX, relativeY,
                265, courseTitles.length * 30 - 10);
        coursePanel.setLayout(null);

        List<Integer> lengths = new ArrayList<>();
        for (String courseTitle : courseTitles) {
            lengths.add(courseTitle.length());
        }

        int buttonWidth = Collections.max(lengths) * 16;

        for (int i = 0; i < courseTitles.length; i++) {
            JButton courseButton = new JButton(courseTitles[i]);
            courseButton.addActionListener(this);
            courseButton.setFocusable(false);
            courseButton.setBounds(0, i * 30, buttonWidth, 20);
            courseButtons.add(courseButton);

            coursePanel.add(courseButton);
        }

        JPanel functionPanel = new JPanel();
        functionPanel.setBounds(relativeX, relativeY + coursePanel.getHeight() + 20,
                80, 50);
        functionPanel.setLayout(null);

        logoutButton.addActionListener(this);
        logoutButton.setFocusable(false);
        logoutButton.setBounds(0, 0, 80, 20);

        refreshButton.addActionListener(this);
        refreshButton.setFocusable(false);
        refreshButton.setBounds(0, 30, 80, 20);

        functionPanel.add(logoutButton);
        functionPanel.add(refreshButton);

        frame.add(coursePanel);
        frame.add(functionPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == logoutButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to log out?", "Log Out Confirmation")) {
                Client.getResponse(new Packet(LOGOUT, new String[]{username}));
                frame.dispose();
                new AccountLogin(frame.getLocation());
            }
        }

        for (int i = 0; i < courseButtons.size(); i++) {
            JButton courseButton = courseButtons.get(i);
            if (e.getSource() == courseButton) {
                Packet request = new Packet(ENTER_COURSE, new String[]{courseTitles[i]});
                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new StudentForumUI(courseTitles[i], frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                            "Please refresh and try again.");
                    new TeacherCourseUI(frame.getLocation());
                }
            }
        }

        if (e.getSource() == refreshButton) {
            frame.dispose();
            new TeacherCourseUI(frame.getLocation());
        }
    }
}
