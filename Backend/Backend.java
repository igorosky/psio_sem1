package Backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import College.People.Employee;
import College.People.Person;
import College.People.Student;
import Filter.Filter;
import Filter.Filter.FilterParam;
import ObjectSaver.ObjectSaver;

public class Backend {
    private ArrayList<Person> people;
    private ArrayList<Person> displayedPeople;
    private Wage wage;
    private Stipend stipend;

    
    public Backend(final ArrayList<Person> peopleList, final Wage wage, final Stipend stipend) {
        people = peopleList;
        displayedPeople = new ArrayList<>();
        this.wage = wage;
        this.stipend = stipend;
    }

    public void save(File file) {
        ObjectSaver.serialize(people, file);
    }

    public void load(File file) {
        Optional<ArrayList<Person>> result = ObjectSaver.deserialize(file);
        if(!result.isPresent()) {
            // TODO
            System.out.println("Not found");
            return;
        }
        people = result.get();
    }

    public ArrayList<Person> displayPeople(ArrayList<FilterParam<Person>> filters) {
        displayedPeople.clear();
        Filter<Person> filter = new Filter<>();
        for (Person person : filter.filter(people, filters)) {
            displayedPeople.add(person);
        }
        return displayedPeople;
    }

    public ArrayList<Person> getCurrentlyDisplayedPeople() {
        return displayedPeople;
    }

    public void addPerson(Person person) {
        people.add(person);
        people.sort(new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.compareTo(o2);
            }
            
        });
    }

    public void removeDisplayedPerson(int i) {
        people.remove(displayedPeople.get(i));
        displayedPeople.remove(i);
        people.sort(new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.compareTo(o2);
            }
            
        });
    }

    public float getSalary(final Employee employee) {
        return wage.wage(employee);
    }

    public float getStipend(Student student) {
        return stipend.stipend(student);
    }

    public Wage getWage() {
        return wage;
    }

    public Stipend getStipend() {
        return stipend;
    }

    public void setStipend(Stipend stipend) {
        this.stipend = stipend;
    }

    public void setWage(Wage wage) {
        this.wage = wage;
    }
}
