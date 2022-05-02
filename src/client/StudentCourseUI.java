package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        if (response == null) {
            frame.dispose();
        } else {
            courseTitles = response.getMsg();

            frame = new JFrame("Learning Management System");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.setLocation(location);
            frame.setSize(600, 400);

            int relativeX = 10;
            int relativeY = 10;

            JPanel header = new JPanel();
            header.setLayout(null);
            JLabel userType = new JLabel("User Type: Student");
            userType.setBounds(0, 0, 200, 20);

            JLabel username = new JLabel("Username: " + Client.username);
            username.setBounds(0, 20, 100 + Client.username.length() * 16, 20);

            header.add(userType);
            header.add(username);
            header.setBounds(relativeX, relativeY, 600, 40);

            relativeY += 40;

            JPanel coursePanel = new JPanel();
            JPanel functionPanel = new JPanel();

            if (courseTitles != null && courseTitles.length != 0) {

                coursePanel.setBounds(relativeX, relativeY,
                        265, courseTitles.length * 30);
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
                functionPanel.setBounds(relativeX, relativeY + coursePanel.getHeight(),
                        80, 50);
            } else {
                coursePanel = new JPanel();
                coursePanel.setLayout(null);
                JLabel noCourseLabel = new JLabel("There is no course yet. " +
                        "Please wait for a teacher to create a course.");
                noCourseLabel.setBounds(0, 10, 600, 20);
                coursePanel.add(noCourseLabel);
                coursePanel.setBounds(relativeX, relativeY,
                        600, 30);
                functionPanel.setBounds(relativeX, relativeY + coursePanel.getHeight() + 20,
                        80, 50);
            }

            functionPanel.setLayout(null);

            logoutButton.addActionListener(this);
            logoutButton.setFocusable(false);
            logoutButton.setBounds(0, 0, 80, 20);

            refreshButton.addActionListener(this);
            refreshButton.setFocusable(false);
            refreshButton.setBounds(0, 30, 80, 20);

            functionPanel.add(logoutButton);
            functionPanel.add(refreshButton);

            frame.add(header);
            frame.add(coursePanel);
            frame.add(functionPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == logoutButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to log out?", "Log Out Confirmation")) {
                Packet response = Client.getResponse(new Packet(LOGOUT, new String[]{username}));
                frame.dispose();
                if (response != null) {
                    new AccountLogin(frame.getLocation());
                }
            }
        }

        for (int i = 0; i < courseButtons.size(); i++) {
            JButton courseButton = courseButtons.get(i);
            if (e.getSource() == courseButton) {
                Packet request = new Packet(ENTER_COURSE, new String[]{courseTitles[i]});
                Packet response = Client.getResponse(request);
                if (response == null) {
                    frame.dispose();
                } else {
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new StudentForumUI(courseTitles[i], frame.getLocation());
                    } else {
                        frame.dispose();
                        WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                                "Please refresh and try again.");
                        new StudentCourseUI(frame.getLocation());
                    }
                }
            }
        }

        if (e.getSource() == refreshButton) {
            frame.dispose();
            new StudentCourseUI(frame.getLocation());
        }
    }
}
