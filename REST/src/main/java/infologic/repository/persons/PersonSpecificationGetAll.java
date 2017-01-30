package infologic.repository.persons;

import infologic.repository.Specification;


public class PersonSpecificationGetAll implements Specification {
    @Override
    public boolean specified(Object pattern) {
        return true;
    }
}
