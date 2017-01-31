package infologic.repository;

import infologic.HibernateUtil;
import infologic.model.Dictionary;
import org.hibernate.Session;

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
    public List<Dictionary> query(Specification specification) {
        throw new UnsupportedOperationException();
    }
}