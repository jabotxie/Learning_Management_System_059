package client;

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import util.Packet;

import static client.Client.getResponse;
import static client.Client.username;
import static util.Packet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentPostUI implements ActionListener {
    JFrame frame = new JFrame("Learning Management System");

    JButton courseListButton;
    JButton courseButton;
    JButton createButton = new JButton("Create");
    JButton refreshButton = new JButton("Refresh");
    JButton logoutButton = new JButton("Log out");


    List<JButton> addReplyButtons = new ArrayList<>();
    List<JButton> voteButtons = new ArrayList<>();
    List<String[]> postsDisplay = new ArrayList<>();
    List<String[]> repliesDisplay = new ArrayList<>();

    String course;
    String topic;

    StudentPostUI(String course, String topic, Point location) {
        this.course = course;
        this.topic = topic;

        Packet request = new Packet(Packet.REQUEST_POST_LIST, new String[]{course, topic});
        Packet response = Client.getResponse(request);
        if (response == null) {
            frame.dispose();
        } else {
            if (!response.isOperationSuccess()) {
                WindowGenerator.error(frame, response.getMsg()[1]);
                String failureType = response.getMsg()[0];
                if (failureType.equals("Course")) {
                    new StudentCourseUI(frame.getLocation());
                } else {
                    new StudentForumUI(course, frame.getLocation());
                }

            } else {
                postsDisplay = response.getPosts();
                repliesDisplay = response.getReplies();


                frame.setLocation(location);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setLayout(null);
                frame.setResizable(true);

                // Header Panel

                JLabel userType = new JLabel("User Type: Student");
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
                header.setBounds(10, 10, 1000, 80);
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
                if (postsDisplay.size() == 0) {
                    JLabel noPostLabel = new JLabel("There is no post yet. Be the first one creating a post in this forum!");
                    noPostLabel.setBounds(xPos, yPos + 10, 600, 20);
                    postPanel.add(noPostLabel);
                }
                for (int i = 0; i < postsDisplay.size(); i++) {
                    JButton addReplyButton = new JButton(("Add an reply"));
                    JButton voteButton = new JButton("Vote for this post");

                    addReplyButtons.add(addReplyButton);
                    voteButtons.add(voteButton);

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

                    JLabel voteNumLabel = new JLabel("Votes: " + voteNum);
                    voteNumLabel.setBounds(xPos + 80, yPos + 10, 200, 20);

                    Date date = new Date(Long.parseLong(postDate));
                    JLabel timeLabel = new JLabel("Post Time: " + date);
                    timeLabel.setBounds(xPos + 200, yPos + 10, 500, 20);

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


                    addReplyButton.addActionListener(this);
                    addReplyButton.setFocusable(false);
                    addReplyButton.setBounds(xPos, yPos + 50 + postContentTextArea.getHeight(), 120, 20);

                    voteButton.addActionListener(this);
                    voteButton.setFocusable(false);
                    voteButton.setBounds(xPos + 120, yPos + 50 + postContentTextArea.getHeight(), 150, 20);

                    addComponent(postPanel, postLabel, voteNumLabel, timeLabel, posterLabel, postContentTextArea, addReplyButton, voteButton);

                    int xReplyPos = 100;
                    int yReplyPos = yPos + 50 + postContentTextArea.getHeight() + 20;

                    String[] replyDisplay = repliesDisplay.get(i);
                    if (replyDisplay != null) {
                        for (int j = 0; j < replyDisplay.length; j += 3) {

                            String replier = replyDisplay[j];
                            String replyTime = replyDisplay[j + 1];
                            String replyContent = replyDisplay[j + 2];
                            JLabel replyLabel = new JLabel("reply" + (j / 3 + 1));
                            replyLabel.setBounds(xReplyPos, yReplyPos, 5 * 16 + ((j + 1) / 10) * 16, 20);


                            Date replyDate = new Date(Long.parseLong(replyTime));
                            JLabel replyTimeLabel = new JLabel("Reply Time Time: " + replyDate);
                            replyTimeLabel.setBounds(xReplyPos + 200, yReplyPos, 300, 20);

                            JLabel replierLabel = new JLabel(replier);
                            replierLabel.setBounds(xReplyPos, yReplyPos + 20, 800, 20);

                            int replyHeight = (replyContent.length() / 140) * 20 + 20;
                            JTextArea replyTextArea = new JTextArea();
                            replyTextArea.setText(replyContent);
                            replyTextArea.setLineWrap(true);
                            replyTextArea.setWrapStyleWord(true);
                            replyTextArea.setEditable(false);
                            replyTextArea.setBounds(xReplyPos, yReplyPos + 40, 800, replyHeight);

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

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseListButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to return to course selection page?", "Confirmation")) {
                frame.dispose();
                new StudentCourseUI(frame.getLocation());
            }
        }

        if (e.getSource() == courseButton) {
            if (WindowGenerator.confirm(frame,
                    "Are you sure you want to return to course page of " + course, "Confirmation")) {
                frame.dispose();
                new StudentForumUI(course, frame.getLocation());
            }
        }

        if (e.getSource() == refreshButton) {
            frame.dispose();
            new StudentPostUI(course, topic, frame.getLocation());
        }

        if (e.getSource() == logoutButton) {
            if (WindowGenerator.confirm(frame, "Are you sure you want to log out?",
                    "Log Out Confirmation")) {
                frame.dispose();
                Packet response = getResponse(new Packet(LOGOUT, new String[]{username}));
                if (response == null) {
                    frame.dispose();
                } else {
                    new AccountLogin(frame.getLocation());
                }
            }
        }

        if (e.getSource() == createButton) {

            String postContent;
            postContent = WindowGenerator.requestClientInput(frame, "Please enter the post content");
            if (postContent != null) {
                String[] msg = new String[]{course, topic, "S", username, postContent};
                Packet request = new Packet(CREATE_POST, msg);
                Packet response = Client.getResponse(request);
                if (response == null) {
                    frame.dispose();
                } else {
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new StudentPostUI(course, topic, frame.getLocation());
                    } else {
                        if (response.getMsg()[0].equals("Course")) {
                            WindowGenerator.error(frame, "Course doesn't exist. " +
                                    "It may be modified or deleted by other users. " +
                                    "You will be directed to course selection page.");
                            frame.dispose();
                            new StudentCourseUI(frame.getLocation());
                        } else {
                            WindowGenerator.error(frame, "Forum doesn't exist. " +
                                    "It may be modified or deleted by other users. " +
                                    "You will be directed to forum selection page.");
                            frame.dispose();
                            new StudentForumUI(course, frame.getLocation());
                        }
                    }
                }
            }
        }

        for (int i = 0; i < addReplyButtons.size(); i++) {
            JButton addReplyButton = addReplyButtons.get(i);
            if (e.getSource() == addReplyButton) {
                String replyContent;
                replyContent = WindowGenerator.requestClientInput(frame, "Enter the reply");

                if (replyContent != null) {
                    Packet request = new Packet(REPLY_POST, new String[]{course, topic, postsDisplay.get(i)[0].substring(8),
                            postsDisplay.get(i)[1], "S", username, replyContent});
                    Packet response = Client.getResponse(request);
                    if (response == null) {
                        frame.dispose();
                    } else {
                        if (response.isOperationSuccess()) {
                            frame.dispose();
                            new StudentPostUI(course, topic, frame.getLocation());
                        } else {
                            frame.dispose();
                            WindowGenerator.error(frame, response.getMsg()[1]);
                            if (response.getMsg()[0].equals("Course")) new StudentCourseUI(frame.getLocation());
                            else if (response.getMsg()[0].equals("Forum"))
                                new StudentForumUI(course, frame.getLocation());
                            else new StudentPostUI(course, topic, frame.getLocation());
                        }
                    }
                }
            }
        }

        for (int i = 0; i < voteButtons.size(); i++) {
            JButton voteButton = voteButtons.get(i);
            if (e.getSource() == voteButton) {
                Packet request = new Packet(VOTE_POST, new String[]{course, topic, postsDisplay.get(i)[0].substring(8),
                        postsDisplay.get(i)[1], username});
                Packet response = Client.getResponse(request);
                if (response == null) {
                    frame.dispose();
                } else {
                    if (response.isOperationSuccess()) {
                        frame.dispose();
                        new StudentPostUI(course, topic, frame.getLocation());
                    } else {

                        WindowGenerator.error(frame, response.getMsg()[1]);
                        if (response.getMsg()[0].equals("Course")) {
                            new StudentCourseUI(frame.getLocation());
                            frame.dispose();
                        } else if (response.getMsg()[0].equals("Forum")) {
                            new StudentForumUI(course, frame.getLocation());
                            frame.dispose();
                        }
                    }
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
