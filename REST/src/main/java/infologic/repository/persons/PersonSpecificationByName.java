package infologic.repository.persons;

import infologic.model.PersonsEntity;
import infologic.repository.Specification;

/**
 * Created by lWeRl on 30.01.2017.
 */
public class PersonSpecificationByName implements Specification<PersonsEntity> {
    private String name;

    public PersonSpecificationByName(String name) {
        this.name = name;
    }

    @Override
    public boolean specified(PersonsEntity pattern) {
        return pattern.getName().equals(name);
    }
}
