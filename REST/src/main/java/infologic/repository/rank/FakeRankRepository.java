package infologic.repository.rank;

import infologic.model.PersonPageRankEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class FakeRankRepository implements Repository<PersonPageRankEntity> {
    List<PersonPageRankEntity> repo = new ArrayList<>();
    private static FakeRankRepository INSTANCE;

    private FakeRankRepository() {
        for (int i = 0; i < 10; i++) {
            PersonPageRankEntity entity = new PersonPageRankEntity();
            entity.setPageId(i);
            entity.setPersonId(i);
            entity.setRank(i+1);
            entity.setRankId(i*2);
            repo.add(entity);
        }
    }
    public FakeRankRepository getInstanse() {
        if (INSTANCE == null) {
            INSTANCE = new FakeRankRepository();
            return INSTANCE;
        } else
            return INSTANCE;
    }

    @Override
    public void add(PersonPageRankEntity pattern) {

    }

    @Override
    public void remove(PersonPageRankEntity pattern) {

    }

    @Override
    public void update(PersonPageRankEntity pattern) {

    }

    @Override
    public List<PersonPageRankEntity> query(Specification specification) {
        return null;
    }
}
