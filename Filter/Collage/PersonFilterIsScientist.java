package Filter.Collage;

import College.People.Person;
import College.People.Scientist;
import Filter.Filter.FilterParam;

public class PersonFilterIsScientist implements FilterParam<Person> {

    @Override
    public boolean isGood(Person item) {
        return item != null && item instanceof Scientist;
    }
    
}
