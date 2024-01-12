package GUI;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import College.People.Person;
import Filter.Collage.PersonFilterByHIndexGreater;
import Filter.Collage.PersonFilterByIndex;
import Filter.Collage.PersonFilterByLName;
import Filter.Collage.PersonFilterByPosition;
import Filter.Collage.PersonFilterIsScientist;
import Filter.Collage.PersonFilterIsStudent;
import Filter.Collage.PersonFilterIsWorker;
import Filter.Filter.FilterParam;

public class FilterMenu extends Dialog {
    interface Callback {
        void callback(ArrayList<FilterParam<Person>> filters);
    }

    private Callback callback;
    private final ArrayList<FilterParam<Person>> initialFilters;
    
    private void init() {
        setSize(300, 200);
        addWindowListener(new DialogClose(this));
        setLayout(new GridLayout(6, 2));
        
        Checkbox byLname = new Checkbox("Last Name");
        TextField lname = new TextField();

        Checkbox byIndex = new Checkbox("Index");
        TextField index = new TextField();

        Checkbox byPosition = new Checkbox("Position");
        TextField position = new TextField();

        Checkbox byGeHIndex = new Checkbox("Minimum HIndex");
        TextField HIndex = new TextField();

        Checkbox byType = new Checkbox("Type");
        Choice type = new Choice();
        type.add("Scientist");
        type.add("Student");
        type.add("Worker");

        for(FilterParam<Person> filterParam : initialFilters) {
            if(filterParam instanceof PersonFilterByLName) {
                byLname.setState(true);
                lname.setText(((PersonFilterByLName)filterParam).getLName());
            }
            else if(filterParam instanceof PersonFilterByIndex) {
                byIndex.setState(true);
                index.setText(((PersonFilterByIndex)filterParam).getIndex());
            }
            else if(filterParam instanceof PersonFilterByPosition) {
                byPosition.setState(true);
                position.setText(((PersonFilterByPosition)filterParam).getPosition());
            }
            else if(filterParam instanceof PersonFilterByHIndexGreater) {
                byGeHIndex.setState(true);
                HIndex.setText(Integer.toString(((PersonFilterByHIndexGreater)filterParam).getLowerBound()));
            }
            else if(filterParam instanceof PersonFilterIsScientist) {
                byType.setState(true);
                type.select(0);
            }
            else if(filterParam instanceof PersonFilterIsStudent) {
                byType.setState(true);
                type.select(1);
            }
            else if(filterParam instanceof PersonFilterIsWorker) {
                byType.setState(true);
                type.select(2);
            }
        }

        Button okButton = new Button("Ok");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<FilterParam<Person>> filters = new ArrayList<>();

                if(byLname.getState()) {
                    filters.add(new PersonFilterByLName(lname.getText()));
                }

                if(byIndex.getState()) {
                    filters.add(new PersonFilterByIndex(index.getText()));
                }

                if(byPosition.getState()) {
                    filters.add(new PersonFilterByPosition(position.getText()));
                }

                if(byGeHIndex.getState()) {
                    filters.add(new PersonFilterByHIndexGreater(Integer.parseInt(HIndex.getText())));
                }

                if(byType.getState()) {
                    switch (type.getSelectedIndex()) {
                        case 0:
                            filters.add(new PersonFilterIsScientist());
                            break;
                        case 1:
                            filters.add(new PersonFilterIsStudent());
                            break;
                        case 2:
                            filters.add(new PersonFilterIsWorker());
                            break;
                    }
                }
                
                callback.callback(filters);
                setVisible(false);
            }
            
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                callback.callback(initialFilters);
                setVisible(false);
            }
            
        });

        add(byLname);
        add(lname);

        add(byIndex);
        add(index);

        add(byPosition);
        add(position);

        add(byGeHIndex);
        add(HIndex);

        add(byType);
        add(type);

        add(cancelButton);
        add(okButton);

        setVisible(true);
    }
    
    public FilterMenu(Frame owner, Callback callback, final ArrayList<FilterParam<Person>> filters) {
        super(owner);
        this.callback = callback;
        this.initialFilters = filters;
        init();
    }
    
    public FilterMenu(Dialog owner, Callback callback, final ArrayList<FilterParam<Person>> filters) {
        super(owner);
        this.callback = callback;
        this.initialFilters = filters;
        init();
    }
}
