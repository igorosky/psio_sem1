package GUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;

import javax.swing.BoxLayout;

import College.Course;

public class CoursesManager extends Dialog {

    private HashSet<Course> list;
    private List displayList;
    private Button removeButton;
    
    private void updateList() {
        displayList.removeAll();
        removeButton.setEnabled(false);
        for(final Course person : list) {
            displayList.add(person.getName());
        }
    }
    
    private void init() {
        setSize(300, 200);
        addWindowListener(new DialogClose(this));
        setLayout(new BorderLayout());
        displayList = new List();
        
        Panel bottom = new Panel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        Button addButton = new Button("Add");
        Dialog tmp = this;
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogGetString dialog = new DialogGetString(tmp);
                dialog.setTitle("Subject name");
                dialog.getButton().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(dialog.getText().getText().isEmpty()) {
                            return;
                        }
                        list.add(new Course(dialog.getText().getText()));
                        dialog.setVisible(false);
                        updateList();
                    }
                    
                });
                dialog.setVisible(true);
            }
            
        });
        removeButton = new Button("Remove");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                list.remove(new Course(displayList.getSelectedItem()));
                updateList();
            }
            
        });
        removeButton.setEnabled(false);

        displayList.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                removeButton.setEnabled(true);
            }
            
        });
        
        bottom.add(addButton, BorderLayout.PAGE_END);
        bottom.add(removeButton, BorderLayout.PAGE_END);
        
        
        add(displayList, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);
        updateList();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }
    
    public CoursesManager(Frame owner, HashSet<Course> list) {
        super(owner);
        this.list = list;
        init();
    }
    
    public CoursesManager(Dialog owner, HashSet<Course> list) {
        super(owner);
        this.list = list;
        init();
    }
}
