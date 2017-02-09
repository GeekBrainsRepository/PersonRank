package ru.geekbrain.gbseeker.personrank.net;


public class RestAPI {
    static final private String server="http://37.194.87.95:30000";

    public static void authentication(iNet2SQL net2SQL) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute(server+"/authentication/user/user");
    }

    public static void getSite(iNet2SQL net2SQL) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute(server+"/site");
    }

    public static void getPerson(iNet2SQL net2SQL) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/person");
    }

    public static void getKeyword(iNet2SQL net2SQL,int person_id) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/person",server + "/keyword/"+person_id);
    }

    public static void getCommonStats(iNet2SQL net2SQL,int site_id) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/site",server + "/common/" + site_id);
    }

    public static void getDailyStats(iNet2SQL net2SQL,int site_id,int person_id,long from,long to) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/statistic/daily/"+site_id+"/"+person_id+"/"+from+"/"+to);
    }
}
