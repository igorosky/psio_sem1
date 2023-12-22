package GUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Optional;

import College.Course;
import College.Department;
import College.DepartmentNotExistException;
import College.People.Person;
import College.People.Scientist;
import College.People.Student;
import College.People.Worker;
import ObjectSaver.ObjectSaver;

public class MainMenu {
    private Frame mainFrame;
    
    private ArrayList<Person> people;

    private Button buttonAdd;
    private Button buttonDelete;
    private Button buttonEdit;
    private Button buttonSave;
    private Button buttonLoad;

    List list;
    TextArea textArea;
    
    class WindowClose extends WindowAdapter {
        public void windowClosing(WindowEvent windowEvent) {
            System.exit(0);
        }
    }

    class DialogClose extends WindowAdapter {
        final Dialog dialog;
        
        private DialogClose(final Dialog dialog) {
            this.dialog = dialog;
        }
        
        public void windowClosing(final WindowEvent windowEvent) {
            dialog.setVisible(false);
        }
    }

    class DialogGetString extends Dialog {
        private TextField text;
        private Button button;
        
        public DialogGetString(Dialog owner) {
            super(owner);
            text = new TextField(11);
            button = new Button("Ok");
            setLayout(new FlowLayout());
            add(text);
            add(button);
            addWindowListener(new DialogClose(this));
            setModalityType(ModalityType.APPLICATION_MODAL);
            setSize(200, 70);
            setResizable(false);
        }
        
        public DialogGetString(Frame owner) {
            super(owner);
            text = new TextField(11);
            button = new Button("Ok");
            setLayout(new FlowLayout());
            add(text);
            add(button);
            addWindowListener(new DialogClose(this));
            setModalityType(ModalityType.APPLICATION_MODAL);
            setSize(200, 70);
            setResizable(false);
        }
    }

    class DialogPeron extends Dialog {
        private Label fnameLabel;
        private TextField fname;
        private Label lnameLabel;
        private TextField lname;
        private Label emailLabel;
        private TextField email;

        private Choice type;

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

        private Button buttonOk;
        private Button buttonCancel;

        public DialogPeron(Frame owner) {
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
                    dialog.setTitle("Subject name");
                    dialog.button.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(dialog.text.getText().isEmpty()) {
                                return;
                            }
                            courses.add(dialog.text.getText());
                            dialog.setVisible(false);
                        }
                        
                    });
                    dialog.setVisible(true);
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
    
    private void updateList() {
        list.removeAll();
        textArea.setText("");
        buttonDelete.setEnabled(false);
        buttonEdit.setEnabled(false);
        for (Person person : people) {
            list.add(person.getName());
        }
        buttonSave.setEnabled(!people.isEmpty());
    }
    
    public MainMenu() {
        people = new ArrayList<>();
        
        mainFrame = new Frame("College");
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new BorderLayout());

        buttonSave = new Button("Save");
        buttonSave.setEnabled(false);
        buttonSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogGetString getName = new DialogGetString(mainFrame);
                getName.setTitle("Save file");
                getName.button.setLabel("Save");
                getName.button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getName.text.getText().isEmpty()) {
                            // TODO
                            return;
                        }
                        ObjectSaver.serialize(people, getName.text.getText());
                        getName.setVisible(false);
                    }
                    
                });
                getName.setVisible(true);
            }
            
        });
        buttonLoad = new Button("Load");
        buttonLoad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogGetString getName = new DialogGetString(mainFrame);
                getName.setTitle("Load file");
                getName.button.setLabel("Load");
                getName.button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getName.text.getText().isEmpty()) {
                            // TODO
                            return;
                        }
                        Optional<ArrayList<Person>> result = ObjectSaver.deserialize(getName.text.getText());
                        if(!result.isPresent()) {
                            // TODO
                            System.out.println("Not found");
                            return;
                        }
                        people = result.get();
                        updateList();
                        getName.setVisible(false);
                    }
                    
                });
                getName.setVisible(true);
            }
            
        });

        textArea = new TextArea();
        textArea.setEditable(false);
        
        Panel top = new Panel(new FlowLayout(FlowLayout.LEFT));
        top.add(buttonSave);
        top.add(buttonLoad);

        list = new List();
        list.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                buttonDelete.setEnabled(true);
                buttonEdit.setEnabled(true);
                textArea.setText(people.get(list.getSelectedIndex()).toString());
            }
            
        });

        buttonAdd = new Button("Add");
        buttonAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogPeron dialog = new DialogPeron(mainFrame);
                dialog.buttonOk.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch(dialog.type.getSelectedIndex()) {
                        case 0: // Scientist
                            {
                                int hIdx = Integer.parseInt(dialog.hIndex.getText());
                                try {
                                    people.add(new Scientist(dialog.fname.getText(), dialog.lname.getText(), dialog.email.getText(), Department.getDepartment(dialog.departmentChoice.getSelectedIndex() + 1), hIdx));
                                }
                                catch(DepartmentNotExistException ex) {
                                    // impossible
                                }
                                break;
                            }
                        case 1: // Student
                            {
                                Student student = new Student(dialog.fname.getText(), dialog.lname.getText(), dialog.email.getText(),dialog. indexNumber.getText());
                                for(int i = 0; i < dialog.courses.getItemCount(); ++i) {
                                    student.addCourse(new Course(dialog.courses.getItem(i)));
                                }
                                people.add(student);
                                break;
                            }
                        case 2: // Worker
                            {
                                try {
                                    people.add(new Worker(dialog.fname.getText(), dialog.lname.getText(), dialog.email.getText(), Department.getDepartment(dialog.departmentChoice.getSelectedIndex() + 1), dialog.worksInOffice.getState(), dialog.worksShifts.getState(), dialog.position.getText()));
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
                        dialog.setVisible(false);
                        updateList();
                    }
                    
                });
            }
            
        });
        buttonDelete = new Button("Delete");
        buttonDelete.setEnabled(false);
        buttonDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                people.remove(list.getSelectedIndex());
                updateList();
            }
            
        });
        buttonEdit = new Button("Edit");
        buttonEdit.setEnabled(false);

        Panel end = new Panel(new FlowLayout(FlowLayout.LEFT));
        end.add(buttonAdd);
        end.add(buttonDelete);
        end.add(buttonEdit);
        
        mainFrame.add(top, BorderLayout.PAGE_START);
        mainFrame.add(list, BorderLayout.LINE_START);
        mainFrame.add(textArea, BorderLayout.CENTER);
        mainFrame.add(end, BorderLayout.PAGE_END);
        mainFrame.addWindowListener(new WindowClose());

        mainFrame.setVisible(true);
    }
}
