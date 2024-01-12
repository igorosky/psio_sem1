package Filter;

import College.People.Person;
import College.People.Student;

public class PersonFilterByIndex implements Filter.FilterParam<Person> {
    private final String idx;

    public PersonFilterByIndex(final String idx) {
        this.idx = idx;
    }

    @Override
    public boolean isGood(Person person) {
        return person != null && person instanceof Student && ((Student)person).getIndexNumber().equals(idx);
    }
    
}
