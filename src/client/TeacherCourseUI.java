package client;

import util.Packet;

import static client.Client.username;
import static util.Packet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeacherCourseUI implements ActionListener {


    JFrame frame = new JFrame("Learning Management System");

    JButton createButton = new JButton("Create");
    JButton refreshButton = new JButton("Refresh");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons = new ArrayList<>();
    ArrayList<JButton> renameButtons = new ArrayList<>();
    ArrayList<JButton> deleteButtons = new ArrayList<>();
    String[] courseTitles;

    public TeacherCourseUI(Point location) {

        Packet request = new Packet(Packet.REQUEST_COURSE_TITLES);
        Packet response = Client.getResponse(request);

        courseTitles = response.getMsg();

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

        JPanel coursePanel = new JPanel();

        if (courseTitles.length != 0) {
            List<Integer> lengths = new ArrayList<>();
            for (String courseTitle : courseTitles) {
                lengths.add(courseTitle.length());
            }

            int courseButtonWidth = Collections.max(lengths) * 16;
            int renameButtonWidth = 85;
            int deleteButtonWidth = 80;
            int buttonGap = 20;

            coursePanel.setBounds(relativeX, relativeY + createPanel.getHeight() + 10,
                    courseButtonWidth + renameButtonWidth + deleteButtonWidth + buttonGap,
                    courseTitles.length * 30 - 10);
            coursePanel.setLayout(null);


            for (int i = 0; i < courseTitles.length; i++) {
                JButton courseButton = new JButton(courseTitles[i]);
                courseButton.addActionListener(this);
                courseButton.setFocusable(false);
                courseButton.setBounds(0, i * 30, courseButtonWidth, 20);
                courseButtons.add(courseButton);


                JButton renameButton = new JButton("Rename");
                renameButton.addActionListener(this);
                renameButton.setFocusable(false);
                renameButton.setBounds(courseButtonWidth + 20, i * 30, renameButtonWidth, 20);
                renameButtons.add(renameButton);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(this);
                deleteButton.setFocusable(false);
                deleteButton.setBounds(courseButtonWidth + renameButtonWidth + 20,
                        i * 30, deleteButtonWidth, 20);
                deleteButtons.add(deleteButton);

                coursePanel.add(courseButton);
                coursePanel.add(renameButton);
                coursePanel.add(deleteButton);
            }
        } else {

            coursePanel = new JPanel();
            coursePanel.setLayout(null);
            JLabel noCourseLabel = new JLabel("There is no course yet. " +
                    "You can create a course.");
            noCourseLabel.setBounds(0, 10, 600, 20);
            coursePanel.add(noCourseLabel);
            coursePanel.setBounds(relativeX, relativeY + createPanel.getHeight() + 10,
                    600, 30);
        }

        JPanel functionPanel = new JPanel();
        functionPanel.setBounds(relativeX, relativeY + createPanel.getHeight() + coursePanel.getHeight() + 20,
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

        frame.add(header);
        frame.add(createPanel);
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

        if (e.getSource() == refreshButton) {
            frame.dispose();
            new TeacherCourseUI(frame.getLocation());
        }

        if (e.getSource() == createButton) {

            String courseTitle;
            courseTitle = WindowGenerator.requestClientInput(frame, "Enter the course title");
            if (courseTitle != null) {
                Packet response = Client.getResponse(new Packet(CREATE_COURSE, new String[]{courseTitle}));
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherCourseUI(frame.getLocation());
                } else {

                    WindowGenerator.error(frame, "Course already exist.");
                    frame.dispose();
                    new TeacherCourseUI(frame.getLocation());
                }
            }

        }
        for (int i = 0; i < renameButtons.size(); i++) {
            JButton renameButton = renameButtons.get(i);
            if (e.getSource() == renameButton) {
                String newTitle;
                newTitle = WindowGenerator.requestClientInput(frame, "Enter the new title for "
                        + courseTitles[i]);
                if (newTitle != null) {
                    Packet request = new Packet(RENAME_COURSE, new String[]{courseTitles[i], newTitle});
                    Packet response = Client.getResponse(request);
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        WindowGenerator.showMsg(frame, "The name of the course " + courseTitles[i] + " has been changed to "
                                + newTitle + "!");
                        new TeacherCourseUI(frame.getLocation());
                    } else {
                        frame.dispose();
                        WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                                "Please refresh and try again.");
                        new TeacherCourseUI(frame.getLocation());
                    }
                }
            }
        }

        for (int i = 0; i < deleteButtons.size(); i++) {
            JButton deleteButton = deleteButtons.get(i);
            if (e.getSource() == deleteButton) {
                if (WindowGenerator.confirm(frame,
                        "Deleting the course will lose all the forums and post in this course. " +
                                "Are you sure you want to delete this course?", "Warning")) {
                    Packet request = new Packet(DELETE_COURSE, new String[]{courseTitles[i]});
                    Packet response = Client.getResponse(request);
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new TeacherCourseUI(frame.getLocation());
                    } else {
                        frame.dispose();
                        WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                                "Please refresh and try again.");
                        new TeacherCourseUI(frame.getLocation());
                    }
                }
            }
        }

        for (int i = 0; i < courseButtons.size(); i++) {
            JButton courseButton = courseButtons.get(i);
            if (e.getSource() == courseButton) {
                Packet request = new Packet(ENTER_COURSE, new String[]{courseTitles[i]});
                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherForumUI(courseTitles[i], frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, "Course doesn't exist. It may be deleted or modified other users. " +
                            "Please refresh and try again.");
                    new TeacherCourseUI(frame.getLocation());
                }
            }
        }
    }
}
