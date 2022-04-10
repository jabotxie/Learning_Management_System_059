import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * UserActivities class stores a static current user
 */
public class UserActivities implements Runnable {
    private static Scanner scanner;

    public static User currentUser;
    private static boolean isDataLoaded = false;

    public static void main(String[] args) {
        DataManager.initData();
        scanner = new Scanner(System.in);
        Thread user1 = new Thread(new UserActivities());
        user1.start();
    }

    /**
     * In this thread, the user is asked to do the following things in linear order:
     * 1. Read from files to update users information, forum information (including the posts in forums)
     * 2. Ask if they to create an account or log in
     * I. If they want to create the account, then
     * a. let them enter a username
     * i. if the username is taken, handle exception and enter another username
     * ii. if the username is available, proceed program
     * b. let the user enter the passcode
     * c. assign the user to current user
     * II. If the user want to log in
     * a. let them enter username and password
     * b. check if match with Users info
     * i. match, assign the user to current user, proceed the program
     * ii. not match, handle exception, return to beginning of 2.
     * 3. select from menu
     * a.  Create forum
     * Add a DiscussionForum instance in User.forums
     * Then repeat 3
     * P.S. Handle the exception if the user does not have permission
     * b.  Edit forum
     * Edit the given forum's topic with a user input, which is the topic of the forum
     * Then repeat 3
     * P.S. Handle the exception if the user does not have permission
     * c.  Delete forum
     * Delete the given forum
     * Then repeat 3
     * P.S. Handle the exception if the user does not have permission
     * P.S. Handle the exception if the given forum does not exist
     * d.  Choose a forum
     * proceed to 4
     * E.  Log out
     * proceed to 8
     * 4. select forum
     * a. e.g. forum one (topic)
     * proceed to 5
     * b. e.g. forum two
     * proceed to 5
     * c. e.g. forum three
     * proceed to 5
     * d. back to last menu
     * back to 3
     * 5. select action (Display the topic of the forum selected)
     * a. add post
     * add the post, and repeat 5
     * b. reply post
     * proceed to 6
     * c. back to last menu
     * back to 4
     * d. log out
     * 6. select action
     * a. e.g. post one (the first 30 character...)
     * proceed to 7
     * b. e.g. post two
     * same with a.
     * c. e.g. post three
     * same with a.
     * d. back to last menu
     * back to 3
     * 7. select action
     * a. import from file
     * let the user enter a file name, add the reply and back to 5
     * b. enter reply
     * let the user enter a String type reply, and add the reply, then back to 5
     * 8. Log out
     * a. save the user info again
     * b. save the forum (and the post in it)
     * c. End the program
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
        String WELCOME = "Welcome to the Learning Management System";
        System.out.println(WELCOME);
        login();
    }

    private void login() {
        String LOGIN_OR_CREATE = "Do you want to\n1. Log in\n2. Create an account";
        String ACCOUNT_TYPE = "What type of account would you like to create?\n1. Teacher\n2. Student";
        String ENTER_USERNAME = "Please enter the username: ";
        String ENTER_PASSWORD = "Please enter the password: ";
        String LOGIN_SUCCESSFUL = "Successfully logged in!";
        String LOGIN_UNSUCCESSFUL = "Username entered doesn't exist or the password is incorrect. Please try again.";
        String USERNAME_TAKEN = "The username is already taken. Please try another one.";
        boolean loggedIn = false;
        do {
            System.out.println(LOGIN_OR_CREATE);
            int loginChoice = getValidInt(2);
            if (loginChoice == 1) {
                System.out.println(ENTER_USERNAME);
                String username = getStringInput();
                System.out.println(ENTER_PASSWORD);
                String password = getStringInput();
                try {
                    DataManager.login(username, password);
                    loggedIn = true;
                } catch (LoginUnsuccessfulException e) {
                    System.out.println(LOGIN_UNSUCCESSFUL);
                }
            } else if (loginChoice == 2) {
                System.out.println(ACCOUNT_TYPE);
                int accountTypeChoice = getValidInt(2);
                do {
                    System.out.println(ENTER_USERNAME);
                    String username = getStringInput();
                    System.out.println(ENTER_PASSWORD);
                    String password = getStringInput();
                    try {
                        DataManager.createAccount(accountTypeChoice == 1 ?
                                Teacher.class : Student.class, username, password);
                        loggedIn = true;
                    } catch (UsernameAlreadyTakenException e) {
                        System.out.println(USERNAME_TAKEN);
                    }
                } while (!loggedIn);
            }
        } while (!loggedIn);
        System.out.println(LOGIN_SUCCESSFUL);
    }

    private void courseActivities() {
        final String MENU = "Do you want to\n1. Create a course\n2. Delete a course\n3. Edit a course\n4. Select a course";
        final String COURSE_TITLE = "Enter the title of the course: ";
        final String SELECT_COURSE = "Please select a course:";
        final String NO_PERMISSION = "You don't have permission to proceed the action.";
        System.out.println(MENU);

        int menuChoice = getValidInt(4);
        switch (menuChoice) {
            case 1: {//add a course
                try {
                    System.out.println(COURSE_TITLE);
                    currentUser.createCourse(getStringInput());
                } catch (NoPermissionException e) {
                    System.out.println(NO_PERMISSION);
                }
                break;
            }
            case 2: {//delete a course
                try {
                    System.out.println(SELECT_COURSE);
                    displayCoursesTitles();
                    currentUser.deleteCourse(new Course(getStringInput()));
                } catch (NoPermissionException e) {
                    System.out.println(NO_PERMISSION);
                }
                break;
            }
        }
    }

    private void displayCoursesTitles() {
        for (int i = 0; i < DataManager.courses.size(); i++) {
            System.out.print((i + 1) + "." + DataManager.courses.get(i));
            if (i != DataManager.courses.size() - 1) {
                System.out.println();
            }
        }
    }

    private int getIntInput() {
        Integer integer = null;
        do {
            try {
                integer = scanner.nextInt();
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Please enter an integer");
                e.printStackTrace();
            }
        } while (integer == null);
        return integer;
    }

    private int getValidInt(int optionNum) {
        int validInt;
        validInt = getIntInput();
        while (validInt > optionNum) {
            System.out.println("Please enter a valid option.");
            validInt = getIntInput();
        }
        return validInt;
    }

    private String getStringInput() {
        return scanner.nextLine();
    }

}
