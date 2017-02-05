/*
 *  
 */
package ru.personrank.data.generalstatistic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import ru.personrank.data.Repository;
import ru.personrank.data.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 */
public class GeneralStatisticOnSiteRepository implements Repository<GeneralStatisticOnSite> {

    private static final String URL_GET_SITE_MAP = "http://37.194.87.95:30000/statistic/getresourcelist";
    private static final String URL_GET_GENERAL_STATISTIC_ON_SITE = "http://37.194.87.95:30000/statistic/common/";
    
    private static final GeneralStatisticOnSiteRepository INSTANCE = new GeneralStatisticOnSiteRepository();

    List<GeneralStatisticOnSite> generalStatisticOnSite;

    private GeneralStatisticOnSiteRepository() {
        generalStatisticOnSite = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> entries = getSiteMap().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            generalStatisticOnSite.add(new GeneralStatisticOnSite(
                    (String) entry.getValue(),
                    getReviewDate(entry.getKey()),
                    getPersonsList(entry.getKey()),
                    getRanksList(entry.getKey())));
        }
            // Тестовые данные
//        // Начало
//        String siteName1 = "lenta.ru";
//        ArrayList<String> persons1 = new ArrayList<>();
//        persons1.add("Путин");
//        persons1.add("Трамп");
//        persons1.add("Обама");
//        persons1.add("Меркиль");
//        persons1.add("Оланд");
//        persons1.add("Навальный");
//        persons1.add("Жириновский");
//        ArrayList<Integer> allRanks1 = new ArrayList<>();
//        allRanks1.add(1227);
//        allRanks1.add(1820);
//        allRanks1.add(987);
//        allRanks1.add(681);
//        allRanks1.add(578);
//        allRanks1.add(57);
//        allRanks1.add(121);
//        generalStatisticOnSite.add(new GeneralStatisticOnSite(siteName1,new GregorianCalendar(), persons1, allRanks1));
//
//        String siteName2 = "komersant.ru";
//        ArrayList<String> persons2 = new ArrayList<>();
//        persons2.add("Путин");
//        persons2.add("Трамп");
//        persons2.add("Обама");
//        persons2.add("Меркиль");
//        persons2.add("Оланд");
//        persons2.add("Навальный");
//        persons2.add("Жириновский");
//        ArrayList<Integer> allRanks2 = new ArrayList<>();
//        allRanks2.add(1147);
//        allRanks2.add(1745);
//        allRanks2.add(1001);
//        allRanks2.add(791);
//        allRanks2.add(670);
//        allRanks2.add(100);
//        allRanks2.add(189);
//        generalStatisticOnSite.add(new GeneralStatisticOnSite(siteName2, new GregorianCalendar(), persons2, allRanks2));
//        // Конец

    }

    public static GeneralStatisticOnSiteRepository getInstance() {
        return INSTANCE;
    }

    // Метод возвращает обьект json по url адресу ресурса
    private static JSONObject getJSON(String url) {
        JSONObject json = null;
        try {
            URL source = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(source.openStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            json = new JSONObject(sb.toString());
            in.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    // Метод возвращает карту сайтов
    private static Map<String, Object> getSiteMap() {
        JSONObject json = getJSON(URL_GET_SITE_MAP);
        return json.toMap();
    }

    // Метод возвращает дату последнего обновления статистики сайта
    private static Calendar getReviewDate(String siteID) {
        JSONObject json = getJSON(URL_GET_GENERAL_STATISTIC_ON_SITE + siteID);
        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(json.getLong("date"));
        return date;
    }
    
    // Метод возвращает список персон сайта
    private static List<String> getPersonsList (String siteID) {
        List<String> persons = new ArrayList<>();
        JSONObject json = getJSON(URL_GET_GENERAL_STATISTIC_ON_SITE + siteID);
        JSONObject jsonResult = (JSONObject) json.get("result");
        for (Map.Entry<String, Object> entry : jsonResult.toMap().entrySet()) {
            persons.add((String)entry.getKey());
        }
        return persons;
    }
    
    // Метод возвращает список рейтингов персон
    private static List<Integer> getRanksList (String siteID) {
        List<Integer> ranks = new ArrayList<>();
        JSONObject json = getJSON(URL_GET_GENERAL_STATISTIC_ON_SITE + siteID);
        JSONObject jsonResult = (JSONObject) json.get("result");
        for (Map.Entry<String, Object> entry : jsonResult.toMap().entrySet()) {
            ranks.add((Integer) entry.getValue());
        }
        return ranks;
    }

    @Override
    public void add(GeneralStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(GeneralStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GeneralStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GeneralStatisticOnSite> query(Specification specification) {
        List<GeneralStatisticOnSite> newList = new ArrayList<>();
        for (GeneralStatisticOnSite gsos : generalStatisticOnSite) {
            if (specification.IsSatisfiedBy(gsos)) {
                newList.add(gsos);
            }
        }
        return newList;
    }

//    // Тестовые данные
//        // Начало
//        String siteName1 = "lenta.ru";
//        ArrayList<String> persons1 = new ArrayList<>();
//        persons1.add("Путин");
//        persons1.add("Трамп");
//        persons1.add("Обама");
//        persons1.add("Меркиль");
//        persons1.add("Оланд");
//        persons1.add("Навальный");
//        persons1.add("Жириновский");
//        ArrayList<Integer> allRanks1 = new ArrayList<>();
//        allRanks1.add(1227);
//        allRanks1.add(1820);
//        allRanks1.add(987);
//        allRanks1.add(681);
//        allRanks1.add(5787);
//        allRanks1.add(57);
//        allRanks1.add(121);
//        generalStatisticOnSite.add(new GeneralStatisticOnSite(siteName1, persons1, allRanks1));
//
//        String siteName2 = "komersant.ru";
//        ArrayList<String> persons2 = new ArrayList<>();
//        persons2.add("Путин");
//        persons2.add("Трамп");
//        persons2.add("Обама");
//        persons2.add("Меркиль");
//        persons2.add("Оланд");
//        persons2.add("Навальный");
//        persons2.add("Жириновский");
//        ArrayList<Integer> allRanks2 = new ArrayList<>();
//        allRanks2.add(1147);
//        allRanks2.add(1745);
//        allRanks2.add(1001);
//        allRanks2.add(791);
//        allRanks2.add(670);
//        allRanks2.add(100);
//        allRanks2.add(189);
//        generalStatisticOnSite.add(new GeneralStatisticOnSite(siteName2, persons2, allRanks2));
//        // Конец
//
}
