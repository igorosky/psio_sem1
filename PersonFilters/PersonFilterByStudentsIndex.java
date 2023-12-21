package PersonFilters;

import College.People.Person;
import College.People.Student;

public class PersonFilterByStudentsIndex implements PersonFilter {

    private final String index;

    public PersonFilterByStudentsIndex(final String index) {
        this.index = index;
    }

    @Override
    public boolean isGood(final Person person) {
        return person != null && person instanceof Student && ((Student)person).getIndexNumber().equals(index);
    }
    
}
