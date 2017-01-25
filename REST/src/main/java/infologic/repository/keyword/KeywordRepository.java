package infologic.repository.keyword;

import infologic.model.KeywordsEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;

import java.util.List;

/**
 * Created by Антон Владимирович on 26.01.2017.
 */
public class KeywordRepository implements Repository<KeywordsEntity> {
    @Override
    public void add(KeywordsEntity pattern) {
        //Тут должны быть запросы к DAO
    }

    @Override
    public void remove(KeywordsEntity pattern) {

    }

    @Override
    public void update(KeywordsEntity pattern) {

    }

    @Override
    public List<KeywordsEntity> query(Specification specification) {
        return null;
    }
}
