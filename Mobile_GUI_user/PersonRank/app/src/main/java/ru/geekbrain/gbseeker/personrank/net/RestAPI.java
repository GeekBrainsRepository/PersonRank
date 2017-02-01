package ru.geekbrain.gbseeker.personrank.net;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by m on 31.01.2017.
 */
public class RestAPI {


    public static void getSite(iNet2SQL net2SQL) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute("http://37.194.87.95:30000/statistic/getresourcelist");


    }
/*
    static void getPerson() {
        ConnectionWrapper p1 = new ConnectionWrapper();
        p1.execute("http://37.194.87.95:30000/statistic/getpersonlist");
    }

    static void getCommonStats(int site_id) {
        ConnectionWrapper p1 = new ConnectionWrapper();
        p1.execute("http://37.194.87.95:30000/statistic/common/" + site_id);
    }*/
}
