package client;

import util.Packet;

import static util.Packet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TeacherCourseUI implements ActionListener {


    JFrame frame = new JFrame("Learning Management System");

    JButton createButton = new JButton("Create");
    JButton refreshButton = new JButton("Refresh");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons = new ArrayList<>();
    ArrayList<JButton> renameButtons = new ArrayList<>();
    ArrayList<JButton> deleteButtons = new ArrayList<>();
    String[] courseTitles;

    public TeacherCourseUI() {
        try {
            Packet request = new Packet(Packet.REQUEST_COURSE_TITLES);
            Packet response = Client.getResponse(request);

            courseTitles = response.getMsg();

            int relativeX = 0;
            int relativeY = 0;

            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.setVisible(true);

            JPanel createPanel = new JPanel();
            createPanel.setBounds(relativeX, relativeY, 80, 20);
            createPanel.setLayout(null);

            createButton.addActionListener(this);
            createButton.setFocusable(false);
            createButton.setBounds(0, 0, 80, 20);

            createPanel.add(createButton);

            JPanel coursePanel = new JPanel();
            coursePanel.setBounds(relativeX, relativeY + createPanel.getHeight() + 10,
                    265, courseTitles.length * 30 - 10);
            coursePanel.setLayout(null);

            for (int i = 0; i < courseTitles.length; i++) {
                JButton courseButton = new JButton(courseTitles[i]);
                courseButton.addActionListener(this);
                courseButton.setFocusable(false);
                courseButton.setBounds(0, i * 30, 80, 20);
                courseButtons.add(courseButton);


                JButton renameButton = new JButton("Rename");
                renameButton.addActionListener(this);
                renameButton.setFocusable(false);
                renameButton.setBounds(100, i * 30, 85, 20);
                renameButtons.add(renameButton);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(this);
                deleteButton.setFocusable(false);
                deleteButton.setBounds(185, i * 30, 80, 20);
                deleteButtons.add(deleteButton);

                coursePanel.add(courseButton);
                coursePanel.add(renameButton);
                coursePanel.add(deleteButton);
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

            frame.add(createPanel);
            frame.add(coursePanel);
            frame.add(functionPanel);

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == logoutButton) {
            if (WindowGenerator.confirm("Are you sure you want to log out?", "Confirmation")) {
                frame.dispose();
                new LoginUI();
            }
        }

        if (e.getSource() == refreshButton) {
            frame.dispose();
            new TeacherCourseUI();
        }

        if (e.getSource() == createButton) {

                String courseTitle = WindowGenerator.requestClientInput("Enter the course title");

                Packet response = Client.getResponse(new Packet(CREATE_COURSE, new String[]{courseTitle}));
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherCourseUI();
                } else {
                    frame.dispose();
                    WindowGenerator.error("Course already exist.");
                    new TeacherCourseUI();
                }

        }
        for (int i = 0; i < renameButtons.size(); i++) {
            JButton renameButton = renameButtons.get(i);
            if (e.getSource() == renameButton) {
                String newTitle = WindowGenerator.requestClientInput("Enter the new title for "
                        + courseTitles[i]);
                Packet request = new Packet(RENAME_COURSE, new String[]{courseTitles[i], newTitle});
                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    WindowGenerator.showMsg("The name of the course " + courseTitles[i] + " has been changed to "
                            + newTitle + "!");
                    new TeacherCourseUI();
                } else {
                    frame.dispose();
                    WindowGenerator.error("Course doesn't exist. It may be deleted or modified other users. " +
                            "Please refresh and try again.");
                    new TeacherCourseUI();
                }
            }
        }

        for (int i = 0; i < deleteButtons.size(); i++) {
            JButton deleteButton = deleteButtons.get(i);
            if (e.getSource() == deleteButton) {
                if (WindowGenerator.confirm(
                        "Deleting the course will lose all the forums and post in this course. " +
                                "Are you sure you want to delete this course?", "Warning")) {
                    Packet request = new Packet(DELETE_COURSE, new String[]{courseTitles[i]});
                    Packet response = Client.getResponse(request);
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new TeacherCourseUI();
                    } else {
                        frame.dispose();
                        WindowGenerator.error("Course doesn't exist. It may be deleted or modified other users. " +
                                "Please refresh and try again.");
                        new TeacherCourseUI();
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
                    new TeacherForumUI();
                } else {
                    frame.dispose();
                    WindowGenerator.error("Course doesn't exist. It may be deleted or modified other users. " +
                    "Please refresh and try again.");
                    new TeacherCourseUI();
                }
            }
        }
    }
}
