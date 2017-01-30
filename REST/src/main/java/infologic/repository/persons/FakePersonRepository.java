package infologic.repository.persons;

import infologic.model.PersonsEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Антон Владимирович on 29.01.2017.
 */
public class FakePersonRepository implements Repository<PersonsEntity>{
    private List<PersonsEntity> repo = new ArrayList<>();
    private static FakePersonRepository INSTANCE;

    private FakePersonRepository() {
        for (int i = 0; i < 10; i++) {
            PersonsEntity entity = new PersonsEntity();
             entity.setId(i);
            entity.setName("Name No."+i);
            repo.add(entity);
        }
    }

    public FakePersonRepository getInstanse() {
        if (INSTANCE == null) {
            INSTANCE = new FakePersonRepository();
            return INSTANCE;
        } else
            return INSTANCE;
    }

    @Override
    public void add(PersonsEntity pattern) {
        repo.add(pattern);
    }

    @Override
    public void remove(PersonsEntity pattern) {
        repo.remove(pattern);
    }

    @Override
    public void update(PersonsEntity pattern) {
        for (PersonsEntity entity :
                repo) {
            if (entity.getId() == pattern.getId()) {
                entity.setName(pattern.getName());
            }
        }
    }

    @Override
    public List<PersonsEntity> query(Specification specification) {
        List<PersonsEntity> list = new ArrayList<>();
        for (PersonsEntity entity : repo) {
            if (specification.specified(entity)){
                list.add(entity);
            }
        }
        return list;
    }
}
