package PersonFilters;

import College.People.Person;
import College.People.Worker;

public class PersonFilterByPosition implements PersonFilter {

    private final String position;

    public PersonFilterByPosition(final String position) {
        this.position = position;
    }

    @Override
    public boolean isGood(final Person person) {
        return person != null && person instanceof Worker && ((Worker)person).getPosition().equals(position);
    }
    
}
