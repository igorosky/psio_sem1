package Test.Filter;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import College.Department;
import College.DepartmentNotExistException;
import College.People.Person;
import College.People.Scientist;
import College.People.Student;
import College.People.Worker;
import Filter.Filter;
import Filter.Collage.PersonFilterByHIndexGreater;
import Filter.Collage.PersonFilterByIndex;
import Filter.Collage.PersonFilterByLName;
import Filter.Collage.PersonFilterByPosition;
import Filter.Collage.PersonFilterIsScientist;
import Filter.Collage.PersonFilterIsStudent;
import Filter.Collage.PersonFilterIsWorker;

public class FilterTest {
    private Filter<Person> filter;
    private ArrayList<Person> people;
    private Random rand;
    
    @Before
    public void Init() {
        filter = new Filter<Person>();
        rand = new Random(System.currentTimeMillis());
        people = new ArrayList<>();
        for(int i = 0; i < 100; ++i) {
            people.add(new Student(Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100))));
        }
        for(int i = 0; i < 100; ++i) {
            try {
                people.add(new Worker(Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100)), Department.getDepartment(rand.nextInt(14) + 1), rand.nextBoolean(), rand.nextBoolean(), Integer.toString(rand.nextInt(100))));
            }
            catch(DepartmentNotExistException e) {
                // Infallible
            }
        }
        for(int i = 0; i < 100; ++i) {
            try {
                people.add(new Scientist(Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100)), Integer.toString(rand.nextInt(100)), Department.getDepartment(rand.nextInt(14) + 1), rand.nextInt(100000)));
            }
            catch(DepartmentNotExistException e) {
                // Infallible
            }
        }
    }

    @Test
    public void PersonByHIndexGreaterRandomTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            final int lowerBound = rand.nextInt(100000);
            filters.add(new PersonFilterByHIndexGreater(lowerBound));
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person instanceof Scientist && ((Scientist)person).gethIndex() > lowerBound);
            }
        }
    }

    @Test
    public void PersonFilterByIndexTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            final String index = Integer.toString(rand.nextInt(100));
            filters.add(new PersonFilterByIndex(index));
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person instanceof Student && ((Student)person).getIndexNumber().equals(index));
            }
        }
    }

    @Test
    public void PersonFilterByLNameTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            final String name = Integer.toString(rand.nextInt(100));
            filters.add(new PersonFilterByLName(name));
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person.getLname().equals(name));
            }
        }
    }

    @Test
    public void PersonFilterByPositionTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            final String position = Integer.toString(rand.nextInt(100));
            filters.add(new PersonFilterByPosition(position));
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person instanceof Worker && ((Worker)person).getPosition().equals(position));
            }
        }
    }

    @Test
    public void PersonFilterIsScientistTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            filters.add(new PersonFilterIsScientist());
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person instanceof Scientist);
            }
        }
    }

    @Test
    public void PersonFilterIsStudentTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            filters.add(new PersonFilterIsStudent());
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person instanceof Student);
            }
        }
    }

    @Test
    public void PersonFilterIsWorkerTest() {
        final ArrayList<Filter.FilterParam<Person>> filters = new ArrayList<>();
        for(int i = 0; i < 1000; ++i) {
            filters.clear();
            filters.add(new PersonFilterIsWorker());
            for(final Person person : filter.filter(people, filters)) {
                assertTrue(person instanceof Worker);
            }
        }
    }
}
