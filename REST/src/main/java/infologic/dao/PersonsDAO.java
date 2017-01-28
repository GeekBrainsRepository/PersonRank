package infologic.dao;

import java.util.Collection;

import org.hibernate.Session;

import infologic.model.PersonsEntity;

public class PersonsDAO implements PersonsDAOInterface {

	@Override
	public Collection<PersonsEntity> getAllPerson() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Collection<PersonsEntity> result = session.createQuery("FROM PersonsEntity").list();
		session.close();
		return result;
	}

}
