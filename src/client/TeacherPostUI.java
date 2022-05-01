package client;

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import util.Packet;

import static client.Client.username;
import static util.Packet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TeacherPostUI implements ActionListener {
    JFrame frame = new JFrame("Learning Management System");

    JButton courseListButton;
    JButton courseButton;
    JButton createButton = new JButton("Create");
    JButton refreshButton = new JButton("Refresh");
    JButton logoutButton = new JButton("Log out");


    List<JButton> editButtons = new ArrayList<>();
    List<JButton> deleteButtons = new ArrayList<>();
    List<JButton> addReplyButtons = new ArrayList<>();
    List<String[]> postsDisplay = new ArrayList<>();
    List<String[]> repliesDisplay = new ArrayList<>();
    JButton addReplyButton = new JButton("Add a reply");

    String course;
    String topic;

    TeacherPostUI(String course, String topic, Point location) {
        this.course = course;
        this.topic = topic;

        Packet request = new Packet(Packet.REQUEST_POST_LIST, new String[]{course, topic});
        Packet response = Client.getResponse(request);

        if (!response.isOperationSuccess()) {
            WindowGenerator.error(frame, response.getMsg()[1]);
            String failureType = response.getMsg()[0];
            if (failureType.equals("Course")) {
                new TeacherCourseUI(frame.getLocation());
            } else {
                new TeacherForumUI(course, location);
            }

        } else {
            postsDisplay = response.getPosts();
            repliesDisplay = response.getReplies();


            frame.setLocation(location);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setResizable(true);

            // Header Panel

            JLabel userType = new JLabel("User Type: Teacher");
            userType.setBounds(800, 0, 200, 20);

            JLabel username = new JLabel("Username: " + Client.username);
            username.setBounds(800, 20, 100 + Client.username.length() * 16, 20);

            logoutButton.addActionListener(this);
            logoutButton.setFocusable(false);
            logoutButton.setBounds(800, 40, 80, 20);

            courseListButton = new JButton("Course List");
            courseListButton.setBounds(0, 0, 100, 20);
            courseListButton.setFocusable(false);
            courseListButton.addActionListener(this);

            JLabel pointerLabel = new JLabel("  >>");
            pointerLabel.setBounds(100, 0, 40, 20);

            courseButton = new JButton(course);
            courseButton.setBounds(120, 0, course.length() * 16, 20);
            courseButton.setFocusable(false);
            courseButton.addActionListener(this);

            JLabel pointerLabelOne = new JLabel(" >>");
            pointerLabelOne.setBounds(120 + courseButton.getWidth(), 0, 40, 20);

            JLabel topicLabel = new JLabel(topic);
            topicLabel.setBounds(140 + courseButton.getWidth(), 0, topic.length() * 16, 20);

            refreshButton.setBounds(0, 20, 90, 20);
            refreshButton.addActionListener(this);
            refreshButton.setFocusable(false);

            createButton.setBounds(0, 60, 80, 20);
            createButton.addActionListener(this);
            createButton.setFocusable(false);

            JPanel header = new JPanel();
            header.setBounds(0, 0, 1000, 80);
            header.setLayout(null);


            header.add(userType);
            header.add(username);
            header.add(courseListButton);
            header.add(pointerLabel);
            header.add(courseButton);
            header.add(pointerLabelOne);
            header.add(topicLabel);
            header.add(createButton);
            header.add(refreshButton);
            header.add(logoutButton);
            frame.add(header);


            int xPos = 50;
            int yPos = 0;


            JPanel postPanel = new JPanel();
            postPanel.setLayout(null);
            postPanel.setBounds(0, 0, 1000, 600);
            postPanel.setBackground(Color.PINK);
            for (int i = 0; i < postsDisplay.size(); i++) {
                JButton editButton = new JButton("Edit");
                JButton deleteButton = new JButton("Delete");
                JButton addReplyButton = new JButton(("Add an reply"));
                editButtons.add(editButton);
                deleteButtons.add(deleteButton);
                addReplyButtons.add(addReplyButton);

                String[] postDisplay = postsDisplay.get(i);
                String poster = postDisplay[0];
                String postDate = postDisplay[1];
                int voteNum;
                try {
                    voteNum = Integer.parseInt(postDisplay[2]);
                } catch (NumberFormatException e) {
                    voteNum = 0;
                }
                String postContent = postDisplay[3];
                int areaHeight = (postContent.length() / 140) * 17 + 17;
                //start creating a single post

                JLabel postLabel = new JLabel("Post" + (i + 1));
                postLabel.setBounds(xPos, yPos + 10, 4 * 16 + ((i + 1) / 10) * 16, 20);

                Date date = new Date(Long.parseLong(postDate));
                JLabel timeLabel = new JLabel("Post Time: " + date);
                timeLabel.setBounds(xPos + 120, yPos + 10, 500, 20);

                JLabel posterLabel = new JLabel(poster);
                posterLabel.setBounds(xPos, yPos + 30, 900, 20);

                JTextArea postContentTextArea = new JTextArea(postContent);
//                postContentTextArea.setBackground(new Color(238, 238, 238));
                postContentTextArea.setText(postContent);
                postContentTextArea.setLineWrap(true);
                postContentTextArea.setWrapStyleWord(true);
                postContentTextArea.setEditable(false);

                postContentTextArea.setBounds(xPos, yPos + 50, 850, areaHeight);
                postContentTextArea.setLineWrap(true);
                postContentTextArea.setColumns(65);
                postContentTextArea.setRows(postContent.length() / 100);
//                postContentTextArea.setFont(new Font("Courier", Font.BOLD, 15));

                editButton.addActionListener(this);
                editButton.setFocusable(false);
                editButton.setBounds(xPos, yPos + 50 + postContentTextArea.getHeight(), 60, 20);

                deleteButton.addActionListener(this);
                deleteButton.setFocusable(false);
                deleteButton.setBounds(xPos + 60, yPos + 50 + postContentTextArea.getHeight(), 80, 20);

                addReplyButton.addActionListener(this);
                addReplyButton.setFocusable(false);
                addReplyButton.setBounds(xPos + 140, yPos + 50 + postContentTextArea.getHeight(), 120, 20);

                addComponent(postPanel, postLabel, timeLabel, posterLabel, postContentTextArea, editButton, deleteButton, addReplyButton);

                int xReplyPos = 100;
                int yReplyPos = yPos + 50 + postContentTextArea.getHeight() + 20;

                String[] replyDisplay = repliesDisplay.get(i);
                if (replyDisplay != null) {
                    for (int j = 0; j < replyDisplay.length; j += 3) {

                        String replier = replyDisplay[j];
                        String replyTime = replyDisplay[j + 1];
                        String replyContent = replyDisplay[j + 2];
                        JLabel replyLabel = new JLabel("reply" + (j/3 + 1));
                        replyLabel.setBounds(xReplyPos, yReplyPos, 5 * 16 + ((j + 1) / 10) * 16, 20);


                        Date replyDate = new Date(Long.parseLong(replyTime));
                        JLabel replyTimeLabel = new JLabel("Reply Time Time: " + replyDate);
                        replyTimeLabel.setBounds(xReplyPos + 200, yReplyPos, 300, 20);

                        JLabel replierLabel = new JLabel(replier);
                        replierLabel.setBounds(xReplyPos, yReplyPos + 20, 800, 20);

                        JTextArea replyTextArea = new JTextArea();
                        replyTextArea.setText(replyContent);
                        replyTextArea.setLineWrap(true);
                        replyTextArea.setWrapStyleWord(true);
                        replyTextArea.setEditable(false);
                        replyTextArea.setBounds(xReplyPos, yReplyPos + 40, 800, 20);

                        yReplyPos += 40 + replyTextArea.getHeight();

                        addComponent(postPanel, replyLabel, replyTimeLabel, replierLabel, replyTextArea);
                    }
                }


                yPos = yReplyPos;
            }


            postPanel.setBounds(0, header.getHeight(), 1000, 600);
            frame.add(postPanel);
            frame.setSize(1000, 700);
            frame.setVisible(true);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseListButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to return to course selection page?", "Confirmation")) {
                frame.dispose();
                new TeacherCourseUI(frame.getLocation());
            }
        }

        if (e.getSource() == courseButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to return to course page of " + course, "Confirmation")) {
                frame.dispose();
                new TeacherForumUI(course, frame.getLocation());
            }
        }

        if (e.getSource() == logoutButton) {
            if (WindowGenerator.confirm(frame, "Are you sure you want to log out?",
                    "Log Out Confirmation")) {
                frame.dispose();
                new AccountLogin(frame.getLocation());
            }
        }

        if (e.getSource() == createButton) {
            String postContent = WindowGenerator.requestClientInput(frame, "Please enter the post content");
            String[] msg = new String[]{course, topic, "T", username, postContent};
            Packet request = new Packet(CREATE_POST, msg);
            Packet response = Client.getResponse(request);
            if (response.isOperationSuccess()) {
                frame.dispose();
                new TeacherPostUI(course, topic, frame.getLocation());
            } else {
                if (response.getMsg()[0].equals("Course")) {
                    WindowGenerator.error(frame, "Course doesn't exist. " +
                            "It may be modified or deleted by other users. " +
                            "You will be directed to course selection page.");
                    frame.dispose();
                    new TeacherCourseUI(frame.getLocation());
                } else {
                    WindowGenerator.error(frame, "Forum doesn't exist. " +
                            "It may be modified or deleted by other users. " +
                            "You will be directed to forum selection page.");
                    frame.dispose();
                    new TeacherForumUI(course, frame.getLocation());
                }
            }
        }

        for (int i = 0; i < deleteButtons.size(); i++) {
            JButton forumButton = deleteButtons.get(i);
            if (e.getSource() == forumButton) {
                Packet request = new Packet(DELETE_POST, new String[]{course, topic, username, postsDisplay.get(i)[1]});
                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherPostUI(course, topic, frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, response.getMsg()[1]);
                    new TeacherForumUI(course, frame.getLocation());
                    if (response.getMsg()[0].equals("Course")) new TeacherCourseUI(frame.getLocation());
                    else new TeacherForumUI(course, frame.getLocation());
                }
            }
        }

        for (int i = 0; i < addReplyButtons.size(); i++) {
            JButton addReplyButton = addReplyButtons.get(i);
            if (e.getSource() == addReplyButton) {
                String replyContent = WindowGenerator.requestClientInput(frame, "Enter the reply");
                Packet request = new Packet(REPLY_POST, new String[]{course, topic, postsDisplay.get(i)[0].substring(8),
                        postsDisplay.get(i)[1], postsDisplay.get(i)[0].charAt(0) == 'T' ? "T" : "S", username, replyContent});
                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherPostUI(course, topic, frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, response.getMsg()[1]);
                    new TeacherForumUI(course, frame.getLocation());
                    if (response.getMsg()[0].equals("Course")) new TeacherCourseUI(frame.getLocation());
                    else if (response.getMsg()[0].equals("Forum")) new TeacherForumUI(course, frame.getLocation());
                    else new TeacherPostUI(course, topic, frame.getLocation());
                }
            }
        }

        for (int i = 0; i < editButtons.size(); i++) {
            JButton editButton = editButtons.get(i);
            if (e.getSource() == editButton) {
                String newContent = WindowGenerator.requestClientInput(frame, "Enter the new post");
                Packet request = new Packet(EDIT_POST, new String[]{course, topic, postsDisplay.get(i)[0].substring(8),
                        postsDisplay.get(i)[1], newContent});
                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    frame.dispose();
                    new TeacherPostUI(course, topic, frame.getLocation());
                } else {
                    frame.dispose();
                    WindowGenerator.error(frame, response.getMsg()[1]);
                    new TeacherForumUI(course, frame.getLocation());
                    if (response.getMsg()[0].equals("Course")) new TeacherCourseUI(frame.getLocation());
                    else if (response.getMsg()[0].equals("Forum")) new TeacherForumUI(course, frame.getLocation());
                    else new TeacherPostUI(course, topic, frame.getLocation());
                }
            }
        }

    }

    private void addComponent(JPanel panel, JComponent... components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

}
