
package ru.personrank.data.generalstatistic;

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
 * @see GeneralStatisticOnSite
 */
public class GeneralStatisticSpecification {

    /**
     * Закрытый конструктор, чтобы исключить возможность создания объекта.
     */
    private GeneralStatisticSpecification() {}
    
    /**
     * Возвращает все элементы таблицы общей статистики.
     * @return - обьект спецификации
     */
    public static Specification<GeneralStatisticOnSite> getAllStatisticSite() {
        return new Specification<GeneralStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(GeneralStatisticOnSite entry) {
                return true;
            }
        };
    }

     /**
     * Возвращает элементы таблицы общей статистики с определенным 
     * именем сайта.
     * 
     * @param nameSite - название сайта
     * @return - обьект спецификации
     */
    public static Specification<GeneralStatisticOnSite> findStatisticSite(final String nameSite) {
        return new Specification<GeneralStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(GeneralStatisticOnSite entry) {
                return nameSite.equals(entry.getSiteName());
            }
        };
    }
}
