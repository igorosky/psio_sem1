package Filter.Collage;

import College.People.Person;
import College.People.Worker;
import Filter.Filter.FilterParam;

public class PersonFilterIsWorker implements FilterParam<Person> {

    @Override
    public boolean isGood(Person item) {
        return item != null && item instanceof Worker;
    }
    
}
