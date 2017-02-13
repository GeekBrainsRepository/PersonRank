/*
 *  
 */
package ru.personrank.data.generalstatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

import org.json.JSONObject;
import ru.personrank.data.Repository;
import ru.personrank.data.Specification;
import ru.personrank.data.UpdatingRepositoryEvent;
import ru.personrank.data.UpdatingRepositoryListener;
import ru.personrank.view.Window;

/**
 * Класс служит для хранения данных таблицы "Общая статистика" получаемых от
 * сервера.
 *
 * Класс реализует паттерн "Репозиторий". Хранит обьекты класса
 * <b>GeneralStatisticOnSite</b> в виде списка. Делает переодические запросы к
 * веб-сервису для обновления данных, при удачном запросе обнавляет данные в
 * списке и записывает их файл. Если сервер недоступен при создании обьекта в
 * список загружаются данные из файла.
 *
 * @author Мартынов Евгений
 * @version 1.0
 */
public class GeneralStatisticOnSiteRepository implements Repository<GeneralStatisticOnSite> {

    private static final long FREQUENCY_UPDATES = 60; //в секундах
    private static final String URL_SITE = "http://37.194.87.95:30000/site";
    private static final String URL_GENERAL_STATISTIC = "http://37.194.87.95:30000/common/";
    private static final GeneralStatisticOnSiteRepository INSTANCE = new GeneralStatisticOnSiteRepository();

    private List listenerList;
    private List<GeneralStatisticOnSite> generalStatisticOnSite;

    /**
     * Создает новый обьект класса <b>GeneralStatisticOnSiteRepository</b>
     */
    private GeneralStatisticOnSiteRepository() {
        listenerList = new ArrayList();
        generalStatisticOnSite = load();
        if (generalStatisticOnSite.isEmpty()) {
           generalStatisticOnSite = updateStatistic();
        }
        new MakerGeneralStatistic();
    }

    /**
     * Возвращает обьект класса.
     *
     * @return обьект GeneralStatisticOnSiteRepository
     */
    public static GeneralStatisticOnSiteRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Добавляет одного слушателя репозитория.
     *
     * @param listener
     */
    public void addUpdatingRepositoryListener(UpdatingRepositoryListener listener) {
        listenerList.add(listener);
    }

    /**
     * Удаляет одного слушателя репозитория.
     *
     * @param listener
     */
    public void removeUpdatingRepositoryListener(UpdatingRepositoryListener listener) {
        listenerList.remove(listener);
    }

    /**
     * Оповещает всех лушателей о происшествии события
     */
    private void fireUpdatingRepositoryEvent() {
        UpdatingRepositoryEvent event = new UpdatingRepositoryEvent(INSTANCE);
        for (Object listener : listenerList) {
            if (listener instanceof UpdatingRepositoryListener) {
                ((UpdatingRepositoryListener) listener).repositoryUpdated(event);
            }
        }
    }

