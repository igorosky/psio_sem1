package Backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import College.People.Person;
import Filter.Filter;
import Filter.Filter.FilterParam;
import ObjectSaver.ObjectSaver;

public class Backend {
    private ArrayList<Person> people;
    private ArrayList<Person> displayedPeople;

    
    public Backend(ArrayList<Person> peopleList) {
        people = peopleList;
        displayedPeople = new ArrayList<>();
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
}
