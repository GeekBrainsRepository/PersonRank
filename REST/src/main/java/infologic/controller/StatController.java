package infologic.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Антон Владимирович on 21.01.2017.
 */
@RestController
public class StatController {

    @RequestMapping("/statistic/getpersonlist")
    public Map<Integer,String> getPersonList() {


        //fake data
        //TODO replace with DAO request
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"First");
        map.put(2,"Second");


        return map;

    }
    @RequestMapping("/statistic/getresourcelist")
    public Map<Integer,String> getResourceList() {

        //fake data
        //TODO replace with DAO request
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"lenta.ru");
        map.put(2,"meduza.io");

        return map;

    }
    @RequestMapping("/statistic/common/{resourceId}")
    public Map<String,Integer> getCommonResourceStat(@PathVariable("resourceId") Integer resourceId) {
        //fake data
        //TODO replace with DAO request
        Map<String,Integer> map = new HashMap<>();
        map.put("Putin",450);
        map.put("Medvedev",370);
        map.put("Variable",resourceId);


        return map;

    }
    @RequestMapping("/statistic/daily/{resourceId}/{personId}/{dateStart}/{dateEnd}")
    public Map<LocalDate,Integer> getDailyStat(@PathVariable Integer resourceId,@PathVariable Integer personId,
                                          @PathVariable String dateStart,@PathVariable String  dateEnd) {
        Map<LocalDate,Integer> map = new HashMap<>();

        //String parsing example
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        LocalDate dateTime = LocalDate.parse(dateStart, formatter);
        Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        map.put(dateTime,1000);
        map.put(dateTime,1001);

        return map;
    }
}
