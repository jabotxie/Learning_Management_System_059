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
import static util.Packet.*;
import static util.Packet.ENTER_FORUM;

public class StudentForumUI implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");
    JButton refreshButton = new JButton("Refresh");
    JButton backButton = new JButton("Back");
    JButton logoutButton = new JButton("Log out");
    List<JButton> forumButtons = new ArrayList<>();

    String[] topics;

    String course;

    public StudentForumUI(String course, Point location) {
        this.course = course;
        Packet request = new Packet(REQUEST_FORUM_TOPICS, new String[]{course});
        Packet response = Client.getResponse(request);
        if (response == null) {
            frame.dispose();
        } else {
            topics = response.getMsg();

            int relativeX = 10;
            int relativeY = 10;

            frame.setSize(600, 400);
            frame.setLocation(location);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setVisible(true);

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

            JPanel topicPanel = new JPanel();
            if (topics.length != 0) {
                List<Integer> lengths = new ArrayList<>();
                for (String courseTitle : topics) {
                    lengths.add(courseTitle.length());
                }

                int topicButtonWidth = Collections.max(lengths) * 16;
                int renameButtonWidth = 85;
                int deleteButtonWidth = 80;
                int buttonGap = 20;

                topicPanel.setBounds(relativeX, relativeY + 10,
                        topicButtonWidth + renameButtonWidth + deleteButtonWidth + buttonGap,
                        topics.length * 30 - 10);
                topicPanel.setLayout(null);


                for (int i = 0; i < topics.length; i++) {
                    JButton topicButton = new JButton(topics[i]);
                    topicButton.addActionListener(this);
                    topicButton.setFocusable(false);
                    topicButton.setBounds(0, i * 30, topicButtonWidth, 20);
                    forumButtons.add(topicButton);

                    topicPanel.add(topicButton);
                }
            } else {
                topicPanel = new JPanel();
                topicPanel.setLayout(null);
                JLabel noCourseLabel = new JLabel("There is no forum in " + course + " yet. " +
                        "Please wait for a teacher to create one.");
                noCourseLabel.setBounds(0, 10, 600, 20);
                topicPanel.add(noCourseLabel);
                topicPanel.setBounds(relativeX, relativeY,
                        600, 30);
            }


            JPanel functionPanel = new JPanel();
            functionPanel.setBounds(relativeX, relativeY + topicPanel.getHeight() + 20,
                    80, 80);
            functionPanel.setLayout(null);

            logoutButton.addActionListener(this);
            logoutButton.setFocusable(false);
            logoutButton.setBounds(0, 0, 80, 20);

            refreshButton.addActionListener(this);
            refreshButton.setFocusable(false);
            refreshButton.setBounds(0, 30, 80, 20);

            backButton.addActionListener(this);
            backButton.setFocusable(false);
            backButton.setBounds(0, 60, 80, 20);

            functionPanel.add(logoutButton);
            functionPanel.add(refreshButton);
            functionPanel.add(backButton);

            frame.add(header);
            frame.add(topicPanel);
            frame.add(functionPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to log out?", "Log Out Confirmation")) {
                Packet response = Client.getResponse(new Packet(LOGOUT, new String[]{username}));
                if (response == null) {
                    frame.dispose();
                } else {
                    frame.dispose();
                    new AccountLogin(frame.getLocation());
                }
            }
        }

        if (e.getSource() == backButton) {
            frame.dispose();
            new StudentCourseUI(frame.getLocation());
        }

        if (e.getSource() == refreshButton) {
            Packet request = new Packet(ENTER_COURSE, new String[]{course});
            Packet response = Client.getResponse(request);
            if (response == null) {
                frame.dispose();
            } else {
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new StudentForumUI(course, frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                            "Please refresh and try again.");
                    new StudentCourseUI(frame.getLocation());
                }
            }
        }

        for (int i = 0; i < forumButtons.size(); i++) {
            JButton courseButton = forumButtons.get(i);
            if (e.getSource() == courseButton) {
                Packet request = new Packet(ENTER_FORUM, new String[]{course, topics[i]});
                Packet response = Client.getResponse(request);
                if (response == null) {
                    frame.dispose();
                } else {
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new StudentPostUI(course, topics[i], frame.getLocation());
                    } else {
                        frame.dispose();
                        WindowGenerator.error(frame, "Forum doesn't exist. It may be deleted or modified other users. " +
                                "Please refresh and try again.");
                        new StudentForumUI(course, frame.getLocation());
                    }
                }
            }
        }
    }
}
