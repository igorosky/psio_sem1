package Filter.Collage;

import College.People.Person;
import College.People.Worker;
import Filter.Filter.FilterParam;

public class PersonFilterByPosition implements FilterParam<Person> {

    private final String position;

    public PersonFilterByPosition(final String position) {
        this.position = position;
    }

    @Override
    public boolean isGood(final Person person) {
        return person != null && person instanceof Worker && ((Worker)person).getPosition().equals(position);
    }

    public String getPosition() {
        return position;
    }
    
}
