package PersonFilters;

import College.People.Person;

public class PersonFilterByLName implements PersonFilter {

    private final String lname; 
    
    public PersonFilterByLName(final String lname) {
        this.lname = lname;
    }

    @Override
    public boolean isGood(Person person) {
        return person != null && person.getLname().equals(lname);
    }
    
}