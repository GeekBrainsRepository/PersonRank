package infologic;


import infologic.model.*;
import infologic.repository.pages.FakePageRepository;
import infologic.repository.pages.PageSpecificationBySiteID;
import infologic.repository.persons.FakePersonRepository;
import infologic.repository.persons.PersonSpecificationGetAll;
import infologic.repository.rank.FakeRankRepository;
import infologic.repository.rank.RankSpecificationByPageID;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.*;

public class StatisticUtilities {
    public static CommonStat createFakeCommon(int siteId) {
        Map<String, Integer> result = new HashMap<>();
        //Выбираем все страницы сайта,по которым будем смотреть статистику
        List<PagesEntity> targetPages = FakePageRepository.getInstanse().query(new PageSpecificationBySiteID(siteId));

        List<PersonPageRankEntity> targetRanks = new ArrayList<>();
        //Выберем все записи рейтинга,существующие для данных страниц
        for (PagesEntity page :
                targetPages) {
            targetRanks.addAll(FakeRankRepository.getInstanse().query(new RankSpecificationByPageID(page.getId())));
        }

        //получаем список всех личностей
        List<PersonsEntity> persons = FakePersonRepository.getInstanse().query(new PersonSpecificationGetAll());
        //пройдемся по записям рейтинга суммируя рейтинг для каждой персоны
        for (PersonsEntity person :
                persons) {
            int rank = 0;
            for (PersonPageRankEntity personRankEntry : targetRanks) {
                if (personRankEntry.getPersonId() == person.getId()) {
                    rank += personRankEntry.getRank();
                }
            }
            result.put(person.getName(), rank);
        }
        //Найдем последнюю дату обновления

        //берем любую дату из списка
        Timestamp lastDate = targetPages.get(0).getLastScanDate();

        for (PagesEntity page :
                targetPages) {
            //сравниваем все остальные с ней
            if (page.getLastScanDate().after(lastDate)) {
                //Если после - сохраняем
                lastDate = page.getLastScanDate();
            }
        }
        CommonStat statistic = new CommonStat(lastDate, result);
        return statistic;
    }

    public static CommonStat createCommon(int siteId) {
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
        return new CommonStat(date, result);
    }

    public static DailyStat createDaily(int siteId, int personId, long startDate, long endDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT personPageRank.rank, page.foundDataTime FROM PersonPageRankEntity AS personPageRank "
                + "INNER JOIN personPageRank.pagesByPageId AS page "
                + "INNER JOIN personPageRank.personsByPersonId AS person WHERE "
                + "person.id = :personId "
                + "AND page.siteId = :siteId "//);
                + "AND page.foundDataTime BETWEEN :dateStart AND :dateEnd");
        query.setParameter("personId", personId);
        query.setParameter("siteId", siteId);
        query.setParameter("dateStart", new Date(startDate));
        query.setParameter("dateEnd", new Date(endDate));
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
        return new DailyStat(result);
    }

    public static Map<Integer, String> getSites() {
        ArrayList<SitesEntity> undoSites;
        Map<Integer, String> newSites = new HashMap<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM SitesEntity");
        undoSites = (ArrayList<SitesEntity>) query.list();
        for (int i = 0; i < undoSites.size(); i++) {
            newSites.put(undoSites.get(i).getId(), undoSites.get(i).getName());
        }
        session.getTransaction().commit();
        session.close();
        return newSites;
    }
    public static Map<Integer, String> getPersons() {
        ArrayList<PersonsEntity> undoPersons;
        Map<Integer, String> newpersons = new HashMap<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM PersonsEntity");
        undoPersons = (ArrayList<PersonsEntity>) query.list();
        for (int i = 0; i < undoPersons.size(); i++) {
            newpersons.put(undoPersons.get(i).getId(), undoPersons.get(i).getName());
        }
        session.getTransaction().commit();
        session.close();
        return newpersons;
    }
}