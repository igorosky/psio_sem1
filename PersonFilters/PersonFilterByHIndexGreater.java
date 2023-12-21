package PersonFilters;

import College.People.Person;
import College.People.Scientist;

public class PersonFilterByHIndexGreater implements PersonFilter {

    private final int lowerBound;

    public PersonFilterByHIndexGreater(final int lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public boolean isGood(final Person person) {
        return person != null && person instanceof Scientist && ((Scientist)person).gethIndex() > lowerBound;
    }
    
}
