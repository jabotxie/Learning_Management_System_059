import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * UserActivities class stores a static current user
 */
public class UserActivities implements Runnable {
    private static Scanner scanner;

    public static User currentUser;
    private static boolean isDataLoaded = false;

    public static void main(String[] args) throws InterruptedException {
        DataManager.initData();
        scanner = new Scanner(System.in);
        Thread user1 = new Thread(new UserActivities());
        user1.start();
        user1.join();
        DataManager.saveData();
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
        int courseSelection;
        do {
            courseSelection = courseActivities();

            if (courseSelection > 0) {
                int forumSelection;
                do {
                    forumSelection = forumActivities(DataManager.courses.get(courseSelection - 1));
                    int postSelection;
                    if (forumSelection > 0) {
                        postSelection = postActivities();
                    }

                } while (forumSelection != -1);
            }

        } while (courseSelection != -1);
        logOut();
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

    private void logOut() {
        final String GOODBYE = "Thanks for using Learning Management System!";
        System.out.println(GOODBYE);
    }

    /**
     * @return -1: user chooses to log out
     * 0: user chooses to create, edit, or delete a course (subsequently run this again)
     * positive integer: the course user wants to enter
     */
    private int courseActivities() {
        final String MENU = "Do you want to\n1. Create a course\n2. Delete a course\n3. Edit a course\n" +
                "4. Enter a course\n5. Log out";
        final String ENTER_METHOD = "How do you like to enter the title:\n1. Command line\n" +
                "2. Import through text file";
        final String FILE_NAME = "Please enter the files name: ";
        final String COURSE_TITLE = "Enter the title of the course: ";
        final String SELECT_COURSE = "Please select a course: ";
        final String NO_PERMISSION = "You don't have permission to proceed the action.";
        final String UPDATING_TITLE = "Please enter the updating course title: ";

        final String IMPORT_UNSUCCESSFUL = "The import process is not successful!";
        final String RETRY = "Do you want to try again?";
        final String EMPTY_COURSE_LIST = "The course list is empty. You have to create a course first.";
        System.out.println(MENU);

        int courseChoice = 0;

        int menuChoice = getValidInt(5);
        switch (menuChoice) {
            case 1: //add a course
                try {
                    verifyPermission();
                    System.out.println(ENTER_METHOD);
                    int methodChoice = getValidInt(3);
                    String courseTitle;
                    if (methodChoice == 1) {
                        System.out.println(COURSE_TITLE);
                        courseTitle = getStringInput();
                        currentUser.createCourse(courseTitle);

                    } else if (methodChoice == 2) {
                        boolean isProcessFinished = false;
                        do {
                            try {
                                System.out.println(FILE_NAME);
                                String fileName = getStringInput();
                                courseTitle = User.getImportedFile(fileName);
                                currentUser.createCourse(courseTitle);
                                isProcessFinished = true;
                            } catch (FileNotFoundException e) {
                                System.out.println(IMPORT_UNSUCCESSFUL);
                                System.out.println(RETRY);
                                int retryChoice = getValidInt(2);
                                if (retryChoice == 2) break;
                            }
                        } while (!isProcessFinished);
                    } else {
                        break;
                    }

                } catch (NoPermissionException e) {
                    System.out.println(NO_PERMISSION);
                }

                break;

            case 2: //delete a course
                try {
                    verifyPermission();
                    if (DataManager.courses.size() == 0) {
                        System.out.println(EMPTY_COURSE_LIST);
                        break;
                    }

                    System.out.println(SELECT_COURSE);
                    displayCoursesTitles();
                    int courseIndex = getValidInt(DataManager.courses.size()) - 1;
                    String deletingCourseTitle = DataManager.courses.get(courseIndex).getCourseTitle();
                    currentUser.deleteCourse(new Course(deletingCourseTitle));
                } catch (NoPermissionException e) {
                    System.out.println(NO_PERMISSION);
                }

                break;

            case 3: //Edit a course
                try {
                    verifyPermission();
                    if (DataManager.courses.size() == 0) {
                        System.out.println(EMPTY_COURSE_LIST);
                        break;
                    }
                    System.out.println(SELECT_COURSE);
                    displayCoursesTitles();
                    int i = getValidInt(DataManager.courses.size()) - 1;
                    System.out.println(UPDATING_TITLE);
                    String updatingTopic = getStringInput();

                    currentUser.editCourse(DataManager.courses.get(i), updatingTopic);
                } catch (NoPermissionException e) {
                    System.out.println(NO_PERMISSION);
                }
                break;
            case 4:
                if (DataManager.courses.size() == 0) {
                    System.out.println(EMPTY_COURSE_LIST);
                    break;
                }
                System.out.println(SELECT_COURSE);
                displayCoursesTitles();
                courseChoice = getValidInt(DataManager.courses.size());
                break;
            case 5:
                courseChoice = -1;
                break;
        }
        return courseChoice;
    }

    private int forumActivities(Course course) {
        int forumSelection = 0;
        final String MENU = "Do you want to\n1. Create a forum\n2. Delete a forum\n3. Edit a forum\n4. Enter a forum" +
                "\n5. Back to last menu";
        final String FORUM_TOPIC = "Please enter the topic of the created forum: ";
        System.out.println(MENU);
        int menuChoice = getValidInt(5);
        switch (menuChoice) {
            case 1:

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                forumSelection = -1;
                break;
        }

        return forumSelection;
    }

    private int postActivities() {
        int postSelection = 0;
        final String MENU = "Do you want to\n1. Create a post\n2. Delete a post\n3. reply a post\n4. Enter a post" +
                "\n5. Back to last menu";
        final String FORUM_TOPIC = "Please enter the topic of the created forum: ";
        System.out.println(MENU);
        int menuChoice = getValidInt(5);
        switch (menuChoice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                postSelection = -1;
                break;
        }
        return postSelection;
    }

    private void displayCoursesTitles() {
        for (int i = 0; i < DataManager.courses.size(); i++) {
            System.out.print((i + 1) + "." + DataManager.courses.get(i) + '\n');

        }
    }

    private int getIntInput() {
        Integer integer = null;
        do {
            try {
                integer = scanner.nextInt();

            } catch (NoSuchElementException e) {

                System.out.println("Please enter an integer");
                e.printStackTrace();
            }
            scanner.nextLine();
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

    private void verifyPermission() throws NoPermissionException {
        if (currentUser.getClass() == Student.class) throw new NoPermissionException();
    }
}
