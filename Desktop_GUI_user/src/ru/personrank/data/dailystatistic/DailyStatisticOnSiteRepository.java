/*
 *  
 */
package ru.personrank.data.dailystatistic;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.personrank.data.Repository;
import ru.personrank.data.Specification;
import ru.personrank.data.UpdatingRepositoryEvent;
import ru.personrank.data.UpdatingRepositoryListener;
import ru.personrank.view.Window;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.personrank.data.generalstatistic.GeneralStatisticOnSite;

/**
 * Класс служит для хранения данных таблицы "Ежедневная статистика" получаемых
 * от сервера.
 * <p>
 * Класс реализует интерфейс <code>Repository</code>. Хранит обьекты данных
 * таблицы <code>DailyStatisticOnSite</code> в виде списка. Делает запросы к
 * веб-сервису для обновления данных, при удачном запросе обнавляет данные в
 * списке и записывает их файл. Если сервер недоступен при создании обьекта в
 * список загружаются данные из файла.</p>
 *
 * @author Мартынов Евгений
 *
 * @see GeneralStatisticOnSite
 * @see Repository
 *
 * @version 1.0
 */
public class DailyStatisticOnSiteRepository implements Repository<DailyStatisticOnSite> {

    private static final long FREQUENCY_UPDATES = 60; //в секундах
    private static final String PATH_SAVED_FILE = "dump/Daily_statistic.dp";
    private static final String URL_SITE = "http://37.194.87.95:30000/site";
    private static final String URL_PERSON = "http://37.194.87.95:30000/person";
    private static final String URL_DAILY_STATISTIC = "http://37.194.87.95:30000/daily/";

    private static final DailyStatisticOnSiteRepository INSTANCE = new DailyStatisticOnSiteRepository();

    private static Logger log = Logger.getLogger(DailyStatisticOnSiteRepository.class.getName());

    List listenerList;
    List<DailyStatisticOnSite> dailyStatisticOnSite;

    /**
     * Создает новый репозиторий.
     */
    private DailyStatisticOnSiteRepository() {
        listenerList = new ArrayList();
        dailyStatisticOnSite = load();
        if (dailyStatisticOnSite.isEmpty()) {
            dailyStatisticOnSite = updateStatistic();
        }
        Window.addThreadInPool(new UpdaterDailyStatistic());
    }

    /**
     * Возвращает обьект репозитория.
     */
    public static DailyStatisticOnSiteRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Добавляет одного слушателя события - "обновление репозитория".
     *
     * @param listener - слушатель события
     */
    public void addUpdatingRepositoryListener(UpdatingRepositoryListener listener) {
        listenerList.add(listener);
    }

    /**
     * Удаляет одного слушателя события - "обновление репозитория".
     *
     * @param listener - слушатель события
     */
    public void removeUpdatingRepositoryListener(UpdatingRepositoryListener listener) {
        listenerList.remove(listener);
    }

    /**
     * Оповещает всех лушателей о происшествии события.
     */
    private void fireUpdatingRepositoryEvent() {
        UpdatingRepositoryEvent event = new UpdatingRepositoryEvent(INSTANCE);
        for (Object listener : listenerList) {
            if (listener instanceof UpdatingRepositoryListener) {
                ((UpdatingRepositoryListener) listener).repositoryUpdated(event);
            }
        }
    }

