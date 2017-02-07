/*
 *  
 */
package ru.personrank.data.generalstatistic;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import ru.personrank.data.UpdatingRepositoryEvent;
import ru.personrank.data.UpdatingRepositoryListener;
import ru.personrank.view.Window;

/**
 *
 */
public class GeneralStatisticOnSiteRepository implements Repository<GeneralStatisticOnSite> {
    
    private static final long FREQUENCY_OF_UPDATES_REPOSITORY = 10; //в секундах
    private static final String URL_GET_SITE_MAP = "http://37.194.87.95:30000/statistic/getresourcelist";
    private static final String URL_GET_GENERAL_STATISTIC_ON_SITE = "http://37.194.87.95:30000/statistic/common/";

    private static final GeneralStatisticOnSiteRepository INSTANCE = new GeneralStatisticOnSiteRepository();
    
    private List listenerList;
    private List<GeneralStatisticOnSite> generalStatisticOnSite;

    private GeneralStatisticOnSiteRepository() {
        generalStatisticOnSite = load();
        listenerList = new ArrayList();
        new MakerGeneralStatistic();
    }

    public static GeneralStatisticOnSiteRepository getInstance() {
        return INSTANCE;
    }
    
    public void addUpdatingRepositoryListener(UpdatingRepositoryListener listener) {
        listenerList.add(listener);
    }
    
    public void removeUpdatingRepositoryListener(UpdatingRepositoryListener listener) {
        listenerList.remove(listener);
    }
    
    private void fireUpdatingRepositoryEvent () {
        UpdatingRepositoryEvent event = new UpdatingRepositoryEvent(INSTANCE);
        for (Object listener : listenerList) {
            if(listener instanceof UpdatingRepositoryListener) {
                ((UpdatingRepositoryListener) listener).repositoryUpdated(event);
            }
        }
    }
    
    private void save(List<GeneralStatisticOnSite> statistic) {
        try (ObjectOutputStream objOStrm = new ObjectOutputStream(new FileOutputStream("General_statistic.dp"))) {
            objOStrm.writeObject(statistic);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private List<GeneralStatisticOnSite> load() {
        try (ObjectInputStream objIStr = new ObjectInputStream(new FileInputStream("General_statistic.dp"))) {
            List<GeneralStatisticOnSite> list = (List<GeneralStatisticOnSite>) objIStr.readObject();
            return list;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
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
    private static List<String> getPersonsList(String siteID) {
        List<String> persons = new ArrayList<>();
        JSONObject json = getJSON(URL_GET_GENERAL_STATISTIC_ON_SITE + siteID);
        JSONObject jsonResult = (JSONObject) json.get("result");
        for (Map.Entry<String, Object> entry : jsonResult.toMap().entrySet()) {
            persons.add((String) entry.getKey());
        }
        return persons;
    }

    // Метод возвращает список рейтингов персон
    private static List<Integer> getRanksList(String siteID) {
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
    
    // Тестовый метод !!!Удалить
    private static List <GeneralStatisticOnSite> getNewStatistic () {
        List<GeneralStatisticOnSite> newStatistic = new ArrayList<>();
        String siteName1 = "lenta.ru";
        ArrayList<String> persons1 = new ArrayList<>();
        persons1.add("Путин");
        persons1.add("Трамп");
        persons1.add("Обама");
        persons1.add("Меркиль");
        persons1.add("Оланд");
        persons1.add("Навальный");
        persons1.add("Жириновский");
        ArrayList<Integer> allRanks1 = new ArrayList<>();
        allRanks1.add(2000);
        allRanks1.add(1820);
        allRanks1.add(987);
        allRanks1.add(681);
        allRanks1.add(578);
        allRanks1.add(57);
        allRanks1.add(121);
        newStatistic.add(new GeneralStatisticOnSite(siteName1, new GregorianCalendar(2017,Calendar.FEBRUARY,5), persons1, allRanks1));

        String siteName2 = "komersant.ru";
        ArrayList<String> persons2 = new ArrayList<>();
        persons2.add("Путин");
        persons2.add("Трамп");
        persons2.add("Обама");
        persons2.add("Меркиль");
        persons2.add("Оланд");
        persons2.add("Навальный");
        persons2.add("Жириновский");
        ArrayList<Integer> allRanks2 = new ArrayList<>();
        allRanks2.add(1147);
        allRanks2.add(1745);
        allRanks2.add(1001);
        allRanks2.add(791);
        allRanks2.add(670);
        allRanks2.add(100);
        allRanks2.add(189);
        newStatistic.add(new GeneralStatisticOnSite(siteName2, new GregorianCalendar(2017,Calendar.FEBRUARY,5), persons2, allRanks2));
        
        String siteName3 = "News.com";
        ArrayList<String> persons3 = new ArrayList<>();
        persons3.add("Путин");
        persons3.add("Трамп");
        persons3.add("Обама");
        persons3.add("Меркиль");
        persons3.add("Оланд");
        persons3.add("Навальный");
        persons3.add("Жириновский");
        ArrayList<Integer> allRanks3 = new ArrayList<>();
        allRanks3.add(1570);
        allRanks3.add(1321);
        allRanks3.add(789);
        allRanks3.add(732);
        allRanks3.add(612);
        allRanks3.add(211);
        allRanks3.add(435);
        newStatistic.add(new GeneralStatisticOnSite(siteName3, new GregorianCalendar(2017,Calendar.FEBRUARY,7), persons3, allRanks3));
        
        return newStatistic;
    }
    
    private class MakerGeneralStatistic implements Runnable {
        
        private Thread thread;

        public MakerGeneralStatistic() {
            thread = new Thread(this, "Maker general statistic");
            thread.setDaemon(true);
            thread.start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(FREQUENCY_OF_UPDATES_REPOSITORY * 1000);
                    System.out.println("Обновление статистики!");
                    List<GeneralStatisticOnSite> newStatistic = new ArrayList<>();
                    newStatistic = getNewStatistic();
//                    Iterator<Map.Entry<String, Object>> entries = getSiteMap().entrySet().iterator();
//                    while (entries.hasNext()) {
//                        Map.Entry<String, Object> entry = entries.next();
//                        newStatistic.add(new GeneralStatisticOnSite(
//                                (String) entry.getValue(),
//                                getReviewDate(entry.getKey()),
//                                getPersonsList(entry.getKey()),
//                                getRanksList(entry.getKey())));
//                    }
                    if(!generalStatisticOnSite.equals(newStatistic) && !newStatistic.isEmpty()) {
                        generalStatisticOnSite = newStatistic;
                        fireUpdatingRepositoryEvent();
                        System.out.println("Статистика обновлена!");
                        save(newStatistic);
                    } else {
                        System.out.println("Нет изменений в базе!");
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

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
