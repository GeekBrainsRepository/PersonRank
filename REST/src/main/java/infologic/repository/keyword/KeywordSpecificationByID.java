package infologic.repository.keyword;

import infologic.model.KeywordsEntity;
import infologic.repository.Specification;

/**
 * Created by Антон Владимирович on 26.01.2017.
 */
public class KeywordSpecificationByID implements Specification<KeywordsEntity> {
    @Override
    public boolean specified(KeywordsEntity pattern) {
        return false;
    }
}
