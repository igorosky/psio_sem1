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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import Backend.Backend;
import College.Department;
import College.DepartmentNotExistException;
import College.People.Employee;
import College.People.Person;
import College.People.Scientist;
import College.People.Student;
import College.People.Worker;
import Filter.Filter.FilterParam;
import GUI.AddPerson.Callback;

public class MainMenu {
    private Frame mainFrame;

    private Button buttonAdd;
    private Button buttonDelete;
    private Button filterButton;
    private Button clearFiltersButton;
    private Button buttonSave;
    private Button buttonLoad;

    private List list;
    private JPanel panel;
    private ArrayList<FilterParam<Person>> filters;
    private Backend backend;

    
    class WindowClose extends WindowAdapter {
        public void windowClosing(WindowEvent windowEvent) {
            System.exit(0);
        }
    }
    
    private void updateList() {
        list.removeAll();
        buttonDelete.setEnabled(false);
        Collection<Person> people = backend.displayPeople(filters);
        buttonSave.setEnabled(!people.isEmpty());
        for (Person person : people) {
            list.add(person.getName());
        }
    }
    
    public MainMenu(Backend bcd) {
        backend = bcd;
        filters = new ArrayList<>();
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
                        backend.save(fileChooser.getSelectedFile());
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
                        backend.load(fileChooser.getSelectedFile());
                        updateList();
                    }
                    
                });
                fileChooser.showOpenDialog(mainFrame);
            }
            
        });

        Panel top = new Panel(new FlowLayout(FlowLayout.LEFT));
        top.add(buttonSave);
        top.add(buttonLoad);

        list = new List();
        list.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                buttonDelete.setEnabled(true);
                final Person selectedPerson = backend.getCurrentlyDisplayedPeople().get(list.getSelectedIndex());
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
                        backend.addPerson(person);
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
                backend.removeDisplayedPerson(list.getSelectedIndex());
                updateList();
            }
            
        });
        filterButton = new Button("Filter");
        filterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new FilterMenu(mainFrame, new FilterMenu.Callback() {

                    @Override
                    public void callback(ArrayList<FilterParam<Person>> filters_) {
                        filters = filters_;
                        updateList();
                    }
                    
                }, filters);
            }
            
        });
        clearFiltersButton = new Button("Clear Filters");
        clearFiltersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                filters.clear();
                updateList();
            }
            
        });

        Panel end = new Panel(new FlowLayout(FlowLayout.LEFT));
        end.add(buttonAdd);
        end.add(buttonDelete);
        end.add(filterButton);
        end.add(clearFiltersButton);
        
        mainFrame.add(top, BorderLayout.PAGE_START);
        mainFrame.add(list, BorderLayout.LINE_START);
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.add(end, BorderLayout.PAGE_END);
        mainFrame.addWindowListener(new WindowClose());

        mainFrame.setVisible(true);
    }
}
