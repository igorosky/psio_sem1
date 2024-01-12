package GUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
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
import java.util.Comparator;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import College.Department;
import College.DepartmentNotExistException;
import College.People.Employee;
import College.People.Person;
import College.People.Scientist;
import College.People.Student;
import College.People.Worker;
import GUI.AddPerson.Callback;
import ObjectSaver.ObjectSaver;

public class MainMenu {
    private Frame mainFrame;
    
    // It would be way better to use some orderedSet (like SortedSet but it does not implements Serializable)
    private ArrayList<Person> people;

    private Button buttonAdd;
    private Button buttonDelete;
    private Button buttonEdit;
    private Button buttonSave;
    private Button buttonLoad;

    private List list;
    private TextArea textArea;
    private JPanel panel;
    
    class WindowClose extends WindowAdapter {
        public void windowClosing(WindowEvent windowEvent) {
            System.exit(0);
        }
    }
    
    private void updateList() {
        people.sort(new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.compareTo(o2);
            }
            
        });
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
        panel = new JPanel();
        
        mainFrame = new Frame("College");
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new BorderLayout());

        buttonSave = new Button("Save");
        buttonSave.setEnabled(false);
        buttonSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                fileChooser.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getActionCommand() != "ApproveSelection") {
                            return;
                        }
                        ObjectSaver.serialize(people, fileChooser.getSelectedFile());
                    }
                    
                });
                fileChooser.showSaveDialog(mainFrame);
            }
            
        });
        buttonLoad = new Button("Load");
        buttonLoad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                fileChooser.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getActionCommand() != "ApproveSelection") {
                            return;
                        }
                        Optional<ArrayList<Person>> result = ObjectSaver.deserialize(fileChooser.getSelectedFile());
                        if(!result.isPresent()) {
                            // TODO
                            System.out.println("Not found");
                            return;
                        }
                        people = result.get();
                        updateList();
                    }
                    
                });
                fileChooser.showOpenDialog(mainFrame);
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
                final Person selectedPerson = people.get(list.getSelectedIndex());
                panel.removeAll();
                java.util.List<Component> toAdd = new ArrayList<>();

                TextField firstName = new TextField(selectedPerson.getFname());
                TextField lastName = new TextField(selectedPerson.getLname());
                TextField email = new TextField(selectedPerson.getMail());
                TextField index = new TextField();
                Choice department = new Choice();
                Checkbox worksShifts;
                Checkbox worksInOffice;
                
                for(int i = 1; i <= 14; ++i) {
                    department.add(String.format("W%d%s", i, i == 4 ? "N" : ""));
                }

                toAdd.add(new Label("Type:"));
                toAdd.add(new Label(selectedPerson instanceof Student ? "Student" : (selectedPerson instanceof Scientist ? "Scientist" : "Worker")));
                toAdd.add(new Label("First name:"));
                toAdd.add(firstName);
                toAdd.add(new Label("Last name:"));
                toAdd.add(lastName);
                toAdd.add(new Label("Email:"));
                toAdd.add(email);
                Button saveButton = new Button("Save");

                saveButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedPerson.setFname(firstName.getText());
                        selectedPerson.setLname(lastName.getText());
                        selectedPerson.setMail(email.getText());
                        updateList();
                    }
                    
                });

                if(selectedPerson instanceof Employee) {
                    Employee employee = (Employee)selectedPerson;
                    department.select(Department.getIndex(employee.getDepartment()));
                    toAdd.add(new Label("Department:"));
                    toAdd.add(department);
                    
                    saveButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                employee.setDepartment(Department.getDepartment(department.getSelectedIndex() + 1));
                            }
                            catch(final DepartmentNotExistException ex) {
                                // Infallible
                            }
                        }
                        
                    });
                }

                if(selectedPerson instanceof Student) {
                    Student student = (Student)selectedPerson;
                    index.setText(student.getIndexNumber());
                    toAdd.add(new Label("Index:"));
                    toAdd.add(index);
                    toAdd.add(new Label("Courses:"));
                    Button coursesManagerButton = new Button("Manage");
                    coursesManagerButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new CoursesManager(mainFrame, student.getCourses());
                        }
                        
                    });
                    toAdd.add(coursesManagerButton);

                    saveButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            student.setIndexNumber(index.getText());
                        }
                        
                    });
                }
                else if(selectedPerson instanceof Scientist) {
                    Scientist scientist = ((Scientist)selectedPerson);
                    index.setText(Integer.toString(scientist.gethIndex()));
                    toAdd.add(new Label("HIndex:"));
                    toAdd.add(index);

                    saveButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            scientist.sethIndex(Integer.parseInt(index.getText()));
                        }
                        
                    });
                }
                else if(selectedPerson instanceof Worker) {
                    Worker worker = ((Worker)selectedPerson);
                    worksShifts = new Checkbox();
                    worksInOffice = new Checkbox();
                    worksShifts.setState(worker.isWorksShifts());
                    worksInOffice.setState(worker.isWorksInOffice());
                    toAdd.add(new Label("Works shifts:"));
                    toAdd.add(worksShifts);
                    toAdd.add(new Label("Works in office:"));
                    toAdd.add(worksInOffice);
                    toAdd.add(new Label("Position:"));
                    index.setText(worker.getPosition());
                    toAdd.add(index);
                    saveButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            worker.setWorksInOffice(worksInOffice.getState());
                            worker.setWorksShifts(worksShifts.getState());
                            worker.setPosition(index.getText());
                        }
                        
                    });
                }
                
                panel.setLayout(new GridLayout(toAdd.size() / 2 + 1, 2));
                for(Component component : toAdd) {
                    panel.add(component);
                }

                panel.add(new Panel());
                
                panel.add(saveButton);
                
                mainFrame.revalidate();
            }
            
        });

        buttonAdd = new Button("Add");
        buttonAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPerson(mainFrame, new Callback() {

                    @Override
                    public void callback(Person person) {
                        people.add(person);
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
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.add(end, BorderLayout.PAGE_END);
        mainFrame.addWindowListener(new WindowClose());

        mainFrame.setVisible(true);
    }
}
