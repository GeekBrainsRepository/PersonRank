package infologic.repository.rank;

import infologic.model.PersonPageRankEntity;
import infologic.repository.Specification;

/**
 * Created by Антон Владимирович on 29.01.2017.
 */
public class RankSpecificationBySiteID implements Specification<PersonPageRankEntity> {
    int pageId;

    public RankSpecificationBySiteID(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public boolean specified(PersonPageRankEntity pattern) {
        return pattern.getPageId()== pageId;
    }
}
