package ru.infologic.repository;

import ru.infologic.HibernateUtil;
import ru.infologic.model.Dictionary;
import ru.infologic.model.KeywordsEntity;
import ru.infologic.model.PersonsEntity;
import ru.infologic.model.SitesEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class Repository implements RepositoryInterface<Dictionary> {
    private Session session;

    @Override
    public void add(Dictionary pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remove(Dictionary pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Dictionary pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<? extends Dictionary> query(Specification specification, Object... args) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<? extends Dictionary> result = null;
        switch (specification) {
            case getPersons: {
                session.beginTransaction();
                Query<PersonsEntity> query = session.createQuery("FROM PersonsEntity", PersonsEntity.class);
                if (query != null) result = query.list();
                session.getTransaction().commit();
                session.close();
                break;
            }
            case getSites: {
                session.beginTransaction();
                Query<SitesEntity> query = session.createQuery("FROM SitesEntity", SitesEntity.class);
                if (query != null) result = query.list();
                session.getTransaction().commit();
                session.close();
                break;
            }
            case getKeywords: {
                session.beginTransaction();
                Query<KeywordsEntity> query = null;
                if (args.length == 0) query = session.createQuery("FROM KeywordsEntity", KeywordsEntity.class);
                else if (args.length == 1 && args[0] instanceof Integer)
                    query = session.createQuery("FROM KeywordsEntity ke WHERE ke.personId=:personId", KeywordsEntity.class).setParameter("personId", args[0]);
                if (query != null) result = query.list();
                session.getTransaction().commit();
                session.close();
                break;
            }
        }
        return result;
    }

}