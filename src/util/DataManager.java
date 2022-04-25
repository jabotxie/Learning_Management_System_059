package util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Project 4 -- Learning Management System
 * <p>
 * A class that helps to read and store the user information in a format of
 * <p>
 * ************************************
 * username
 * password
 * util.User Type(T for util.Teacher, S for util.Student)
 * ************************************
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */

public class DataManager implements Serializable, Runnable {

    public static ArrayList<User> users;
    public static final Date usersSync = new Date(System.currentTimeMillis());
    public static ArrayList<Course> courses;
    public static final Date coursesSync = new Date(System.currentTimeMillis());
    private static boolean isDataInitializedFromFile = false;
    public static String usersInfoFileName = "UserInfo.txt";
    public static String coursesInfoFileName = "CoursesInfo.txt";

    public static void main(String[] args) throws IOException {
        initData();
        //TODO: Initialize the server
        ServerSocket serverSocket = new ServerSocket(4242);

        System.out.println("Waiting for the client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    //method that initializes data upon first run
    public static void initData() {
        synchronized (coursesSync) {
            synchronized (usersSync) {
                if (!isDataInitializedFromFile) {
                    try {
                        courses = getCoursesFromFile();
                        if (courses == null) {
                            courses = new ArrayList<>();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        courses = new ArrayList<>();
                    }
                    try {
                        users = getUsersFromFile();

                    } catch (FileNotFoundException e) {//No users in database
                        users = new ArrayList<>();
                    }
                    isDataInitializedFromFile = true;
                }
            }
        }
    }

    //method to save user info and course info to file
    public static void saveData() {
        saveUserInfo();
        saveCoursesToFile();

    }

    //method that retrieves course info from file
    private static ArrayList<Course> getCoursesFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(coursesInfoFileName));
        CourseList courseList = (CourseList) objectInputStream.readObject();
        return courseList.getCourses();
    }

    //method that saves course info to file
    private static void saveCoursesToFile() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(coursesInfoFileName));
            DataManager.isDataInitializedFromFile = false;
            objectOutputStream.writeObject(new CourseList(DataManager.courses));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //method that retrieves user info from file
    private static ArrayList<User> getUsersFromFile() throws FileNotFoundException {

        BufferedReader bf = new BufferedReader(new FileReader(usersInfoFileName));
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        try {
            String line = bf.readLine();
            while (line != null) {
                lines.add(line);
                line = bf.readLine();
            }
            bf.close();

            for (int i = 0; i < lines.size(); i += 3) {
                String username = lines.get(i);
                String password = lines.get(i + 1);
                String userType = lines.get(i + 2);
                Class<? extends User> t = userType.equals("T") ? Teacher.class : Student.class;
                if (t == Teacher.class) {
                    users.add(new Teacher(username, password));
                } else {
                    users.add(new Student(username, password));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    //method to save user info to file
    private static void saveUserInfo() {
        synchronized (usersSync) {

            StringBuilder sb = new StringBuilder();
            for (User user : users) {
                sb.append(user.getUsername()).append('\n');
                sb.append(user.getPassword()).append('\n');
                sb.append(user.getClass() == Teacher.class ? "T" : "S").append('\n');
            }
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(usersInfoFileName));
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //method to allow user login
    public static Class<? extends User> checkToken(String username, String password) {
        synchronized (usersSync) {
            int i = users.indexOf(new Student(username, password));
            try {
                if (users.get(i).getPassword().equals(password)) {
                    return users.get(i).getClass();
                } else {
                    return null;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }
    }

    //method to create account
    public static boolean createAccount(Class<? extends User> c, String username, String password) {
        synchronized (usersSync) {
            if (users.contains(new Student(username, password))) {
                return false;
            } else {
                if (c == Teacher.class) {
                    Teacher newTeacher = new Teacher(username, password);
                    users.add(newTeacher);
                    return true;
                } else {
                    users.add(new Student(username, password));
                    Student newStudent = new Student(username, password);
                    users.add(newStudent);
                    return true;
                }

            }
        }
    }

    //method to remove account
    public static void deleteAccount(User currentUser, String username, String password) {
        synchronized (usersSync) {
            synchronized (coursesSync) {
                checkToken(username, password);
                for (Course course : courses) {
                    for (DiscussionForum forum : course.forums) {
                        ArrayList<Integer> removingPostsIndex = new ArrayList<>();
                        for (int i = 0; i < forum.posts.size(); i++) {
                            DiscussionPost post = forum.posts.get(i);
                            post.votes.removeIf(vote -> vote.getStudent().equals(currentUser));

                            post.replies.removeIf(reply -> reply.getOwner().equals(currentUser));
                            if (post.getOwner().equals(currentUser))
                                removingPostsIndex.add(i);
                        }
                        for (int i = removingPostsIndex.size() - 1; i >= 0; i--) {
                            forum.posts.remove(removingPostsIndex.get(i).intValue());
                        }
                    }
                }
                users.remove(currentUser);
            }
        }
        saveData();
    }


    public static boolean createCourse(String courseTitle) {
        synchronized (coursesSync) {
            if (courses.contains(new Course(courseTitle))) return false;
            courses.add(new Course(courseTitle));
            return true;
        }
    }

    @Override
    public void run() {
        try {
            initData();
            ServerSocket serverSocket = new ServerSocket(4242);
            Socket socket = serverSocket.accept();
            System.out.println(socket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] getCourseTitles() {
        String[] courseTitles = new String[courses.size()];
        for (int i = 0; i < courseTitles.length; i++) {
            courseTitles[i] = courses.get(i).getCourseTitle();
        }
        return courseTitles;
    }

    public ArrayList<String> getForumTopics(String courseTitle) throws TargetNotFoundException {
        synchronized (coursesSync) {
            ArrayList<String> forumTopics = new ArrayList<>();
            if (courses.contains(new Course(courseTitle))) {
                int courseIndex = courses.indexOf(new Course(courseTitle));
                Course course = courses.get(courseIndex);
                for (DiscussionForum forum: course.forums) {
                    forumTopics.add(forum.getTopic());
                }
                return forumTopics;
            } else {
                throw new TargetNotFoundException();
            }
        }
    }
    //TODO: Add data function methods

}
