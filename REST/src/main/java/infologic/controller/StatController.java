package infologic.controller;

import infologic.StatisticUtilities;
import infologic.model.CommonStat;
import infologic.model.DailyStat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatController {

    @RequestMapping("/statistic/getpersonlist")
    public Map<Integer, String> getPersonList() {
        // fake data
        // TODO replace with DAO request
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "First");
        map.put(2, "Second");
        return map;
    }

    @RequestMapping("/statistic/getresourcelist")
    public Map<Integer, String> getResourceList() {
        // fake data
        // TODO replace with DAO request
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "lenta.ru");
        map.put(2, "meduza.io");
        return map;
    }

    // Финальный вид для реализованного интрфейса
    @RequestMapping("/statistic/common/{siteId}")
    public CommonStat getCommon(@PathVariable("siteId") Integer siteId) {
        return StatisticUtilities.createCommon(siteId);
    }

    // Финальный вид для реализованного интрфейса
    @RequestMapping("/statistic/daily/{siteId}/{personId}/{dateStart}/{dateEnd}")
    public DailyStat getDaily(@PathVariable Integer siteId, @PathVariable Integer personId, @PathVariable Long dateStart, @PathVariable Long dateEnd) {
        return null;
    }
}