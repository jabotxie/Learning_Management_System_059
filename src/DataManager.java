
import java.io.*;
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
 * User Type(T for Teacher, S for Student)
 * ************************************
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 
 * @version April 11, 2022
 */

public class DataManager implements Serializable {

    public static ArrayList<User> users;
    public static final Date usersSync = new Date(System.currentTimeMillis());
    public static ArrayList<Course> courses;
    public static final Date coursesSync = new Date(System.currentTimeMillis());
    private static boolean isDataInitializedFromFile = false;
    public static String usersInfoFileName = "UserInfo.txt";
    public static String coursesInfoFileName = "CoursesInfo.txt";

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

    public static void saveData() {
        saveUserInfo();
        saveCoursesToFile();

    }

    private static ArrayList<Course> getCoursesFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(coursesInfoFileName));
        CourseList courseList = (CourseList) objectInputStream.readObject();
        return courseList.getCourses();
    }

    private static void saveCoursesToFile() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(coursesInfoFileName));
            DataManager.isDataInitializedFromFile = false;
            objectOutputStream.writeObject(new CourseList(DataManager.courses));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    public static void login(UserActivities userActivities, String username, String password) throws AccountInfoNotMatchException {
        synchronized (usersSync) {
            int i = users.indexOf(new Student(username, password));
            try {
                if (users.get(i).getPassword().equals(password)) {
                    userActivities.currentUser = users.get(i);
                } else {
                    throw new AccountInfoNotMatchException();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AccountInfoNotMatchException();
            }
        }

    }

    public static void createAccount(UserActivities userActivities, Class<? extends User> c, String username, String password)
            throws UsernameAlreadyTakenException {
        synchronized (usersSync) {
            if (users.contains(new Student(username, password))) {
                throw new UsernameAlreadyTakenException();
            } else {
                if (c == Teacher.class) {
                    users.add(new Teacher(username, password));
                    userActivities.currentUser = new Teacher(username, password);
                } else {
                    users.add(new Student(username, password));
                    userActivities.currentUser = new Student(username, password);
                }
                saveUserInfo();
            }
        }

    }

    public static void deleteAccount(UserActivities userActivities, String username, String password)
            throws AccountInfoNotMatchException {
        synchronized (usersSync) {
            synchronized (coursesSync) {
                login(userActivities, username, password);
                for (Course course : courses) {
                    for (DiscussionForum forum : course.forums) {
                        ArrayList<Integer> removingPostsIndex = new ArrayList<>();
                        for (int i = 0; i < forum.posts.size(); i++) {
                            DiscussionPost post = forum.posts.get(i);
                            post.votes.removeIf(vote -> vote.getStudent().equals(userActivities.currentUser));


                            post.replies.removeIf(reply -> reply.getOwner().equals(userActivities.currentUser));
                            if (post.getOwner().equals(userActivities.currentUser))
                                removingPostsIndex.add(i);
                        }
                        for (int i = removingPostsIndex.size() - 1; i >= 0; i--) {
                            forum.posts.remove(removingPostsIndex.get(i).intValue());
                        }
                    }
                }
                users.remove(userActivities.currentUser);
                userActivities.currentUser = null;
            }
        }
        saveData();
    }

}
