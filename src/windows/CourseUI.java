package windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseUI implements ActionListener {
    JFrame frame = new JFrame("Learning Management System");
    JButton editButton = new JButton("edit");
    JButton createButton = new JButton("create");
    JButton deleteButton = new JButton("delete");
    ArrayList<JButton> courseButtons;
    
    public CourseUI() {

        editButton.setFocusable(false);
        editButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);



        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.add(editButton);
        panel.add(createButton);
        panel.add(deleteButton);

        frame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