    /**
     * Сохраняет список обьектов <b>GeneralStatisticOnSite</b> в файл.
     *
     * Сохраняет список сайтов, переданный в качестве аргумента, в файл.
     *
     * @param statistic - список элементов статистики в виде коллекции List
     */
    private void save(List<GeneralStatisticOnSite> statistic) {
        try (ObjectOutputStream objOStrm = new ObjectOutputStream(
                new FileOutputStream("General_statistic.dp"))) {
            objOStrm.writeObject(statistic);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Загружает сохраненную статистику из файла.
     *
     * @return список элементов статистики в виде коллекции List
     */
    private List<GeneralStatisticOnSite> load() {
        File file = new File("General_statistic.dp");
        if (file.exists()) {

            if (file.length() == 0) {
                return new ArrayList<>();
            }

            try (ObjectInputStream objIStr = new ObjectInputStream(new FileInputStream(file))) {
                List<GeneralStatisticOnSite> list = (List<GeneralStatisticOnSite>) objIStr.readObject();
                return list;
            } catch (FileNotFoundException ex) {
                // Возникновение исключения маловероятно,
                // потому что предварительно проверяется наличае файла, 
                // и в случае его отсутствия создается новый.
                ex.printStackTrace();
                return new ArrayList<>();
            } catch (IOException ex) {
                ex.printStackTrace();
                return new ArrayList<>();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            try {
                file.createNewFile();
                return new ArrayList<>();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return new ArrayList<>();
    }

    /**
     * Собирает обновленный список элементов общей статистики 
     *
     * @return список элементов статистики в виде коллекции List
     */
    private List<GeneralStatisticOnSite> updateStatistic() {
        List<GeneralStatisticOnSite> list = new ArrayList<>();
        // На случай недоступности сервера, раскоментировать тестовые данные!
        // newStatistic = getTestStatistic();
        Iterator<Map.Entry<String, Object>> entries = getSiteMap().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            list.add(new GeneralStatisticOnSite(
                    (String) entry.getValue(),
                    getReviewDate(entry.getKey()),
                    getPersonsList(entry.getKey()),
                    getRanksList(entry.getKey())));
        }
        if(list.isEmpty()) {
            JOptionPane.showMessageDialog(Window.getInstance(), 
                    "<html>Не удалось получить общую статистику "
                    + "от сервера!<br>Повторный запрос будет отправлен через "
                    + FREQUENCY_UPDATES + " сек.", "Сервер не "
                    + "доступен", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    /**
     * Возвращает обьект json по url адресу ресурса.
     *
     * @param url - адресс ресурса(веб-сервера)
     * @return json элемент статистики в виде json обьекта
     */
    private static JSONObject getJSON(String url) {
        JSONObject json = null;
        try {
            URL source = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(source.openStream(), "UTF-8"));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            //System.out.println(sb.);
            json = new JSONObject(sb.toString());
            in.close();
        } catch (IOException ex) {
            json = new JSONObject();
        }
        return json;
    }

    /**
     * Возвращает карту сайтов.
     *
     * @return карта сайтов в виде пар ключ - значения колекции Map
     */
    private static Map<String, Object> getSiteMap() {
        JSONObject json = getJSON(URL_SITE);
        return json.toMap();
    }

    /**
     * Возвращает дату последнего обновления статистики сайта.
     *
     * @param siteID - ключ обозначающий определенный сайт
     * @return дата в виде обьекта <b>Calendar</b>
     */
    private static Calendar getReviewDate(String siteID) {
        JSONObject json = getJSON(URL_GENERAL_STATISTIC + siteID);
        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(json.getLong("date"));
        return date;
    }

    /**
     * Возвращает список персон сайта.
     *
     * @param siteID - ключ обозначающий определенный сайт
     * @return список в виде коллекции List с именами персон
     */
    private static List<String> getPersonsList(String siteID) {
        List<String> persons = new ArrayList<>();
        JSONObject json = getJSON(URL_GENERAL_STATISTIC + siteID);
        JSONObject jsonResult = (JSONObject) json.get("result");
        for (Map.Entry<String, Object> entry : jsonResult.toMap().entrySet()) {
            persons.add((String) entry.getKey());
        }
        return persons;
    }

    /**
     *
     * @param siteID - ключ обозначающий определенный сайт
     * @return список в виде коллекции List с рейтингом персон
     */
    private static List<Integer> getRanksList(String siteID) {
        List<Integer> ranks = new ArrayList<>();
        JSONObject json = getJSON(URL_GENERAL_STATISTIC + siteID);
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

    /**
     * Делает выборку необходимых сайтов по параметрам переданным в виде класса
     * <b>Specification</b>.
     *
     * @param specification - условия для выборки
     * @return список элементов статистики в виде коллекции List
     */
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

    /**
     * Тестовый метод для заполнения списка тестовыми данными.
     */
    private static List<GeneralStatisticOnSite> getTestStatistic() {
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
        newStatistic.add(new GeneralStatisticOnSite(siteName1, new GregorianCalendar(2017, Calendar.FEBRUARY, 5), persons1, allRanks1));

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
        allRanks2.add(874);
        allRanks2.add(670);
        allRanks2.add(100);
        allRanks2.add(189);
        newStatistic.add(new GeneralStatisticOnSite(siteName2, new GregorianCalendar(2017, Calendar.FEBRUARY, 5), persons2, allRanks2));

        String siteName3 = "News.com";
        ArrayList<String> persons3 = new ArrayList<>();
        persons3.add("Путин");
        persons3.add("Трамп");
        persons3.add("Обама");
        persons3.add("Меркиль");
        persons3.add("Оланд");
        persons3.add("Навальный");
        ArrayList<Integer> allRanks3 = new ArrayList<>();
        allRanks3.add(1570);
        allRanks3.add(1321);
        allRanks3.add(789);
        allRanks3.add(732);
        allRanks3.add(612);
        allRanks3.add(211);
        newStatistic.add(new GeneralStatisticOnSite(siteName3, new GregorianCalendar(2017, Calendar.FEBRUARY, 7), persons3, allRanks3));

        return newStatistic;
    }

    /**
     * Класс служит для обновления статистики по сайтам, и записи оной в файл.
     *
     * Подкласс GeneralStatisticOnSiteRepository. Переодически опрашивает
     * сервис, при возврате данных обновляет список статистики сайтов и
     * переписывает данные в файле на новые.
     */
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
                    Thread.sleep(FREQUENCY_UPDATES * 1000);
                    System.out.println("Обновление статистики!");
                    List<GeneralStatisticOnSite> newStatistic = updateStatistic();
                    if (!generalStatisticOnSite.equals(newStatistic) && !newStatistic.isEmpty()) {
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
}