    @Override
    public void add(DailyStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(DailyStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(DailyStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Запрашивает список элементов репозитория удовлетворяющих условиям
     * заданным в спецификации.
     *
     * @param specification - обьект спецификации
     * @return список элементов репозитория в виде коллекции List
     */
    @Override
    public List<DailyStatisticOnSite> query(Specification specification) {
        List<DailyStatisticOnSite> newList = new ArrayList<>();
        for (DailyStatisticOnSite dsos : dailyStatisticOnSite) {
            if (specification.IsSatisfiedBy(dsos)) {
                newList.add(dsos);
            }
        }
        return newList;
    }

    /**
     * Сохраняет список обьектs спецификации в файл.
     * <p>
     * Сохраняет список сайтов, переданный в качестве аргумента, в файл.
     * </p>
     *
     * @param statistic - список элементов статистики в виде коллекции List
     */
    private void save(List<DailyStatisticOnSite> statistic) {
        try (ObjectOutputStream objOStrm = new ObjectOutputStream(
                new FileOutputStream(PATH_SAVED_FILE))) {
            objOStrm.writeObject(statistic);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Загружает сохраненную статистику из файла.
     *
     * @return список элементов статистики в виде коллекции List
     */
    private List<DailyStatisticOnSite> load() {
        File file = new File(PATH_SAVED_FILE);
        if (file.exists()) {
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            try (ObjectInputStream objIStr = new ObjectInputStream(new FileInputStream(file))) {
                List<DailyStatisticOnSite> list = (List<DailyStatisticOnSite>) objIStr.readObject();
                return list;
            } catch (FileNotFoundException ex) {
                log.log(Level.SEVERE, null, ex);
                return new ArrayList<>();
            } catch (IOException | ClassNotFoundException ex) {
                log.log(Level.SEVERE, null, ex);
                return new ArrayList<>();
            }
        } else {
            try {
                File folder = new File("dump");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                file.createNewFile();
                return new ArrayList<>();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Собирает обновленный список элементов ежедневной статистики
     *
     * @return список элементов статистики в виде коллекции List
     */
    private List<DailyStatisticOnSite> updateStatistic() {
        List<DailyStatisticOnSite> list = new ArrayList<>();
//        На случай недоступности сервера, раскоментировать тестовые данные!
        list = getTestStatistic();
//        Раскоментировать при доступном сервере
//        Iterator<Map.Entry<String, Object>> entriesSite = getSiteMap().entrySet().iterator();
//        List<DailyStatisticOnSite.Person> persons = null;
//        List<Calendar> scanDateList = getScanDateList();
//        while (entriesSite.hasNext()) {
//            Map.Entry<String, Object> entrySite = entriesSite.next();
//            persons = new ArrayList<>();
//            Iterator<Map.Entry<String, Object>> entriesPerson = getPersonMap().entrySet().iterator();
//            while (entriesPerson.hasNext()) {
//                Map.Entry<String, Object> entryPerson = entriesPerson.next();
//                persons.add(new DailyStatisticOnSite.Person(
//                        (String) entryPerson.getValue(),
//                        scanDateList,
//                        getNumNewPages(entrySite.getKey(), entryPerson.getKey(), scanDateList)
//                ));
//            }
//            list.add(new DailyStatisticOnSite((String) entrySite.getValue(), persons));
//        }
        if (list.isEmpty()) {
            log.warning("Не удалось получить ежедневную статистику от сервера!"
                    + "Повторный запрос будет отправлен через "
                    + FREQUENCY_UPDATES + " сек.");
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
            json = new JSONObject(sb.toString());
            in.close();
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            json = new JSONObject();
        }
        return json;
    }

    /**
     * Возвращает карту сайтов.
     *
     * @return карта сайтов в виде пар ключ-значение колекции Map
     */
    private static Map<String, Object> getSiteMap() {
        JSONObject json = getJSON(URL_SITE);
        return json.toMap();
    }

    /**
     * Возвращает карту персон.
     *
     * @return карта сайтов в виде пар ключ-значение колекции Map
     */
    private static Map<String, Object> getPersonMap() {
        JSONObject json = getJSON(URL_PERSON);
        return json.toMap();
    }

    /**
     * Возвращает список дат.
     *
     * @return даты в виде колекции List
     */
    private static List<Calendar> getScanDateList() {
        List<Calendar> dateList = new ArrayList<>();
        Calendar firstDay = new GregorianCalendar(2017, Calendar.JANUARY, 1);
        Calendar counterDay;
        Calendar toDay = new GregorianCalendar();
        Calendar nextDay;
        dateList.add(firstDay);
        counterDay = (Calendar) firstDay.clone();
        while (counterDay.compareTo(toDay) < 0) {
            counterDay.add(Calendar.DATE, 1);
            nextDay = new GregorianCalendar();
            nextDay.setTime(counterDay.getTime());
            dateList.add(nextDay);
        }
        return dateList;
    }

    /**
     * Возвращает количество страниц сайта с на которых упоминается личность за
     * определенную дату.
     *
     * @param siteID - идентификатор сайта
     * @param personID - идентификатор личности
     * @param scanDateList - список дат
     * @return количество страниц в виде списка List
     */
    private List<Integer> getNumNewPages(String siteID, String personID, List<Calendar> scanDateList) {
        List<Integer> numPages = new ArrayList();
        JSONObject json = getJSON(URL_DAILY_STATISTIC
                + siteID + "/"
                + personID + "/"
                + scanDateList.get(0).getTimeInMillis() + "/"
                + scanDateList.get(scanDateList.size() - 1).getTimeInMillis()
        );
        JSONArray arr = (JSONArray) json.get("result");
        for (Object object : arr) {
            numPages.add((Integer) object);
        }
        return numPages;
    }

    /**
     * Возвращает тестовые данные. Метод нужен исключительно для тестирования
     * репозитория.
     *
     * @return - список элементов статистики в колекции List
     */
    private List<DailyStatisticOnSite> getTestStatistic() {
        List<DailyStatisticOnSite> testStatistic = new ArrayList<>();
        DailyStatisticOnSite.Person p1 = new DailyStatisticOnSite.Person("Путин",
                Arrays.asList(new Calendar[]{new GregorianCalendar(2017, Calendar.JANUARY, 17),
            new GregorianCalendar(2017, Calendar.JANUARY, 19),
            new GregorianCalendar(2017, Calendar.JANUARY, 21),
            new GregorianCalendar(2017, Calendar.JANUARY, 22),
            new GregorianCalendar(2017, Calendar.JANUARY, 23),
            new GregorianCalendar(2017, Calendar.JANUARY, 24),
            new GregorianCalendar(2017, Calendar.JANUARY, 25),
            new GregorianCalendar(2017, Calendar.JANUARY, 26),
            new GregorianCalendar(2017, Calendar.JANUARY, 27),
            new GregorianCalendar(2017, Calendar.JANUARY, 29)}),
                Arrays.asList(new Integer[]{4, 0, 0, 3, 5, 8, 5, 0, 0, 0}));
        DailyStatisticOnSite.Person p2 = new DailyStatisticOnSite.Person("Трамп",
                Arrays.asList(new Calendar[]{new GregorianCalendar(2017, Calendar.JANUARY, 18),
            new GregorianCalendar(2017, Calendar.JANUARY, 20),
            new GregorianCalendar(2017, Calendar.JANUARY, 21),
            new GregorianCalendar(2017, Calendar.JANUARY, 22),
            new GregorianCalendar(2017, Calendar.JANUARY, 23),
            new GregorianCalendar(2017, Calendar.JANUARY, 24),
            new GregorianCalendar(2017, Calendar.JANUARY, 25),
            new GregorianCalendar(2017, Calendar.JANUARY, 26),
            new GregorianCalendar(2017, Calendar.JANUARY, 27),
            new GregorianCalendar(2017, Calendar.JANUARY, 28)}),
                Arrays.asList(new Integer[]{2, 1, 3, 7, 5, 9, 7, 2, 1, 4}));
        DailyStatisticOnSite site1 = new DailyStatisticOnSite("lenta.ru", Arrays.asList(new DailyStatisticOnSite.Person[]{p1, p2}));
        testStatistic.add(site1);

        DailyStatisticOnSite.Person p3 = new DailyStatisticOnSite.Person("Путин",
                Arrays.asList(new Calendar[]{new GregorianCalendar(2017, Calendar.JANUARY, 17),
            new GregorianCalendar(2017, Calendar.JANUARY, 20),
            new GregorianCalendar(2017, Calendar.JANUARY, 21)}),
                Arrays.asList(new Integer[]{1, 4, 2}));
        DailyStatisticOnSite.Person p4 = new DailyStatisticOnSite.Person("Трамп",
                Arrays.asList(new Calendar[]{new GregorianCalendar(2017, Calendar.JANUARY, 19),
            new GregorianCalendar(2017, Calendar.JANUARY, 20),
            new GregorianCalendar(2017, Calendar.JANUARY, 21)}),
                Arrays.asList(new Integer[]{5, 2, 1}));
        DailyStatisticOnSite.Person p5 = new DailyStatisticOnSite.Person("Обама",
                Arrays.asList(new Calendar[]{new GregorianCalendar(2017, Calendar.JANUARY, 17),
            new GregorianCalendar(2017, Calendar.JANUARY, 19),
            new GregorianCalendar(2017, Calendar.JANUARY, 21)}),
                Arrays.asList(new Integer[]{4, 8, 2}));
        DailyStatisticOnSite site2 = new DailyStatisticOnSite("komersant.ru", Arrays.asList(new DailyStatisticOnSite.Person[]{p3, p4, p5}));
        testStatistic.add(site2);
        return testStatistic;
    }

    /**
     * Создает задачу, которая обнавляет репозиторий ежедневной статистики.
     *
     * <p>
     * Отправляет запрос на сервер. Если новые данные не отличаются от текущих
     * то задача переходит в режим ожидания до истечения времени таймера. Если
     * данные отличаются, то новые замещают старые, затем оповещаются все
     * слушатели о том что произошло обновление репозитория и происходит
     * сохранение новых данных в файл.
     * </p>
     */
    private class UpdaterDailyStatistic extends Thread {

        /**
         * Создает задачу.
         */
        public UpdaterDailyStatistic() {
            super("UpdaterDailyStatistic");
            super.setDaemon(true);
        }

        /**
         * Выполняет обновление статистики.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(FREQUENCY_UPDATES * 1000);
                    List<DailyStatisticOnSite> newStatistic = updateStatistic();
                    if (!dailyStatisticOnSite.equals(newStatistic) && !newStatistic.isEmpty()) {
                        dailyStatisticOnSite = newStatistic;
                        fireUpdatingRepositoryEvent();
                        log.info("Ежедневная статистика обновлена!");
                        save(newStatistic);
                    }
                } catch (InterruptedException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
