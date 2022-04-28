package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeacherPostUI implements ActionListener {
    JFrame frame = new JFrame("Learning Management System");

    JButton createButton = new JButton("Create");
    JButton refreshButton = new JButton("Refresh");
    JButton backButton = new JButton("Back");
    JButton logoutButton = new JButton("Log out");
    List<JButton> forumButtons = new ArrayList<>();
    List<JButton> editButtons = new ArrayList<>();
    List<JButton> deleteButtons = new ArrayList<>();
    String[] posts;

    String course;
    String topic;

    TeacherPostUI(String course, String topic, Point location) {
        this.course = course;
        this.topic = topic;

        Packet request = new Packet(Packet.REQUEST_POST_LIST, new String[]{course, topic});
        Packet response = Client.getResponse(request);

        posts = response.getMsg();

        int relativeX = 0;
        int relativeY = 0;

        frame.setSize(600, 400);
        frame.setLocation(location);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

        JPanel createPanel = new JPanel();
        createPanel.setBounds(relativeX, relativeY, 80, 20);
        createPanel.setLayout(null);

        createButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.setBounds(0, 0, 80, 20);

        createPanel.add(createButton);

        JPanel topicPanel = new JPanel();
        if (posts.length != 0) {
            List<Integer> lengths = new ArrayList<>();
            for (String courseTitle : posts) {
                lengths.add(courseTitle.length());
            }

            int topicButtonWidth = Collections.max(lengths) * 16;
            int renameButtonWidth = 85;
            int deleteButtonWidth = 80;
            int buttonGap = 20;

            topicPanel.setBounds(relativeX, relativeY + createPanel.getHeight() + 10,
                    topicButtonWidth + renameButtonWidth + deleteButtonWidth + buttonGap,
                    posts.length * 30 - 10);
            topicPanel.setLayout(null);


            for (int i = 0; i < posts.length; i++) {
                JButton topicButton = new JButton(posts[i]);
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

        frame.add(createPanel);
        frame.add(topicPanel);
        frame.add(functionPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
