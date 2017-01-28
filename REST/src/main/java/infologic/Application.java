package infologic;

import infologic.model.SitesEntity;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

        /**
         *
         *  тестовый запрос для проверки работы Hibernate
         */

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        SitesEntity sitesEntity = new SitesEntity();
        sitesEntity.setName("amazon.com");
        session.save(sitesEntity);
        session.getTransaction().commit();

    }
}
