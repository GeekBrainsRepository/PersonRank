package infologic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Антон Владимирович on 21.01.2017.
 */
@RestController
public class StatController {

    @RequestMapping("/stat/getplist")
    public Map<Integer,String> getPersonList() {


        //fake data
        //TODO replace with DAO request
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"First");
        map.put(2,"Second");


        return map;

    }
    @RequestMapping("/stat/getrlist")
    public Map<Integer,String> getResourceList() {

        //fake data
        //TODO replace with DAO request
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"lenta.ru");
        map.put(2,"meduza.io");

        return map;

    }

}
