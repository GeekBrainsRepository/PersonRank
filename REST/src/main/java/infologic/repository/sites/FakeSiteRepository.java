package infologic.repository.sites;

import infologic.model.SitesEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;

import java.util.ArrayList;
import java.util.List;


public class FakeSiteRepository implements Repository<SitesEntity> {

    List<SitesEntity> repo = new ArrayList<>();
    private static FakeSiteRepository INSTANCE;

    private FakeSiteRepository() {
        for (int i = 0; i < 10; i++) {
            SitesEntity entity = new SitesEntity();
            entity.setId(i);
            entity.setName("http://site"+i+".ru");
            repo.add(entity);
        }
    }
    public FakeSiteRepository getInstanse() {
        if (INSTANCE == null) {
            INSTANCE = new FakeSiteRepository();
            return INSTANCE;
        } else
            return INSTANCE;
    }
    @Override
    public void add(SitesEntity pattern) {

    }

    @Override
    public void remove(SitesEntity pattern) {

    }

    @Override
    public void update(SitesEntity pattern) {

    }

    @Override
    public List<SitesEntity> query(Specification specification) {
        List<SitesEntity> list = new ArrayList<>();
        for (SitesEntity entity :
                repo) {
            if (specification.specified(entity)){
                list.add(entity);
            }
        }

        return list;
    }
}
