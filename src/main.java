import javax.swing.*;


public class main {
    public static void main(String[] args) {
        User.initForum();
        SwingUtilities.invokeLater(new UserActivities());
        User.saveForum();
    }
}
