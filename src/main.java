import javax.swing.*;


public class main {
    public static void main(String[] args) {
        DataManager.initData();
//        SwingUtilities.invokeLater(new UserActivities());
        DataManager.saveData();
    }
}
