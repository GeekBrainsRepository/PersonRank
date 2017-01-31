package infologic.repository;

import infologic.HibernateUtil;
import infologic.model.Book;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by lWeRl on 31.01.2017.
 */
public class BookRepository implements Repository<Book> {
    private Session session;

    @Override
    public void add(Book pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remove(Book pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Book pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Book> query(Specification specification) {
        throw new UnsupportedOperationException();
    }
}