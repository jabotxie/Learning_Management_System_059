package client;

import util.DataManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Design implements ActionListener {
    JFrame frame = new JFrame("Learning Management System");

    JButton createButton = new JButton("Create");

    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons = new ArrayList<>();
    ArrayList<JButton> renameButtons = new ArrayList<>();
    ArrayList<JButton> deleteButtons = new ArrayList<>();
    String[] courseTitles = new String[]{"CS180", "CS159", "CS240", "CS123"};

    public static void main(String[] args) {
        new Design();
    }
    public Design() {
//        int relativeX = 0;
//        int relativeY = 0;
//
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

        String text = "I did a search engine where I can enter some words and find the links where this words appears. But when I display this links, I am displaying it as string, and I want to display it as hyperlinks, that the user can click and connect to the site. I used the solutions I found here on stackoverflow, but none of then worked.";

        JTextArea textArea = new JTextArea(text);

        JPanel panel = new JPanel();
        panel.add(textArea);
        frame.add(panel);

//
//        JPanel createPanel = new JPanel();
//        createPanel.setBounds(relativeX, relativeY, 80, 20);
//        createPanel.setLayout(null);
//
//        createButton.addActionListener(this);
//        createButton.setFocusable(false);
//        createButton.setBounds(0, 0, 80, 20);
//
//        createPanel.add(createButton);
//
//        JPanel coursePanel = new JPanel();
//        coursePanel.setBounds(relativeX,relativeY + createPanel.getHeight() + 10,
//                265, courseTitles.length * 30 - 10);
//        coursePanel.setLayout(null);
//
//        for (int i = 0; i < courseTitles.length; i++) {
//            JButton courseButton = new JButton(courseTitles[i]);
//            courseButton.addActionListener(this);
//            courseButton.setFocusable(false);
//            courseButton.setBounds(0, i * 30, 80, 20);
//            courseButtons.add(courseButton);
//
//
//            JButton renameButton = new JButton("Rename");
//            renameButton.addActionListener(this);
//            renameButton.setFocusable(false);
//            renameButton.setBounds(100, i * 30, 85, 20);
//            renameButtons.add(renameButton);
//
//            JButton deleteButton = new JButton("Delete");
//            deleteButton.addActionListener(this);
//            deleteButton.setFocusable(false);
//            deleteButton.setBounds(185, i * 30, 80, 20);
//            deleteButtons.add(deleteButton);
//
//            coursePanel.add(courseButton);
//            coursePanel.add(renameButton);
//            coursePanel.add(deleteButton);
//        }
//
//
//
//        JPanel logoutPanel = new JPanel();
//        logoutPanel.setBackground(Color.BLUE);
//        logoutPanel.setBounds(relativeX, relativeY + createPanel.getHeight() + coursePanel.getHeight() + 20,
//                80, 20);
//        logoutPanel.setLayout(null);
//
//        logoutButton.addActionListener(this);
//        logoutButton.setFocusable(false);
//        logoutButton.setBounds(0, 0, 80, 20);
//
//        logoutPanel.add(logoutButton);
//
//
//        frame.add(createPanel);
//        frame.add(coursePanel);
//        frame.add(logoutPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
