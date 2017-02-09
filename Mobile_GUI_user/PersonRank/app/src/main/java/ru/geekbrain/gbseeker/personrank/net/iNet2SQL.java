package ru.geekbrain.gbseeker.personrank.net;

public interface iNet2SQL {
    void updateDB(String json,String param);
    void updateUI();
    String getInfo();
    void init();
}
