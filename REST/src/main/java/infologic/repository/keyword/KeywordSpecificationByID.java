package infologic.repository.keyword;

import infologic.model.KeywordsEntity;
import infologic.repository.Specification;


public class KeywordSpecificationByID implements Specification<KeywordsEntity> {
    private int id;
    public KeywordSpecificationByID(int id) {
        this.id = id;
    }

    @Override
    public boolean specified(KeywordsEntity pattern) {
        if (pattern.getId()==id) return true;
        else return false;
    }
}
