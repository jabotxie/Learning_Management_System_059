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

public class TeacherCourseUI implements ActionListener {


    JFrame frame = new JFrame("Learning Management System");

    JButton createButton = new JButton("create");

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


            JPanel logoutPanel = new JPanel();
            logoutPanel.setBackground(Color.BLUE);
            logoutPanel.setBounds(relativeX, relativeY + createPanel.getHeight() + coursePanel.getHeight() + 20,
                    80, 20);
            logoutPanel.setLayout(null);

            logoutButton.addActionListener(this);
            logoutButton.setFocusable(false);
            logoutButton.setBounds(0, 0, 80, 20);

            logoutPanel.add(logoutButton);


            frame.add(createPanel);
            frame.add(coursePanel);
            frame.add(logoutPanel);

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

        if (e.getSource() == createButton) {

                String courseTitle = WindowGenerator.requestClientInput("Enter the course title");

                Packet response = Client.getResponse(new Packet(CREATE_COURSE, new String[]{courseTitle}));
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherCourseUI();
                } else {
                    WindowGenerator.error("Course already exist.");
                }

        }
        for (int i = 0; i < renameButtons.size(); i++) {
            JButton renameButton = renameButtons.get(i);
            if (e.getSource() == renameButton) {
                String newTitle = WindowGenerator.requestClientInput("Enter the new title for "
                        + courseTitles[i]);
                Packet request = new Packet(RENAME_COURSE, new String[]{courseTitles[i], newTitle});
                Packet response = Client.getResponse(request);
                //TODO
            }
        }

    }
}
