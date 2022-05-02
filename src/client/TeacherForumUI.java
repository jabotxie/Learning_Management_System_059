package client;

import server.SystemServer;
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

public class TeacherForumUI implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");

    JButton createButton = new JButton("Create");
    JButton refreshButton = new JButton("Refresh");
    JButton backButton = new JButton("Back");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> forumButtons = new ArrayList<>();
    ArrayList<JButton> editButtons = new ArrayList<>();
    ArrayList<JButton> deleteButtons = new ArrayList<>();
    String[] topics;

    String course;

    TeacherForumUI(String course, Point location) {
        this.course = course;
        Packet request = new Packet(Packet.REQUEST_FORUM_TOPICS, new String[]{course});
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
            JLabel userType = new JLabel("User Type: Teacher");
            userType.setBounds(0, 0, 200, 20);

            JLabel username = new JLabel("Username: " + Client.username);
            username.setBounds(0, 20, 100 + Client.username.length() * 16, 20);

            header.add(userType);
            header.add(username);
            header.setBounds(relativeX, relativeY, 600, 40);

            relativeY += 40;

            JPanel createPanel = new JPanel();
            createPanel.setBounds(relativeX, relativeY, 80, 20);
            createPanel.setLayout(null);

            createButton.addActionListener(this);
            createButton.setFocusable(false);
            createButton.setBounds(0, 0, 80, 20);

            createPanel.add(createButton);

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

                topicPanel.setBounds(relativeX, relativeY + createPanel.getHeight() + 10,
                        topicButtonWidth + renameButtonWidth + deleteButtonWidth + buttonGap,
                        topics.length * 30 - 10);
                topicPanel.setLayout(null);


                for (int i = 0; i < topics.length; i++) {
                    JButton topicButton = new JButton(topics[i]);
                    topicButton.addActionListener(this);
                    topicButton.setFocusable(false);
                    topicButton.setBounds(0, i * 30, topicButtonWidth, 20);
                    forumButtons.add(topicButton);


                    JButton renameButton = new JButton("Rename");
                    renameButton.addActionListener(this);
                    renameButton.setFocusable(false);
                    renameButton.setBounds(topicButtonWidth + 20, i * 30, renameButtonWidth, 20);
                    editButtons.add(renameButton);

                    JButton deleteButton = new JButton("Delete");
                    deleteButton.addActionListener(this);
                    deleteButton.setFocusable(false);
                    deleteButton.setBounds(topicButtonWidth + renameButtonWidth + 20,
                            i * 30, deleteButtonWidth, 20);
                    deleteButtons.add(deleteButton);

                    topicPanel.add(topicButton);
                    topicPanel.add(renameButton);
                    topicPanel.add(deleteButton);
                }
            } else {
                topicPanel = new JPanel();
                topicPanel.setLayout(null);
                JLabel noCourseLabel = new JLabel("There is no forum in " + course + " yet. " +
                        "You can create one.");
                noCourseLabel.setBounds(0, 10, 600, 20);
                topicPanel.add(noCourseLabel);
                topicPanel.setBounds(relativeX, relativeY + createPanel.getHeight(),
                        600, 30);
            }


            JPanel functionPanel = new JPanel();
            functionPanel.setBounds(relativeX, relativeY + createPanel.getHeight() + topicPanel.getHeight() + 20,
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
            frame.add(createPanel);
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
                frame.dispose();
                if (response != null) {
                    new AccountLogin(frame.getLocation());
                }
            }
        }

        if (e.getSource() == backButton) {
            frame.dispose();
            new TeacherCourseUI(frame.getLocation());
        }

        if (e.getSource() == refreshButton) {
            Packet request = new Packet(ENTER_COURSE, new String[]{course});
            Packet response = Client.getResponse(request);
            if (response == null) {
                frame.dispose();
            } else {
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherForumUI(course, frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                            "Please refresh and try again.");
                    new TeacherCourseUI(frame.getLocation());
                }
            }
        }

        if (e.getSource() == createButton) {

            String topic;
            topic = WindowGenerator.requestClientInput(frame, "Enter the forum topic");
            if (topic != null) {
                Packet response = Client.getResponse(new Packet(CREATE_FORUM, new String[]{course, topic}));
                if (response == null) {
                    frame.dispose();
                } else {
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new TeacherForumUI(course, frame.getLocation());
                    } else {
                        if (response.getMsg()[0].equals("Course")) {
                            frame.dispose();
                            WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                                    "Please refresh and try again.");
                            new TeacherCourseUI(frame.getLocation());
                        } else {
                            frame.dispose();
                            WindowGenerator.error(frame, "Forum already exists.");
                            new TeacherForumUI(course, frame.getLocation());
                        }
                    }
                }
            }
        }
        for (int i = 0; i < editButtons.size(); i++) {
            JButton renameButton = editButtons.get(i);
            if (e.getSource() == renameButton) {
                String newTitle;
                newTitle = WindowGenerator.requestClientInput(frame, "Enter the new topic for forum "
                        + topics[i]);
                if (newTitle != null) {
                    Packet request = new Packet(EDIT_TOPIC, new String[]{course, topics[i], newTitle});
                    Packet response = Client.getResponse(request);
                    if (response == null) {
                        frame.dispose();
                    } else {
                        if (response.isOperationSuccess()) {
                            frame.dispose();
                            WindowGenerator.showMsg(frame, "The topic of the forum " + topics[i] + " has been changed to "
                                    + newTitle + "!");
                            new TeacherForumUI(course, frame.getLocation());
                        } else {
                            if (response.getMsg()[0].equals("Course")) {
                                frame.dispose();
                                WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                                        "Please refresh and try again.");
                                new TeacherCourseUI(frame.getLocation());
                            } else if (response.getMsg()[0].equals("Forum")) {
                                frame.dispose();
                                WindowGenerator.error(frame, "Forum doesn't exists. " +
                                        "It may be deleted or modified other users. Please refresh and try again.");
                                new TeacherForumUI(course, frame.getLocation());
                            } else {
                                frame.dispose();
                                WindowGenerator.error(frame, "Forum already exists. Please refresh and try again.");
                                new TeacherForumUI(course, frame.getLocation());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < deleteButtons.size(); i++) {
            JButton deleteButton = deleteButtons.get(i);
            if (e.getSource() == deleteButton) {
                if (WindowGenerator.confirm(frame,
                        "Deleting the forum will lose all the posts under this forum. " +
                                "Are you sure you want to delete this forum?", "Warning")) {
                    Packet request = new Packet(DELETE_FORUM, new String[]{course, topics[i]});
                    Packet response = Client.getResponse(request);
                    if (response == null) {
                        frame.dispose();
                    } else {
                        if (response.isOperationSuccess() || response.getMsg()[0].equals("Forum")) {
                            frame.dispose();
                            new TeacherForumUI(course, frame.getLocation());
                        } else {
                            frame.dispose();
                            WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                                    "Please enter another topic and try again.");
                            new TeacherCourseUI(frame.getLocation());

                        }
                    }
                }
            }
        }

        for (int i = 0; i < forumButtons.size(); i++) {
            JButton forumButton = forumButtons.get(i);
            if (e.getSource() == forumButton) {
                Packet request = new Packet(ENTER_FORUM, new String[]{course, topics[i]});
                Packet response = Client.getResponse(request);
                if (response == null) {
                    frame.dispose();
                } else {
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new TeacherPostUI(course, topics[i], false, frame.getLocation());
                    } else {
                        frame.dispose();
                        WindowGenerator.error(frame, "Forum doesn't exist. It may be deleted or modified other users. " +
                                "Please refresh and try again.");
                        new TeacherForumUI(course, frame.getLocation());
                    }
                }
            }
        }
    }
}
