//package data;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class Client extends JComponent implements Runnable {
//    User currentUser;
//
//    JFrame frame;
//    JButton loginButton;
//    JButton createButton;
//    JLabel usernameLabel;
//    JLabel passwordLabel;
//    JTextField usernameText;
//    JTextField passwordText;
//    Client client;
//
//    ActionListener actionListener = new AbstractAction() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (e.getSource() == loginButton) {
//                String password = passwordText.getText();
//                String username = usernameText.getText();
//                try {
//                    currentUser = DataServer.login(username, password);
//
//                } catch (AccountInfoNotMatchException aE) {
//                    aE.printStackTrace();
//                }
//
//            }
//
//            if (e.getSource() == createButton) {
//
//            }
//        }
//    };
//
//    /**
//     *
//     * @return
//     * 1: login successfully
//     * -1: login again
//     * -2: quit
//     */
//    private int login() {
//
//        JFrame frame = new JFrame("Login");
//
//        Container content = frame.getContentPane();
//
//        content.setLayout(new BorderLayout());
//        client = new Client();
//        content.add(client);
//
//        frame.setSize(600, 400);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//
//        loginButton = new JButton("Login");
//        createButton = new JButton("Create");
//        usernameLabel = new JLabel("Username");
//        passwordLabel = new JLabel("Password");
//        usernameText = new JTextField(10);
//        passwordText = new JTextField(10);
//
//        JPanel loginPanel = new JPanel();
//        loginPanel.add(usernameLabel);
//        loginPanel.add(usernameText);
//        loginPanel.add(passwordLabel);
//        loginPanel.add(passwordText);
//        loginPanel.add(loginButton);
//
//
//
//
//        loginButton.addActionListener(actionListener);
//        createButton.addActionListener(actionListener);
//
//        content.add(loginPanel, BorderLayout.NORTH);
//
//
////        clrButton = new JButton("Clear");
////        clrButton.addActionListener(actionListener);
////        fillButton = new JButton("Fill");
////        fillButton.addActionListener(actionListener);
////        eraseButton = new JButton("Erase");
////        eraseButton.addActionListener(actionListener);
////        randomButton = new JButton("Random");
////        randomButton.addActionListener(actionListener);
////
////
////        JPanel panelTop = new JPanel();
////
////
////        panelTop.add(clrButton);
////        panelTop.add(fillButton);
////        panelTop.add(eraseButton);
////        panelTop.add(randomButton);
////        content.add(panelTop, BorderLayout.NORTH);
//        frame.setVisible(true);
//        return 1;
//    }
//
//    private int course(Client client) {
//        return 1;
//    }
//
//    private int forum(Course course) {
//        return 1;
//    }
//
//    private int post(DiscussionForum forum) {
//        return 1;
//    }
//
//    public static void main(String[] args) {
//        DataServer.initData();
//        SwingUtilities.invokeLater(new Client());
//    }
//
//    @Override
//    public void run() {
//        login();
//
//
////        int loginReturn;
////        do {
////            loginReturn = login();
////            if (loginReturn == 1) {
////                int courseReturn;
////                do {
////                    courseReturn = course(this);
////                } while (courseReturn != -1);
////            }
////        } while (loginReturn != -1);
//
//
////        int loginReturn;
////        do {
////            SwingUtilities.invokeLater(new LoginUI());
////            loginReturn = login();
////            if (loginReturn == 1) {//login successfully
////                ArrayList<Course> courses = DataServer.getCourses();
////                int courseReturn;
////                do {
////                    courseReturn = course();
////                    if (courseReturn > 0) {
////                        Course course = courses.get(courseReturn - 1);
////                        int forumReturn;
////                        do {
////                            forumReturn = forum(course);
////                            if (forumReturn > 0) {
////                                DiscussionForum forum = course.forums.get(forumReturn - 1);
////                            }
////                        } while (forumReturn != -1);
////                    }
////                } while (courseReturn != -1);
////            }
////        } while (loginReturn != -2);
//
//    }
//}
