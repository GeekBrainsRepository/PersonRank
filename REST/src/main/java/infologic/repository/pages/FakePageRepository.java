package infologic.repository.pages;

import infologic.model.PagesEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class FakePageRepository implements Repository<PagesEntity> {
    List<PagesEntity> repo = new ArrayList<>();
    private static FakePageRepository INSTANCE;

    private FakePageRepository() {
        for (int i = 0; i < 10; i++) {
            PagesEntity entity = new PagesEntity();
            entity.setId(i);
            entity.setSiteId(1);
            entity.setUrl("http://site" + i + ".ru");
            entity.setFoundDataTime(new Timestamp(2000000 + i));
            entity.setLastScanDate(new Timestamp(2000000 + 2 * i));
            repo.add(entity);
        }
    }

    public static FakePageRepository getInstanse() {
        if (INSTANCE == null) {
            INSTANCE = new FakePageRepository();
            return INSTANCE;
        } else
            return INSTANCE;
    }

    @Override
    public void add(PagesEntity pattern) {
        repo.add(pattern);
    }

    @Override
    public void remove(PagesEntity pattern) {
        repo.remove(pattern);
    }

    @Override
    public void update(PagesEntity pattern) {
        for (PagesEntity entity :
                repo) {
            if (entity.getId() == pattern.getId()) {
                entity.setUrl(pattern.getUrl());
            }
        }
    }

    @Override
    public List<PagesEntity> query(Specification specification) {
        List<PagesEntity> list = new ArrayList<>();
        for (PagesEntity entity :
                repo) {
            if (specification.specified(entity)) {
                list.add(entity);
            }


        }
        return list;
    }
}
