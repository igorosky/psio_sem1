package GUI;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import College.Course;
import College.Department;
import College.DepartmentNotExistException;
import College.People.Person;
import College.People.Scientist;
import College.People.Student;
import College.People.Worker;

public class AddPerson extends Dialog {
    private Label fnameLabel;
    private TextField fname;
    private Label lnameLabel;
    private TextField lname;
    private Label emailLabel;
    private TextField email;

    Choice type;

    // Employee
    // private Department department;
    private Choice departmentChoice;

    // Scientist
    private Label hIndexLabel;
    private TextField hIndex;

    // Worker
    private Checkbox worksInOffice;
    private Checkbox worksShifts;
    private Label positionLabel;
    private TextField position;

    // Student
    private Label indexNumberLabel;
    private TextField indexNumber;
    private List courses;
    private Button buttonCoursesAdd;
    private Button buttonCoursesRemove;

    Button buttonOk;
    private Button buttonCancel;

    public interface Callback {
        void callback(Person person);
    }

    public AddPerson(Frame owner, Callback callback) {
        super(owner);
        buttonCancel = new Button("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
            
        });

        buttonOk = new Button("Ok");

        fnameLabel = new Label("First name:");
        fname = new TextField(15);
        lnameLabel = new Label("Last name:");
        lname = new TextField(15);
        emailLabel = new Label("Email:");
        email = new TextField(15);
        type = new Choice();
        type.add("Scientist");
        type.add("Student");
        type.add("Worker");
        type.select(0);
        departmentChoice = new Choice();

        hIndexLabel = new Label("HIndex:");
        hIndex = new TextField(9);

        worksInOffice = new Checkbox("Works in office");
        worksInOffice.setEnabled(false);
        worksShifts = new Checkbox("Works shifts");
        worksShifts.setEnabled(false);
        positionLabel = new Label("Position:");
        positionLabel.setEnabled(false);
        position = new TextField(15);
        position.setEnabled(false);

        indexNumberLabel = new Label("Index:");
        indexNumberLabel.setEnabled(false);
        indexNumber = new TextField(15);
        indexNumber.setEnabled(false);
        courses = new List();
        courses.setEnabled(false);
        courses.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                buttonCoursesRemove.setEnabled(true);
            }
            
        });
        buttonCoursesAdd = new Button("Add");
        buttonCoursesAdd.setEnabled(false);
        Dialog tmp = this;
        buttonCoursesAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogGetString dialog = new DialogGetString(tmp);
                setTitle("Subject name");
                dialog.getButton().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(dialog.getText().getText().isEmpty()) {
                            return;
                        }
                        courses.add(dialog.getText().getText());
                        setVisible(false);
                    }
                    
                });
                setVisible(true);
            }
            
        });
        buttonCoursesRemove = new Button("Remove");
        buttonCoursesRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                courses.remove(courses.getSelectedIndex());
            }
            
        });
        buttonCoursesRemove.setEnabled(false);
        
        type.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                departmentChoice.setEnabled(type.getSelectedIndex() != 1);
                hIndexLabel.setEnabled(type.getSelectedIndex() == 0);
                hIndex.setEnabled(type.getSelectedIndex() == 0);
                worksInOffice.setEnabled(type.getSelectedIndex() == 2);
                worksShifts.setEnabled(type.getSelectedIndex() == 2);
                positionLabel.setEnabled(type.getSelectedIndex() == 2);
                position.setEnabled(type.getSelectedIndex() == 2);
                indexNumberLabel.setEnabled(type.getSelectedIndex() == 1);
                indexNumber.setEnabled(type.getSelectedIndex() == 1);
                courses.setEnabled(type.getSelectedIndex() == 1);
                buttonCoursesAdd.setEnabled(type.getSelectedIndex() == 1);
                buttonCoursesRemove.setEnabled(type.getSelectedIndex() == 1 && courses.getSelectedIndex() != -1);
            }
            
        });

        for(int i = 1; i <= 14; ++i) {
            departmentChoice.add(String.format("W%d%s", i, i == 4 ? "N" : ""));
        }

        buttonOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch(type.getSelectedIndex()) {
                case 0: // Scientist
                    {
                        int hIdx = Integer.parseInt(hIndex.getText());
                        try {
                            callback.callback(new Scientist(fname.getText(), lname.getText(), email.getText(), Department.getDepartment(departmentChoice.getSelectedIndex() + 1), hIdx));
                        }
                        catch(DepartmentNotExistException ex) {
                            // impossible
                        }
                        break;
                    }
                case 1: // Student
                    {
                        Student student = new Student(fname.getText(), lname.getText(), email.getText(), indexNumber.getText());
                        for(int i = 0; i < courses.getItemCount(); ++i) {
                            student.addCourse(new Course(courses.getItem(i)));
                        }
                        callback.callback(student);
                        break;
                    }
                case 2: // Worker
                    {
                        try {
                            callback.callback(new Worker(fname.getText(), lname.getText(), email.getText(), Department.getDepartment(departmentChoice.getSelectedIndex() + 1), worksInOffice.getState(), worksShifts.getState(), position.getText()));
                        }
                        catch(DepartmentNotExistException ex) {
                            // impossible
                        }
                        break;
                    }
                default:
                    {
                        // Impossible state
                        return;
                    }
                }
                setVisible(false);
            }
            
        });

        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(fnameLabel);
        add(fname);
        add(lnameLabel);
        add(lname);
        add(emailLabel);
        add(email);
        add(type);
        add(departmentChoice);
        add(hIndexLabel);
        add(hIndex);
        add(worksInOffice);
        add(worksShifts);
        add(positionLabel);
        add(position);
        add(indexNumberLabel);
        add(indexNumber);
        add(courses);
        add(buttonCoursesAdd);
        add(buttonCoursesRemove);
        add(buttonCancel);
        add(buttonOk);
        addWindowListener(new DialogClose(this));
        setSize(200, 600);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

}
