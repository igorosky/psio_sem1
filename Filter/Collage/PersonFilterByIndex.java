package Filter.Collage;

import College.People.Person;
import College.People.Student;
import Filter.Filter.FilterParam;

public class PersonFilterByIndex implements FilterParam<Person> {

    private final String index;

    public PersonFilterByIndex(final String idx) {
        this.index = idx;
    }

    @Override
    public boolean isGood(Person person) {
        return person != null && person instanceof Student && ((Student)person).getIndexNumber().equals(index);
    }

    public String getIndex() {
        return index;
    }
    
}
