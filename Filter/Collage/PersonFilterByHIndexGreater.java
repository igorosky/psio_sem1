package Filter.Collage;

import College.People.Person;
import College.People.Scientist;
import Filter.Filter.FilterParam;

public class PersonFilterByHIndexGreater implements FilterParam<Person> {

    private final int lowerBound;

    public PersonFilterByHIndexGreater(final int lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public boolean isGood(final Person person) {
        return person != null && person instanceof Scientist && ((Scientist)person).gethIndex() > lowerBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }
    
}
