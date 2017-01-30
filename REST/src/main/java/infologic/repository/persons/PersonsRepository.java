package infologic.repository.persons;

import infologic.HibernateUtil;
import infologic.model.PersonsEntity;
import infologic.repository.Repository;
import infologic.repository.Specification;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lWeRl on 30.01.2017.
 */
public class PersonsRepository implements Repository<PersonsEntity> {
    private Session session;

    @Override
    public void add(PersonsEntity pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remove(PersonsEntity pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(PersonsEntity pattern) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(pattern);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<PersonsEntity> query(Specification specification) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        // а теперь начинаются чудеса на виражах которые нам нахер не сдались
        List list = session.createQuery("FROM PersonsEntity").list(); //загружаем все объекты из таблицы
        ArrayList<PersonsEntity> result = new ArrayList<>();
        for (Object o : list) { //и их все перебираем
            if (o instanceof PersonsEntity) {
                if(specification.specified(o)) result.add((PersonsEntity) o);
            }
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
