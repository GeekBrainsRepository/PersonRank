package infologic.repository.persons;

import infologic.model.PersonsEntity;
import infologic.repository.Specification;


public class PersonSpecificationByID implements Specification<PersonsEntity> {
    int id;

    public PersonSpecificationByID(int id) {
        this.id = id;
    }

    @Override
    public boolean specified(PersonsEntity pattern) {
        return pattern.getId()==id;
    }
}
