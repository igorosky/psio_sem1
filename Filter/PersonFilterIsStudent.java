package Filter;

import College.People.Person;
import College.People.Student;

public class PersonFilterIsStudent implements Filter.FilterParam<Person> {

    @Override
    public boolean isGood(final Person person) {
        return person != null && person instanceof Student;
    }
    
}
