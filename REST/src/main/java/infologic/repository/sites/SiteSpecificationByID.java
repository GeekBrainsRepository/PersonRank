package infologic.repository.sites;

import infologic.model.SitesEntity;
import infologic.repository.Specification;


public class SiteSpecificationByID implements Specification<SitesEntity> {
    int id;

    public SiteSpecificationByID(int id) {
        this.id = id;
    }

    @Override
    public boolean specified(SitesEntity pattern) {
        return pattern.getId()==id;
    }
}
