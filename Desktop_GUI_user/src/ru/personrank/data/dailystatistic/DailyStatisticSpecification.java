
package ru.personrank.data.dailystatistic;

import ru.personrank.data.Specification;

/**
 * Генерирует условия для запросов к репозиторию.
 * 
 * <p>
 * Статический класс генерации. Создает анонимные классы <code>Specification
 * </code> для передачи в качестве аргумента в запросы <b>"query"</b> репозитория 
 * </p>
 * 
 * @author Мартынов Евгений
 * 
 * @see Specification
 * @see DailyStatisticOnSite
 */
public final class DailyStatisticSpecification {

    /**
     * Закрытый конструктор, чтобы исключить возможность создания объекта.
     */
    private DailyStatisticSpecification() {}

    /**
     * Возвращает все элементы таблицы ежедневной статистики.
     * @return - обьект спецификации
     */
    public static Specification<DailyStatisticOnSite> getAllStatisticSite() {
        return new Specification<DailyStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(DailyStatisticOnSite entry) {
                return true;
            }
        };
    }

    /**
     * Возвращает элементы таблицы ежедневной статистики с определенным 
     * именем сайта.
     * 
     * @param nameSite - название сайта
     * @return - обьект спецификации
     */
    public static Specification<DailyStatisticOnSite> findStatisticSite(final String nameSite) {
        return new Specification<DailyStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(DailyStatisticOnSite entry) {
                return nameSite.equals(entry.getSiteName());
            }
        };
    }
}
