package ru.geekbrain.gbseeker.personrank.net;


public class RestAPI {
    static final private String server="http://37.194.87.95:30000";

    public static void authentication(iNet2SQL net2SQL,String user,String pass) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute(server+"/authentication/"+user+"/"+pass);
    }
    public static void registration(iNet2SQL net2SQL,String user,String pass) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute(server+"/registration/"+user+"/"+pass);
    }

    public static void getSites(iNet2SQL net2SQL) {
        ConnectionWrapper p = new ConnectionWrapper(net2SQL);
        p.execute(server+"/site");
    }

    public static void getPersons(iNet2SQL net2SQL) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/person");
    }

    public static void getKeywords(iNet2SQL net2SQL, int person_id) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/person",
                    server + "/keyword/"+person_id);
    }

    public static void getCommonStats(iNet2SQL net2SQL,int site_id) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute(server + "/site",
                    server + "/common/" + site_id);
    }

    public static void getDailyStats(iNet2SQL net2SQL,int site_id,int person_id,long from,long to) {
        ConnectionWrapper p1 = new ConnectionWrapper(net2SQL);
        p1.execute( server + "/site",
                    server + "/person",
                    server + "/daily/"+site_id+"/"+person_id+"/"+from+"/"+to);
    }
}
