package Filter.Collage;

import College.People.Person;
import Filter.Filter.FilterParam;

public class PersonFilterByLName implements FilterParam<Person> {

    private final String lname; 
    
    public PersonFilterByLName(final String lname) {
        this.lname = lname;
    }

    @Override
    public boolean isGood(Person person) {
        return person != null && person.getLname().equals(lname);
    }

    public String getLName() {
        return lname;
    }
    
}
