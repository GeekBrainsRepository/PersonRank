package infologic.repository.keyword;

import infologic.model.KeywordsEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Антон Владимирович on 26.01.2017.
 */

public class FakeKeywordRepository implements Repository<KeywordsEntity> {

    private List<KeywordsEntity> repo = new ArrayList<>();
    private FakeKeywordRepository INSTANCE;

    private FakeKeywordRepository() {

        KeywordsEntity entity = new KeywordsEntity();
        for (int i = 0; i < 10; i++) {
            entity.setId(i);
            entity.setName("Name No." + i);
            entity.setPersonId(i);
            repo.add(entity);
        }

    }

    public FakeKeywordRepository getInstanse() {
        if (INSTANCE == null) {
            INSTANCE = new FakeKeywordRepository();
            return INSTANCE;
        } else
            return INSTANCE;
    }

    @Override
    public void add(KeywordsEntity pattern) {
        repo.add(pattern);
    }

    @Override
    public void remove(KeywordsEntity pattern) {
        repo.remove(pattern);
    }

    @Override
    public void update(KeywordsEntity pattern) {
        for (KeywordsEntity entry : repo) {
            if (entry.getId() == pattern.getId()) {
                entry.setName(pattern.getName());
                entry.setPersonId(pattern.getPersonId());
            }
        }
    }

    @Override
    public List<KeywordsEntity> query(Specification specification) {
        List<KeywordsEntity> list = new ArrayList<>();
        for (KeywordsEntity entity :
                repo) {
            if (specification.specified(entity)){
                list.add(entity);
            }
        }

        return list;
    }
}
