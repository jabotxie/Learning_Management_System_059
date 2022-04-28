package util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static String usersInfoFileName = "UserInfo.txt";
    public static String coursesInfoFileName = "CoursesInfo.txt";


    //method that initializes data upon first run
    public static void initData() {
        synchronized (coursesSync) {
            synchronized (usersSync) {
                courses = getCoursesFromFile();
                if (courses == null) {
                    courses = new ArrayList<>();
                }
                users = getUsersFromFile();
                if (users == null) {
                    users = new ArrayList<>();
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
    @SuppressWarnings("unchecked")
    private static ArrayList<Course> getCoursesFromFile() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(coursesInfoFileName));
            return (ArrayList<Course>) objectInputStream.readObject();
        } catch (ClassCastException | IOException | ClassNotFoundException e) {
            return null;
        }
    }

    //method that saves course info to file
    private static void saveCoursesToFile() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(coursesInfoFileName));
            objectOutputStream.writeObject(DataManager.courses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method that retrieves user info from file
    private static ArrayList<User> getUsersFromFile() {
        try {
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
        } catch (FileNotFoundException e) {
            return null;
        }
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
                    users.add(new Teacher(username, password));
                } else {
                    users.add(new Student(username, password));
                }
                return true;
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
                        List<Integer> removingPostsIndex = new ArrayList<>();
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

    public static boolean deleteCourse(String courseTitle) {
        synchronized (coursesSync) {
            if (!courses.contains(new Course(courseTitle))) return false;
            courses.remove(new Course(courseTitle));
            return true;
        }
    }

    public static boolean renameCourse(String courseName, String newTitle) {
        synchronized (coursesSync) {
            int courseIndex = courses.indexOf(new Course(courseName));
            if (courseIndex == -1) return false;
            courses.get(courseIndex).setCourseTitle(newTitle);
            return true;
        }
    }


    public static int createForum(String courseTitle, String topic) {
        synchronized (coursesSync) {
            int courseIndex = courses.indexOf(new Course(courseTitle));
            if (courseIndex == -1) {
                return -2;
            }
            Course course = courses.get(courseIndex);
            if (course.forums.contains(new DiscussionForum(topic))) return -1;
            course.forums.add(new DiscussionForum(topic));
            return 1;
        }
    }

    public static int editForum(String courseTitle, String oldTopic, String newTopic) {
        synchronized (coursesSync) {
            int courseIndex = courses.indexOf(new Course(courseTitle));
            if (courseIndex == -1) return -1;

            Course course = courses.get(courseIndex);
            if (course.forums.contains(new DiscussionForum(newTopic))) return -3;
            int forumIndex = course.forums.indexOf(new DiscussionForum(oldTopic));
            if (forumIndex == -1) return -2;

            DiscussionForum forum = course.forums.get(forumIndex);
            forum.setTopic(newTopic);
            return 1;

        }
    }

    public static int deleteForum(String courseTitle, String topic) {
        synchronized (coursesSync) {
            int courseIndex = courses.indexOf(new Course(courseTitle));
            if (courseIndex == -1) return -1;

            Course course = courses.get(courseIndex);
            int forumIndex = course.forums.indexOf(new DiscussionForum(topic));
            if (forumIndex == -1) return -2;

            course.forums.remove(forumIndex);
            return 1;
        }
    }

    public static int checkForumExistence(String courseTitle, String topic) {
        synchronized (coursesSync) {
            int courseIndex = courses.indexOf(new Course(courseTitle));
            if (courseIndex == -1) return -1;

            Course course = courses.get(courseIndex);
            if (!course.forums.contains(new DiscussionForum(topic))) return -2;

            return 1;
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
                for (DiscussionForum forum : course.forums) {
                    forumTopics.add(forum.getTopic());
                }
                return forumTopics;
            } else {
                throw new TargetNotFoundException();
            }
        }
    }

}
