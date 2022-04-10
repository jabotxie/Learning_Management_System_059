import java.io.*;
import java.util.ArrayList;

/**
 * A class that helps to read and store the user information in a format of
 *
 * ************************************
 * username
 * password
 * User Type(T for Teacher, S for Student)
 * ************************************
 *
 */

public class AccountManager {

    public static ArrayList<User> users;

    public static String userInfoFileName = "UserInfo.txt";

    public static void updateUsers() {
        try {
            AccountManager.users = getUsers(userInfoFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<User> getUsers(String filename) throws FileNotFoundException {

        BufferedReader bf = new BufferedReader(new FileReader(filename));
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

    public synchronized static void saveUserInfo(String fileName) {

        StringBuilder sb = new StringBuilder();
        for (User user: users) {
            sb.append(user.getUsername()).append('\n');
            sb.append(user.getPassword()).append('\n');
            sb.append(user.getClass() == Teacher.class ? "T" : "S").append('\n');
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized static void login(String username, String password) throws LoginUnsuccessfulException {

        int i = users.indexOf(new Student(username, password));
        try {
            if(users.get(i).getPassword().equals(password)) {
                UserActivities.currentUser = users.get(i);
            }  else {
                throw new LoginUnsuccessfulException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LoginUnsuccessfulException();
        }


    }

    public static synchronized void createAccount(Class<? extends User> c, String username, String password)
            throws UsernameAlreadyTakenException {
        if (users.contains(new Student(username, password))) {
            throw new UsernameAlreadyTakenException();
        } else {
            if (c == Teacher.class) {
                users.add(new Teacher(username, password));
            } else {
                users.add(new Student(username, password));
            }
            saveUserInfo(userInfoFileName);
        }
    }


}
