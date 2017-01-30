package infologic.repository.pages;

import infologic.model.PagesEntity;
import infologic.repository.Specification;


public class PageSpecificationBySiteID implements Specification<PagesEntity> {
    int siteId;

    public PageSpecificationBySiteID(int siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean specified(PagesEntity pattern) {
        return pattern.getSiteId()==siteId;
    }
}
