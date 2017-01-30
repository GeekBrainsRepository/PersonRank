package infologic.repository.pages;

import infologic.model.PagesEntity;
import infologic.repository.Specification;

/**
 * Created by Антон Владимирович on 29.01.2017.
 */
public class PageSpecificationByID implements Specification<PagesEntity> {
    int id;
    public PageSpecificationByID(int desiredId) {
        id=desiredId;
    }

    @Override
    public boolean specified(PagesEntity pattern) {
        return pattern.getId()==id;
    }
}
