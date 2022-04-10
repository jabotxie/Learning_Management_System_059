import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

/**
 * UserActivities class stores a static current user
 *
 *
 */
public class UserActivities implements Runnable {
    public static User currentUser;
    public static boolean isForumRead;

    /**
     * In this thread, the user is asked to do the following things in linear order:
     * 1. Read from files to update users information, forum information (including the posts in forums)
     * 2. Ask if they to create an account or log in
     *    I. If they want to create the account, then
     *      a. let them enter a username
     *          i. if the username is taken, handle exception and enter another username
     *          ii. if the username is available, proceed program
     *      b. let the user enter the passcode
     *      c. assign the user to current user
     *    II. If the user want to log in
     *      a. let them enter username and password
     *      b. check if match with Users info
     *          i. match, assign the user to current user, proceed the program
     *          ii. not match, handle exception, return to beginning of 2.
     * 3. select from menu
     *      a.  Create forum
     *              Add a DiscussionForum instance in User.forums
     *              Then repeat 3
     *              P.S. Handle the exception if the user does not have permission
     *      b.  Edit forum
     *              Edit the given forum's topic with a user input, which is the topic of the forum
     *              Then repeat 3
     *              P.S. Handle the exception if the user does not have permission
     *      c.  Delete forum
     *              Delete the given forum
     *              Then repeat 3
     *              P.S. Handle the exception if the user does not have permission
     *              P.S. Handle the exception if the given forum does not exist
     *      d.  Choose a forum
     *              proceed to 4
     *      E.  Log out
     *              proceed to 8
     * 4. select forum
     *      a. e.g. forum one (topic)
     *          proceed to 5
     *      b. e.g. forum two
     *          proceed to 5
     *      c. e.g. forum three
     *          proceed to 5
     *      d. back to last menu
     *          back to 3
     *  5. select action (Display the topic of the forum selected)
     *      a. add post
     *          add the post, and repeat 5
     *      b. reply post
     *          proceed to 6
     *      c. back to last menu
     *          back to 4
     *      d. log out
     *  6. select action
     *      a. e.g. post one (the first 30 character...)
     *          proceed to 7
     *      b. e.g. post two
     *          same with a.
     *      c. e.g. post three
     *          same with a.
     *      d. back to last menu
     *          back to 3
     *   7. select action
     *      a. import from file
     *          let the user enter a file name, add the reply and back to 5
     *      b. enter reply
     *          let the user enter a String type reply, and add the reply, then back to 5
     *   8. Log out
     *      a. save the user info again
     *      b. save the forum (and the post in it)
     *      c. End the program
     */
    @Override
    public void run() {
        //TODO: implement user behavior
//        JFrame frame = new JFrame("Learning Management System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300,300);
//
//        // Adds Button to content pane of frame
//
//
//        JTextField userNameTF = new JTextField("Username",10);
//        JTextField passwordTF = new JTextField("password", 10);
//        JButton loginButton = new JButton("Log In");
//
//        JPanel panel = new JPanel();
//        panel.add(userNameTF);
//        panel.add(passwordTF);
//        panel.add(loginButton);
//        frame.getContentPane().add(panel);
//        frame.setVisible(true);
//
//        loginButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                String username = userNameTF.getText();
//                String password = passwordTF.getText();
//
//                System.out.println(username);
//                System.out.println(password);
//
//            }
//        });
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to the Learning Management System.\n");
//        System.out.println("Press 1 to login, Press 2 to create account.\n");
//        int loginChoice = scanner.nextInt();
//        if (loginChoice == 1)
    }

}
