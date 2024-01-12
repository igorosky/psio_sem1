package Filter.Collage;

import College.People.Person;
import College.People.Student;
import Filter.Filter.FilterParam;

public class PersonFilterIsStudent implements FilterParam<Person> {

    @Override
    public boolean isGood(Person item) {
        return item != null && item instanceof Student;
    }
    
}
