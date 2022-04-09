import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that helps to read and store the user information in a format of
 *
 * ************************************
 * username
 * password
 * User Type(T for Teacher, S for Student)
 */
public class AccountManager {

    public static ArrayList<User> users;

    public AccountManager(ArrayList<User> users) {
        AccountManager.users = users;
    }

    private ArrayList<User> getUsers(String filename) throws FileNotFoundException {
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

}
