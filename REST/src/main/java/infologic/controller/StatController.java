package infologic.controller;

import infologic.StatisticUtilities;
import infologic.model.CommonStat;
import infologic.model.DailyStat;
import infologic.model.SitesEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StatController {

    @RequestMapping(method = RequestMethod.GET, path="/statistic/getpersonlist")
    public Map<Integer, String> getPersonList() {
        return StatisticUtilities.getPersons();
    }

    @RequestMapping("/statistic/getresourcelist")
    public Map<Integer, String> getResourceList() {
        return StatisticUtilities.getSites();
    }

    // Финальный вид для реализованного интрфейса
    @RequestMapping("/statistic/common/{siteId}")
    public CommonStat getCommon(@PathVariable("siteId") Integer siteId) {
        return StatisticUtilities.createCommon(siteId);
    }

    // Финальный вид для реализованного интрфейса
    // For test
    //Wed Feb 01 00:00:00 MSK 2017 long 1485896400000
    //Thu Feb 02 00:00:00 MSK 2017 long 1485982800000
    //Fri Feb 03 00:00:00 MSK 2017 long 1486069200000
    //Sat Feb 04 00:00:00 MSK 2017 long 1486155600000
    @RequestMapping("/statistic/daily/{siteId}/{personId}/{dateStart}/{dateEnd}")
    public DailyStat getDaily(@PathVariable Integer siteId, @PathVariable Integer personId, @PathVariable Long dateStart, @PathVariable Long dateEnd) {
        return StatisticUtilities.createDaily(siteId,personId,dateStart,dateEnd);
    }
}