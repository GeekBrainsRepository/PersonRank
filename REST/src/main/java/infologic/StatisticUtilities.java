package infologic;


import infologic.model.*;
import infologic.repository.RepositoryInterface;
import infologic.repository.Specification;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

public class StatisticUtilities {

    public static Common createCommon(int siteId) {
        Map<String, Integer> result = new HashMap<>();
        Date date = new Date(0l);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT pe.name, ppr.rank, p.lastScanDate FROM PersonsEntity as pe INNER JOIN pe.personPageRanksById as ppr "
                + "INNER JOIN ppr.pagesByPageId as p "
                + "INNER JOIN p.sitesBySiteId as s "
                + "WHERE s.id = :id");
        query.setParameter("id", siteId);
        for (Object o : query.list()) {
            Object[] row = (Object[]) o;
            String name = (String) row[0];
            int rank = (int) row[1];
            if (((Date) row[2]).after(date)) date = (Date) row[2];
            if (result.containsKey(name)) result.put(name, result.get(name) + rank);
            else result.put(name, rank);
        }
        session.getTransaction().commit();
        session.close();
        return new Common(date, result);
    }

    public static Daily createDaily(int siteId, int personId, long startDate, long endDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT personPageRank.rank, page.foundDataTime FROM PersonPageRankEntity AS personPageRank "
                + "INNER JOIN personPageRank.pagesByPageId AS page "
                + "INNER JOIN personPageRank.personsByPersonId AS person WHERE "
                + "person.id = :personId "
                + "AND page.siteId = :siteId "//);
                + "AND page.foundDataTime BETWEEN :dateStart AND :dateEnd");
        query.setParameter("personId", personId).setParameter("siteId", siteId).setParameter("dateStart", new Date(startDate)).setParameter("dateEnd", new Date(endDate));
        final long day = 86400000l;
        List<Integer> result = new ArrayList<>();
        int count = (int) ((endDate - startDate) / day);
        for (int i = 0; i <= count; i++) {
            result.add(0);
        }
        for (Object o : query.list()) {
            Object[] row = (Object[]) o;
            int rank = (int) row[0];
            int index = (int) ((((Date) row[1]).getTime() - startDate) / day);
            result.set(index, result.get(index) + rank);
        }
        session.getTransaction().commit();
        session.close();
        return new Daily(result);
    }

    public static Map<Integer, String> createPersons(RepositoryInterface repository) {
        Map<Integer, String> result = new HashMap<>();
        for (Object e : repository.query(Specification.getPersons)) {
            PersonsEntity person = (PersonsEntity) e;
            result.put(person.getId(), person.getName());
        }
        return result;
    }

    public static Map<Integer, String> createSites(RepositoryInterface repository) {
        Map<Integer, String> result = new HashMap<>();
        for (Object e : repository.query(Specification.getSites)) {
            SitesEntity site = (SitesEntity) e;
            result.put(site.getId(), site.getName());
        }
        return result;
    }

    public static Map<Integer, String> createKeyword(RepositoryInterface repository, int personId) {
        Map<Integer, String> result = new HashMap<>();
        for (Object e : repository.query(Specification.getKeywords, personId)) {
            KeywordsEntity keyword = (KeywordsEntity) e;
            result.put(keyword.getId(), keyword.getName());
        }
        return result;
    }

    public static String authenticationUsers(String login, String password){
        ArrayList<UsersEntity> result = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from UsersEntity as u where u.login = :login and u.password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        result = (ArrayList<UsersEntity>) query.list();
        session.getTransaction().commit();
        session.close();

        if (login.equals(result.get(0).getLogin()) & password.equals(result.get(0).getPassword())){
            return "true";
        }else{
            return "false";
        }
    }
}
