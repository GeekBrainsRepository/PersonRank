package ru.geekbrain.gbseeker.personrank.net;


public class RestAPI {


    public static void getSite(iNet2SQL net2SQL) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute("http://37.194.87.95:30000/statistic/getresourcelist");


    }

    public static void getPerson(iNet2SQL net2SQL) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute("http://37.194.87.95:30000/statistic/getpersonlist");
    }

    public static void getKeyword(iNet2SQL net2SQL) {
        //ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        //p1.execute("http://37.194.87.95:30000/statistic/getpersonlist");
    }

    public static void getCommonStats(iNet2SQL net2SQL,int site_id) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute("http://37.194.87.95:30000/statistic/common/" + site_id);
    }


    public static void getDailyStats(iNet2SQL net2SQL,int site_id,int person_id,long from,long to) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute("http://37.194.87.95:30000/statistic/daily/"+site_id+"/"+person_id+"/"+from+"/"+to);
    }
}
