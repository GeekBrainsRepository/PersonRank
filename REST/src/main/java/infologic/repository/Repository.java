package infologic.repository;

import infologic.HibernateUtil;
import infologic.model.Dictionary;
import infologic.model.KeywordsEntity;
import infologic.model.PersonsEntity;
import infologic.model.SitesEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by lWeRl on 31.01.2017.
 */
public class Repository implements RepositoryInterface<Dictionary> {
    private Session session;

    @Override
    public void add(Dictionary pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(pattern);
        closeSession(session);
    }

    @Override
    public void remove(Dictionary pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(pattern);
        closeSession(session);
    }

    @Override
    public void update(Dictionary pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(pattern);
        closeSession(session);
    }

    @Override
    public List<? extends Dictionary> query(Specification specification, Object... args) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<? extends Dictionary> result = null;
        switch (specification) {
            case getPersons: {
                Query<PersonsEntity> query = session.createQuery("FROM PersonsEntity", PersonsEntity.class);
                if (query != null) result = query.list();
                break;
            }
            case getSites: {
                Query<SitesEntity> query = session.createQuery("FROM SitesEntity", SitesEntity.class);
                if (query != null) result = query.list();
                break;
            }
            case getKeywords: {
                Query<KeywordsEntity> query = null;
                if (args.length == 0) query = session.createQuery("FROM KeywordsEntity", KeywordsEntity.class);
                else if (args.length == 1 && args[0] instanceof Integer)
                    query = session.createQuery("FROM KeywordsEntity ke WHERE ke.personId=:personId", KeywordsEntity.class).setParameter("personId", args[0]);
                if (query != null) result = query.list();
                break;
            }
        }
        return result;
    }

    private void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}